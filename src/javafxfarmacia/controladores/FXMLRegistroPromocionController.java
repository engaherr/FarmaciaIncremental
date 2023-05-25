/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import javafxfarmacia.interfaz.INotificacionOperacion;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.PromocionDAO;
import javafxfarmacia.modelo.dao.PromocionProductoDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.modelo.pojo.Promocion;
import javafxfarmacia.modelo.pojo.PromocionProducto;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLRegistroPromocionController implements Initializable {

    private INotificacionOperacion interfazNotificacion;
    private boolean esEdicion;
    private Promocion promocion;

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
    private File archivoFoto;
    @FXML
    private Label rutaImagen;
    
    private ObservableList<Producto> productos;
    private ObservableList<PromocionProducto> carrito;
    private ObservableList<PromocionProducto> productosEditados;
    private ObservableList<PromocionProducto> productosEliminados;
    @FXML
    private TableView<PromocionProducto> tvProductosdePromocion;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfPrecioFinal;
    @FXML
    private TableColumn tcNombreProducto;
    @FXML
    private TableColumn tcCantidadProducto;
    @FXML
    private TableColumn tcPrecioUnitario;
    @FXML
    private TableColumn tcPrecioFinal;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField tfPrecioActualUnidad;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnNuevoProducto;
    @FXML
    private Button btnAñadirProducto;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionProducto();
        
        tcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        tcCantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tcPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        tcPrecioFinal.setCellValueFactory(new PropertyValueFactory<>("precioFinal"));
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        carrito = FXCollections.observableArrayList();

        
        tvProductosdePromocion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PromocionProducto>() {
            @Override
            public void changed(ObservableValue<? extends PromocionProducto> observable, PromocionProducto oldValue, PromocionProducto newValue) {
               
                btnEliminar.setDisable(newValue == null);
                btnModificar.setDisable(newValue == null);
                String precioUnidad = Double.toString(newValue.getPrecioUnitario());
                String cantidad = Integer.toString(newValue.getCantidad());
                String precioFinal = Double.toString(newValue.getPrecioFinal());
                tfPrecioActualUnidad.setText(precioUnidad);
                tfCantidad.setText(cantidad);
                tfPrecioFinal.setText(precioUnidad);
                
            }
        
        }); 
        cbProductos.valueProperty().addListener(new ChangeListener<Producto>(){
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
                if(newValue != null){
                    String precio = String.valueOf(newValue.getPrecio());
                    tfPrecioActualUnidad.setText("$ "+precio+ " MXN");
                }
            }
            
        });
    } 
    
    public void inicializarInformacionFormulario(boolean esEdicion, Promocion promocion, INotificacionOperacion interfazNotificacion){
        this.esEdicion = esEdicion;
        this.promocion = promocion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            cargarInformacionPromocion();
            actualizarTablaPromocion();
        }else{
            
        }
    }
    
    private void cargarInformacionPromocion(){
        tfDescripcionPromo.setText(promocion.getDescripcion());
        tfFechaInicio.setText(promocion.getFechaInicio());
        tfFechaTermino.setText(promocion.getFechaTermino());
        
        try{
            ByteArrayInputStream inputFoto = new ByteArrayInputStream(promocion.getImagen());
            Image imgPromocion = new Image(inputFoto);
            imagenPromocion.setImage(imgPromocion);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        cargarProductosPromocion();
                
    }
    
    private void cargarProductosPromocion(){
        carrito = FXCollections.observableArrayList();
        carrito.addAll(promocion.getProductos().getPromocionesProductoRespuesta());
        tvProductosdePromocion.setItems(carrito);
              
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
        try{
            if(esEdicion){
                if(archivoFoto != null){
                    promocionValida.setImagen(Files.readAllBytes(archivoFoto.toPath()));
                }else{
                    promocionValida.setImagen(promocion.getImagen());
                }
                promocionValida.setIdPromocion(promocion.getIdPromocion());
                actualizarPromocion(promocionValida);
            }else{
                registrarPromocion(promocionValida);
            }
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error con el archivo","Ocurrió un error al intentar guardar la imagen, "
                    + "por favor vuelva a seleccionar el archivo", Alert.AlertType.ERROR);
        }

    }
    private void actualizarPromocion(Promocion promocionActualizar){
        int codigoRespuesta = PromocionDAO.modificarPromocion(promocionActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion","Por el momento no hay conexión, "
                        + "por favor inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al modificar la promoción,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING); 
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Promocion registrada", "La actualización de la "
                        + "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                int idPromocion = promocionActualizar.getIdPromocion();
                actualizarProductosPromocion(idPromocion);
                break;
        }
    }
    
    private void actualizarProductosPromocion(int idPromocion){
        
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
                Promocion promocionTemporal = PromocionDAO.obtenerUltimaPromocionGuardada();
                int idPromocion = promocionTemporal.getIdPromocion();
                registrarProductosPromocion( idPromocion);
                break;
        }
        
    }
    
    private void registrarProductosPromocion(int promocionNueva){
        int tamano = carrito.size() - 1;
        for(int i = 0; i <= tamano; i++){
            carrito.get(i).setIdPromocion(promocionNueva);
            int codigoRespuesta = PromocionProductoDAO.guardarPromocionProducto(carrito.get(i));
            switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Por el momento no hay conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al registrar los productos de la promoción,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Promocion registrada", "El registro de los productos de la "
                        + "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                
                break;
            }
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
        archivoFoto = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        visualizarImagen(archivoFoto);
             
    }
    
    private void visualizarImagen(File imagenSeleccionada){
        if(imagenSeleccionada != null){
            try{
                BufferedImage bufferImage = ImageIO.read(imagenSeleccionada);
                Image imagenCodificada = SwingFXUtils.toFXImage(bufferImage, null);
                imagenPromocion.setImage(imagenCodificada);
                
                
            }catch(IOException ex){
                rutaImagen.setText("Error al tratar de cargar la imagen seleccioanda");
            }
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
        double precioFinal = Integer.parseInt(tfPrecioFinal.getText());
        
        if(productoSeleccionado != null && unidadesProducto > 0){
           PromocionProducto promocionProducto = new PromocionProducto();
            promocionProducto.setIdProducto(productoSeleccionado.getIdProducto());
            promocionProducto.setNombreProducto(productoSeleccionado.getNombre());
            promocionProducto.setCantidad(unidadesProducto);
            promocionProducto.setPrecioUnitario(productoSeleccionado.getPrecio());
            promocionProducto.setPrecioFinal(precioFinal);
            
            carrito.add(promocionProducto);
            actualizarTablaPromocion();
            limpiarCeldasProducto();

            
        }else{
            Utilidades.mostrarDialogoSimple("Adevertencia","Por favor selecciona un producto o "
                    + "ingresa una cantidad mayor a cero", Alert.AlertType.WARNING);
        }
    }
    
    private void actualizarTablaPromocion(){
        ObservableList<PromocionProducto> listaCarrito = FXCollections.observableArrayList(carrito);
        tvProductosdePromocion.setItems(carrito);
        double precioTotal = 0;
        
        for(PromocionProducto promocion : listaCarrito){
            precioTotal = precioTotal + promocion.getPrecioFinal();
        }
        lbPrecioFinal.setText("Precio final: $" + String.valueOf(precioTotal) + " MXN");
    }

    @FXML
    private void clicEliminarProducto(ActionEvent event) {
        PromocionProducto productoSeleccionado = tvProductosdePromocion.getSelectionModel().getSelectedItem();
        if(productoSeleccionado != null){
            carrito.remove(productoSeleccionado);
            actualizarTablaPromocion();
            limpiarCeldasProducto();

            if(carrito.size() < 1){
                 btnEliminar.setDisable(true);
            }else{
                btnEliminar.setDisable(false);
            }



        }
    }

    @FXML
    private void clicModificarProducto(ActionEvent event) {
        int posicion = tvProductosdePromocion.getSelectionModel().getSelectedIndex();
        PromocionProducto productoGuardado = carrito.get(posicion);        
        double precioFinal = Double.parseDouble(tfPrecioFinal.getText());
        int cantidad = Integer.parseInt(tfCantidad.getText());
        productoGuardado.setPrecioFinal(precioFinal);
        productoGuardado.setCantidad(cantidad);
        PromocionProducto producNuevo = productoGuardado;
        carrito.set(posicion, producNuevo);
        actualizarTablaPromocion();

        productosEditados.add(producNuevo);
        limpiarCeldasProducto();

    }

    @FXML
    private void clicNuevoProducto(ActionEvent event) {
        limpiarCeldasProducto();
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);

        
    }
    
    private void limpiarCeldasProducto(){
        tfCantidad.setText("");
        tfPrecioActualUnidad.setText("");
        tfPrecioFinal.setText("");
        cbProductos.getSelectionModel().clearSelection();
    }
    
}
