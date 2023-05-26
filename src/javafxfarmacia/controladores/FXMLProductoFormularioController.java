/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxfarmacia.interfaz.INotificacionOperacion;
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
    private File imagenSeleccionada;
    
    private INotificacionOperacion interfazNotificacion;
    @FXML
    private DatePicker dpFechaVencimiento;

    @Override    
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionSucursal();
        formatearTextFieldNumerico(tfCantidad);
        formatearTextFieldNoNumerico(tfPresentacion);
        formatearTextFieldFlotante(tfPrecio);
        dpFechaVencimiento.setOnAction(event -> {
            LocalDate dateSeleccionada = dpFechaVencimiento.getValue();
            LocalDate dateActual = LocalDate.now();
            
            if(dateSeleccionada != null && dateSeleccionada.isBefore(dateActual)){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la actual"
                        , Alert.AlertType.WARNING);
                dpFechaVencimiento.setValue(null);
            }
        });
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, 
            Producto producto, INotificacionOperacion interfazNotificacion){
        this.interfazNotificacion = interfazNotificacion;
        this.esEdicion = esEdicion;
        this.productoEdicion = producto;
        if(esEdicion){
            lbTitulo.setText("Editar Información de " + producto.getNombre());
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar Producto");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombre.setText(productoEdicion.getNombre());
        tfNombre.setEditable(false);
        tfCantidad.setText(Integer.toString(productoEdicion.getCantidad()));
        if(productoEdicion.getFechaVencimiento() != null)
            dpFechaVencimiento.setValue(LocalDate.parse(productoEdicion.getFechaVencimiento()));
        else
            dpFechaVencimiento.setValue(null);
        tfPrecio.setText(Double.toString(productoEdicion.getPrecio()));
        if(!"N/A".equals(productoEdicion.getPresentacion()))
            tfPresentacion.setText(productoEdicion.getPresentacion());
        cbSucursal.getSelectionModel().select(obtenerPosicionComboSucursal(
                productoEdicion.getIdSucursal()));
        ckboxVentaControlada.setSelected(productoEdicion.isVentaControlada());
        
        try {
            ByteArrayInputStream baisFoto = new ByteArrayInputStream(productoEdicion.getFoto());
            Image imgFoto = new Image(baisFoto);
            ivFoto.setImage(imgFoto);
        } catch (Exception e) {
            e.printStackTrace();
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
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        
        if(imagenSeleccionada != null){
            try {
                BufferedImage bufferImg = ImageIO.read(imagenSeleccionada);
                Image imgDecodificada = SwingFXUtils.toFXImage(bufferImg, null);
                ivFoto.setImage(imgDecodificada);
            } catch (IOException ex) {
                Utilidades.mostrarDialogoSimple("Error con imagen",
                        "Hubo un error para mostrar la imagen seleccionada, inténtelo de nuevo",
                        Alert.AlertType.ERROR);
            }
        }
    }
    
    private void validarCamposRegistro() {
        boolean esValido = true;
        String nombre = null; 
        if(!tfNombre.getText().trim().isEmpty()){
            nombre = tfNombre.getText();
        }else{
            esValido = false;
        }
        int cantidad = 0;
        try{
            cantidad = Integer.parseInt(tfCantidad.getText());
        }catch(NumberFormatException e){
            esValido = false;
        }
        double precio = 0;
        try{
            precio = Double.parseDouble(tfPrecio.getText());
            if(precio <= 0)
                esValido = false;
        }catch(NumberFormatException e){
            esValido = false;
        }
        String fechaVencimiento = null;
        if(dpFechaVencimiento.getValue() != null)
            fechaVencimiento = dpFechaVencimiento.getValue().format(DateTimeFormatter.ISO_DATE);
        String presentacion = "N/A";
        if(!tfPresentacion.getText().isEmpty())
            presentacion = tfPresentacion.getText();
        boolean esVentaControlada = ckboxVentaControlada.isSelected();
        int idSucursal = 0;
        if(!cbSucursal.getSelectionModel().isEmpty())
            idSucursal = cbSucursal.getValue().getIdSucursal();
        else
            esValido = false;
        
        if(esValido == true){
            Producto productoValidado = new Producto();
            productoValidado.setCantidad(cantidad);
            productoValidado.setFechaVencimiento(fechaVencimiento);
            productoValidado.setIdSucursal(idSucursal);
            productoValidado.setNombre(nombre);
            productoValidado.setPrecio(precio);
            productoValidado.setPresentacion(presentacion);
            productoValidado.setVentaControlada(esVentaControlada);

            try {
                if(esEdicion){
                    if(imagenSeleccionada != null){
                        productoValidado.setFoto(Files.readAllBytes(imagenSeleccionada.toPath()));
                    }else{
                        productoValidado.setFoto(productoEdicion.getFoto());
                    }
                    productoValidado.setIdProducto(productoEdicion.getIdProducto());
                    actualizarProducto(productoValidado);
                }else{
                    if(imagenSeleccionada != null){
                        productoValidado.setFoto(Files.readAllBytes(imagenSeleccionada.toPath()));
                    }
                    registrarProducto(productoValidado);
                }
            } catch (IOException e) {
                Utilidades.mostrarDialogoSimple("Error con imagen",
                        "Hubo un error para guardar la imagen seleccionada, inténtelo de nuevo",
                        Alert.AlertType.ERROR);
            }
        }else{
           Utilidades.mostrarDialogoSimple("Campos Vacíos",
                   "Por favor ingrese información o valores en los campos obligatorios",
                   Alert.AlertType.WARNING);
        }
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
                        interfazNotificacion.notificarOperacionGuardar();
                        cerrarVentana();
                    break;
            }
    }
    
    private void actualizarProducto(Producto productoActualizar){
        int codigoRespuesta = ProductoDAO.modificarProducto(productoActualizar);
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
                    Utilidades.mostrarDialogoSimple("Producto Actualizado",
                            "La información del producto fue modificada correctamente", 
                            Alert.AlertType.INFORMATION);        
                    interfazNotificacion.notificarOperacionGuardar();
                    cerrarVentana();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfCantidad.getScene().getWindow();
        escenarioBase.close();
    }
    
    private int obtenerPosicionComboSucursal(int idSucursal){
        for(int i = 0; i < sucursales.size(); i++){
            if(sucursales.get(i).getIdSucursal() == idSucursal)
                return i;
        }
        return 0;
    }
    
    private void formatearTextFieldNumerico(TextField tfNumerico) {
        tfNumerico.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNumerico.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            if (tfNumerico.getText().equals("0")) {
                tfNumerico.setText("1");
            }
        });
    }
    
    private void formatearTextFieldFlotante(TextField tfFlotante){
        tfFlotante.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d{0," + 2 + "}")) {
                tfFlotante.setText(oldValue);
            }
        });
    }
    
    private void formatearTextFieldNoNumerico(TextField tfNoNumerico) {
        tfNoNumerico.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                tfNoNumerico.setText(newValue.replaceAll("[^a-zA-Z]", ""));
            }
        });
    }
}
