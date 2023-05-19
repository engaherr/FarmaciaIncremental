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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.SucursalDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.Sucursal;
import javafxfarmacia.modelo.pojo.SucursalRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLProductoFormularioController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfFechaVencimiento; //Opcional 
    @FXML
    private TextField tfCantidad;
    @FXML
    private CheckBox ckboxVentaControlada;
    @FXML
    private TextField tfPresentacion; //Opcional si(vacio) = 'N/A'
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfPrecio;
    
    private ObservableList<Sucursal> sucursales;
    private boolean esEdicion;
    private Producto productoEdicion;
    private byte[] imagenBytes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionSucursal();
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, Producto producto){
        this.esEdicion = esEdicion;
        this.productoEdicion = producto;
        if(esEdicion){
            lbTitulo.setText("Editar Producto");
        }else{
            lbTitulo.setText("Registrar Producto");
        }
    }
    
    private void cargarInformacionSucursal(){
        sucursales = FXCollections.observableArrayList();
        SucursalRespuesta sucursalesBD = SucursalDAO.obtenerInformacionProducto();
        switch(sucursalesBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión",
                            "Por el momento no hay conexión, por favor inténtelo más tarde"
                            , Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Hubo un error al cargar la información por favor intentélo de nuevo más tarde",
                            Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                    sucursales.addAll(sucursalesBD.getSucursales());
                    cbSucursal.setItems(sucursales);
                break;
        }
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
        validarCamposRegistro();
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        List<String> tiposDeArchivo = Arrays.asList("*.png", "*.jpg", "*.jpeg");
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroDialogo = 
                 new FileChooser.ExtensionFilter("Archivos de Imagen(*.png,*.jpg,*.jpeg)",
                         tiposDeArchivo);
        dialogoSeleccionImg.getExtensionFilters().add(filtroDialogo);
        
        Stage escenarioBase = (Stage) tfCantidad.getScene().getWindow();
        File archivoSeleccionado = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        visualizarImagen(archivoSeleccionado);
    }
    
    private void visualizarImagen(File imgSeleccionada) {
        if(imgSeleccionada != null){
            try {
                BufferedImage bufferImg = ImageIO.read(imgSeleccionada);
                Image imgDecodificada = SwingFXUtils.toFXImage(bufferImg, null);
                ivFoto.setImage(imgDecodificada);
                guardarImagen(bufferImg);
            } catch (IOException ex) {
                Utilidades.mostrarDialogoSimple("Error con imagen",
                        "Hubo un error para mostrar la imagen seleccionada, inténtelo de nuevo",
                        Alert.AlertType.ERROR);
            }
        }
    }
    
    private void guardarImagen(BufferedImage bufferImg){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferImg, "png", baos);
            imagenBytes = baos.toByteArray();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error con imagen",
                        "Hubo un error para guardar la imagen seleccionada, inténtelo de nuevo",
                        Alert.AlertType.ERROR);
        }
    }

    private void validarCamposRegistro() {
        String nombre = tfNombre.getText();
        String fechaVencimiento = tfFechaVencimiento.getText();
        int cantidad = Integer.parseInt(tfCantidad.getText());
        double precio = Double.parseDouble(tfPrecio.getText());
        String presentacion = tfPresentacion.getText();
        boolean esVentaControlada = ckboxVentaControlada.isSelected();
        int idSucursal = cbSucursal.getValue().getIdSucursal();
        //TODO Validacion
        
        Producto productoValidado = new Producto();
        productoValidado.setCantidad(cantidad);
        productoValidado.setFechaVencimiento(fechaVencimiento);
        productoValidado.setIdSucursal(idSucursal);
        productoValidado.setNombre(nombre);
        productoValidado.setPrecio(precio);
        productoValidado.setPresentacion(presentacion);
        productoValidado.setVentaControlada(esVentaControlada);
        productoValidado.setFoto(imagenBytes);
        registrarProducto(productoValidado);
    }
    
    private void registrarProducto(Producto productoRegistro){
        int codigoRespuesta = ProductoDAO.guardarProducto(productoRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión",
                            "Por el momento no hay conexión, por favor inténtelo más tarde"
                            , Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Hubo un error al cargar la información por favor intentélo de nuevo más tarde",
                            Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Producto Registrado",
                            "El producto fue registrado exitosamente", 
                            Alert.AlertType.INFORMATION);
                    cerrarVentana();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfCantidad.getScene().getWindow();
        escenarioBase.close();
    }
}
