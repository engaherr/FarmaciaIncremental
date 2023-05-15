/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.SucursalDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.Sucursal;
import javafxfarmacia.modelo.pojo.SucursalRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

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
    private TextField tfFechaVencimiento;
    @FXML
    private TextField tfCantidad;
    @FXML
    private CheckBox ckboxVentaControlada;
    @FXML
    private TextField tfPresentacion;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfPrecio;
    
    private ObservableList<Sucursal> sucursales;
    private boolean esEdicion;
    private Producto productoEdicion;

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
        Stage escenarioBase = (Stage) tfCantidad.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
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
                break;
        }
    }
    
}
