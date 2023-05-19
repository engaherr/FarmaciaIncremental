/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.PromocionDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.modelo.pojo.Promocion;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLRegistroPromocionController implements Initializable {

    @FXML
    private TextField tfDescripcionPromo;
    @FXML
    private ComboBox<Producto> cbProductos;
    @FXML
    private TextField tfFechaInicio;
    @FXML
    private TextField tfFechaTermino;
    @FXML
    private Label lbPrecioFinal;
    @FXML
    private ImageView imagenPromocion;
    @FXML
    private Label rutaImagen;
    
    private ObservableList<Producto> productos;
    private ObservableList<Producto> carrito;
    @FXML
    private TableView<Producto> tvProductosdePromocion;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfPrecioFinal;
    
    private byte[] imagenBytes;
    @FXML
    private TableColumn<?, ?> tcNombreProducto;
    @FXML
    private TableColumn<?, ?> tcCantidadProducto;
    @FXML
    private TableColumn<?, ?> tcPrecioUnitario;
    @FXML
    private TableColumn<?, ?> tcPrecioFinal;
    @FXML
    private Button btnEliminar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionProducto();
        
        tcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcCantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tcPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        tcPrecioFinal.setCellValueFactory(new PropertyValueFactory<>("precioFinal"));
        btnEliminar.setDisable(true);
        
        tvProductosdePromocion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
               
                btnEliminar.setDisable(newValue == null);
            }
        }); 
    }    

    @FXML
    private void clicRegistrarPromocion(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        String descripcion = tfDescripcionPromo.getText();
        String fechaInicio = tfFechaInicio.getText();
        String fechaTermino = tfFechaTermino.getText();
        
        Promocion promocionValida = new Promocion();
        promocionValida.setDescripcion(descripcion);
        promocionValida.setFechaInicio(fechaInicio);
        promocionValida.setFechaTermino(fechaTermino);
        promocionValida.setImagen(imagenBytes);
        registrarPromocion(promocionValida);
    }
    
    private void registrarPromocion(Promocion promocionRegistrar){
        int codigoRespuesta = PromocionDAO.registrarPromocion(promocionRegistrar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Por el momento no hay conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al registrar la promoción,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Promocion registrada", "El registro de la "
                        + "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                break;

        }
        
    }

    @FXML
    private void clicCancelarPromocion(ActionEvent event) {
        Stage escenarioBase = (Stage) tfDescripcionPromo.getScene().getWindow();
        escenarioBase.close();
        
    }

    @FXML
    private void clicSeleccionarImagen(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroDialogo = new 
            FileChooser.ExtensionFilter("Archivos PNG (*.png*)","*.PNG","*.JPG","*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroDialogo);
        
        Stage escenarioBase = (Stage) tfDescripcionPromo.getScene().getWindow();
        File imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        visualizarImagen(imagenSeleccionada);
             
    }
    
    private void visualizarImagen(File imagenSeleccionada){
        if(imagenSeleccionada != null){
            try{
                BufferedImage bufferImage = ImageIO.read(imagenSeleccionada);
                Image imagenCodificada = SwingFXUtils.toFXImage(bufferImage, null);
                imagenPromocion.setImage(imagenCodificada);
                guardarImagen(bufferImage);
                
            }catch(IOException ex){
                rutaImagen.setText("Error al tratar de cargar la imagen seleccioanda");
            }
        }
    }
    
    private void guardarImagen(BufferedImage imagenPromocion){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagenPromocion,"png",baos);
            imagenBytes = baos.toByteArray();
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error con imagen", "Ocurrió un error al guardar la imagen"
                    + "seleccionada, por favor inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarInformacionProducto(){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta productosBD = ProductoDAO.obtenerInformacionProducto();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "POr el momento no hay conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al cargar la información,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                productos.addAll(productosBD.getProductos());
                cbProductos.setItems(productos);
                break;
        }
    }

    @FXML
    private void clicAñadirProdcuto(ActionEvent event) {
        Producto productoSeleccionado = cbProductos.getSelectionModel().getSelectedItem();
        int unidadesProducto = Integer.parseInt(tfCantidad.getText());
        
        if(productoSeleccionado != null && unidadesProducto > 0){
            Producto productoEnPromocion = new Producto();
            productoEnPromocion.setIdProducto(productoSeleccionado.getIdProducto());
            productoEnPromocion.setNombre(productoSeleccionado.getNombre());
            productoEnPromocion.setCantidad(unidadesProducto);
            productoEnPromocion.setPrecio(productoSeleccionado.getPrecio());
            
            double precioUnitario = productoEnPromocion.getPrecio();
            double precioFinal = precioUnitario * unidadesProducto;
            productoEnPromocion.setPrecioFinal(precioFinal);
            
            carrito.add(productoEnPromocion);
            actualizarTablaPromocion();
            
            tfCantidad.clear();
            cbProductos.getSelectionModel().clearSelection();
            
        }else{
            Utilidades.mostrarDialogoSimple("Adevertencia","Por favor selecciona un producto o "
                    + "ingresa una cantidad mayor a cero", Alert.AlertType.WARNING);
        }
    }
    
    private void actualizarTablaPromocion(){
        ObservableList<Producto> listaCarrito = FXCollections.observableArrayList(carrito);
        for(Producto producto : listaCarrito) {
            double precioFinal = producto.getPrecio() * producto.getCantidad();
            producto.setPrecioFinal(precioFinal);
        }
        tvProductosdePromocion.setItems(listaCarrito);
        double precioTotal = 0;
        
        for(Producto producto : listaCarrito){
            precioTotal = precioTotal + producto.getPrecioFinal();
        }
        lbPrecioFinal.setText("$" + String.valueOf(precioTotal) + "mxn");
    }

    @FXML
    private void clicEliminarProducto(ActionEvent event) {
        Producto productoSeleccionado = tvProductosdePromocion.getSelectionModel().getSelectedItem();
        if(productoSeleccionado != null){
            productos.remove(productoSeleccionado);
            btnEliminar.setDisable(true);
            actualizarTablaPromocion();
 
        }
    }
}
