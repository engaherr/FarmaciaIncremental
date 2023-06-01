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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    private ComboBox<Producto> cbProductos;
    

    @FXML
    private TextField tfDescripcionPromo;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfPrecioFinal;

    @FXML
    private ImageView imagenPromocion;
    private File archivoFoto;
    
    @FXML
    private Label rutaImagen;
    @FXML
    private Label lbPrecioFinal;
    
    private ObservableList<Producto> productos;
    private ObservableList<PromocionProducto> carrito;
    private ObservableList<PromocionProducto> productosOriginales;
    

    @FXML
    private TableView<PromocionProducto> tvProductosdePromocion;

        @FXML
    private TableColumn tcNombreProducto;
    @FXML
    private TableColumn tcCantidadProducto;
    @FXML
    private TableColumn tcPrecioUnitario;
    @FXML
    private TableColumn tcPrecioFinal;
    
    
    @FXML
    private TextField tfPrecioActualUnidad;
    
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnNuevoProducto;
    @FXML
    private Button btnAñadirProducto;
    
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaTermino;
    @FXML
    private Text txTitulo;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal;
    private LocalDate fechaAnteriorInicio;
    private LocalDate fechaAnteriorTermino;

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
        productosOriginales = FXCollections.observableArrayList();
        
        tfPrecioActualUnidad.setEditable(false);
        dpFechaInicio.setEditable(false);
        dpFechaTermino.setEditable(false);
        
        estiloNormal = tfDescripcionPromo.getStyle();
        
        formatearTextFieldNumerico(tfCantidad);
        formatearTextFieldFlotante(tfPrecioFinal);
        
        dpFechaInicio.setOnAction(event -> {
            LocalDate dateSeleccionada = dpFechaInicio.getValue();
            LocalDate dateActual = LocalDate.now();
            
            if(dateSeleccionada != null && dateSeleccionada.isBefore(dateActual)){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la actual"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                   dpFechaInicio.setValue(null);
                }else{
                    dpFechaInicio.setValue(fechaAnteriorInicio);
                }
            }
            
            if (dateSeleccionada != null && dateSeleccionada.getYear() > LocalDate.now().getYear()) {
                Utilidades.mostrarDialogoSimple("Año inválido", "Solo se aceptan promociones con el año en curso", Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaInicio.setValue(null);
                }else{
                    dpFechaInicio.setValue(fechaAnteriorInicio);
                }                
            }
        });
 
        dpFechaTermino.setOnAction(event -> {
            LocalDate dateSeleccionada = dpFechaTermino.getValue();
            LocalDate dateInicio = dpFechaInicio.getValue();
            
            if(dateSeleccionada != null && dateSeleccionada.isBefore(dateInicio)){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la del inicio de la promoción"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaTermino.setValue(null);
                }else{
                    dpFechaTermino.setValue(fechaAnteriorTermino);

                }
            }
            
            if(dateSeleccionada != null && dateSeleccionada.getMonth() != dateInicio.getMonth()){
                Utilidades.mostrarDialogoSimple("Fecha no válida",
                        "Seleccione una fecha posterior a la del inicio de la promoción"
                        , Alert.AlertType.WARNING);
                if(fechaAnteriorInicio == null){
                    dpFechaTermino.setValue(null);
                }else{
                    dpFechaTermino.setValue(fechaAnteriorTermino);

                }                
            }
        });

        
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
            txTitulo.setText("Edición de promoción");
        }
    }
    
    private void cargarInformacionPromocion(){
        tfDescripcionPromo.setText(promocion.getDescripcion());
        dpFechaInicio.setValue(LocalDate.parse(promocion.getFechaInicio()));
        dpFechaTermino.setValue(LocalDate.parse(promocion.getFechaTermino()));
        
        fechaAnteriorInicio = dpFechaInicio.getValue();
        fechaAnteriorTermino = dpFechaTermino.getValue();
        
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
        
        productosOriginales.addAll(promocion.getProductos().getPromocionesProductoRespuesta());
              
    }

    @FXML
    private void clicRegistrarPromocion(ActionEvent event) {
        validarCamposRegistro();
    }
    
    
 private void validarCamposRegistro() {
        tfDescripcionPromo.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaTermino.setStyle(estiloNormal);
        tvProductosdePromocion.setStyle(estiloNormal);
        
        boolean esValido = true;
        
        String descripcion = null; 
        if(!tfDescripcionPromo.getText().trim().isEmpty()){
            descripcion = tfDescripcionPromo.getText();
        }else{
            esValido = false;
            tfDescripcionPromo.setStyle(tfDescripcionPromo.getStyle() + estiloError);
        }
        
        String fechaInicio = null;
        if(dpFechaInicio.getValue() != null){
           fechaInicio = dpFechaInicio.getValue().format(DateTimeFormatter.ISO_DATE);
        }else{
            dpFechaInicio.setStyle(estiloError);
            esValido = false;
        }
        
        
        String fechaTermino = null;
        if(dpFechaTermino.getValue() != null){
            fechaTermino = dpFechaTermino.getValue().format(DateTimeFormatter.ISO_DATE);
        }else{
            dpFechaTermino.setStyle(estiloError);
            esValido = false;
        }
        
        if(carrito.isEmpty()){
            tvProductosdePromocion.setStyle(estiloError);
            esValido = false;
        }
        
        if(esValido == true){
            Promocion promocionValida = new Promocion();
            promocionValida.setDescripcion(descripcion);
            promocionValida.setFechaInicio(fechaInicio);
            promocionValida.setFechaTermino(fechaTermino);

            try {
                if(esEdicion){
                    if(archivoFoto != null){
                        promocionValida.setImagen(Files.readAllBytes(archivoFoto.toPath()));
                    }else{
                        promocionValida.setImagen(promocion.getImagen());
                    }
                    promocionValida.setIdPromocion(promocion.getIdPromocion());
                    actualizarPromocion(promocionValida);
                }else{
                    if(archivoFoto != null){
                        promocionValida.setImagen(Files.readAllBytes(archivoFoto.toPath()));
                    }
                    registrarPromocion(promocionValida);
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
                
                    eliminarProductosPromocion();
                    registrarProductosPromocion(promocion.getIdPromocion());
                

                cerrarVentana();
                interfazNotificacion.notificarOperacionEditar();
               

                break;
        }
    }
    
    private void eliminarProductosPromocion(){

            int codigoRespuesta = PromocionProductoDAO.eliminarProductoPromocion(productosOriginales.get(0).getIdPromocion());
            switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                //Utilidades.mostrarDialogoSimple("Error  BD al eliminar los productos de la promoción", "Por el momento no hay conexion, "
                        //+ "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                //Utilidades.mostrarDialogoSimple("Eliminación de productos de promocion hecha", "La eliminación de los productos de la "
                        //+ "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                //Utilidades.mostrarDialogoSimple("Eliminación de productos de promocion hecha", "La eliminación de los productos de la "
                  //      + "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                
                break;
            }
          
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
                cerrarVentana();
                //interfazNotificacion.notificarOperacionActualizar(promocionRegistrar.getDescripcion());

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
        
        Stage escenarioBase = (Stage) lbPrecioFinal.getScene().getWindow();
        archivoFoto = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        visualizarImagen(archivoFoto);
      
             
    }
    
    private void visualizarImagen(File archivoFoto){
        if(archivoFoto != null){
            try{
                BufferedImage bufferImage = ImageIO.read(archivoFoto);
                Image imagenCodificada = SwingFXUtils.toFXImage(bufferImage, null);
                imagenPromocion.setImage(imagenCodificada);
                
                
            }catch(IOException ex){
                System.out.println("Error al tratar de cargar la imagen seleccioanda");
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
        tfCantidad.setStyle(estiloNormal);
        tfPrecioFinal.setStyle(estiloNormal);
        
        boolean esValido = true;
        Producto productoSeleccionado = cbProductos.getSelectionModel().getSelectedItem();
        
        if (productoSeleccionado == null){
            Utilidades.mostrarDialogoSimple("Sin producto seleccionado",
                    "Por favor seleccione un producto para agregar a la promoción", Alert.AlertType.WARNING);
            esValido = false;
        }    
        for(PromocionProducto producto : carrito ){
            if(producto.getIdProducto() == productoSeleccionado.getIdProducto()){
                Utilidades.mostrarDialogoSimple("Producto duplicado","Por favor seleccione otro producto", Alert.AlertType.WARNING);
                esValido = false;
            }
        }    
            
        int cantidad = 0;
        try{
            cantidad = Integer.parseInt(tfCantidad.getText());
            if(cantidad >3){
                Utilidades.mostrarDialogoSimple("Máximo de productos alcanzados", "No se pueden incluir más "
                        + "de 3 unidades del mismo producto en una promoción", Alert.AlertType.WARNING);
                esValido = false;
            }
        }catch(NumberFormatException e){
            esValido = false;
            tfCantidad.setStyle(tfCantidad.getStyle() + estiloError);
        }
              
        double descuento = 0;
        try{
            descuento = Double.parseDouble(tfPrecioFinal.getText());
            if(descuento > 100){
                esValido = false;
                tfPrecioFinal.setStyle(tfPrecioFinal.getStyle() + estiloError);
                Utilidades.mostrarDialogoSimple("Error en la cantidad de descuento",
                        "Por favor ingrese una cantidad de descuento válida", Alert.AlertType.WARNING);
            }
        }catch(NumberFormatException e){
            esValido = false;
            tfPrecioFinal.setStyle(tfPrecioFinal.getStyle() + estiloError);
        }
                
        double descuentoReal = 1 - (descuento / 100);
        double precioUnidad = descuentoReal * productoSeleccionado.getPrecio();
        double precioFinal = 0;
        for(int i = 1; i <= cantidad; i++){
            precioFinal += precioUnidad;
        }
        
        
        if(esValido == true){

            PromocionProducto promocionProducto = new PromocionProducto();
            promocionProducto.setIdProducto(productoSeleccionado.getIdProducto());
            promocionProducto.setNombreProducto(productoSeleccionado.getNombre());
            promocionProducto.setCantidad(cantidad);
            promocionProducto.setPrecioUnitario(productoSeleccionado.getPrecio());
            promocionProducto.setPrecioFinal(precioFinal);
            
            carrito.add(promocionProducto);
            actualizarTablaPromocion();
            limpiarCeldasProducto();
        
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

            if(carrito.size() < 1 ){
                 btnEliminar.setDisable(true);
            }else{
                btnEliminar.setDisable(false);
            }
        }
    }

    @FXML
    private void clicModificarProducto(ActionEvent event) {
        tfCantidad.setStyle(estiloNormal);
        tfPrecioFinal.setStyle(estiloNormal);
        
        int posicion = tvProductosdePromocion.getSelectionModel().getSelectedIndex();
        PromocionProducto productoGuardado = carrito.get(posicion);     
        boolean esValido = true;
        int cantidad = 0;
        try{
            cantidad = Integer.parseInt(tfCantidad.getText());
            if(cantidad >3){
                Utilidades.mostrarDialogoSimple("Máximo de productos alcanzados", "No se pueden incluir más "
                        + "de 3 unidades del mismo producto en una promoción", Alert.AlertType.WARNING);
                esValido = false;
            }
        }catch(NumberFormatException e){
            esValido = false;
            tfCantidad.setStyle(tfCantidad.getStyle() + estiloError);
        }
              
        double descuento = 0;
        try{
            descuento = Double.parseDouble(tfPrecioFinal.getText());
            if(descuento > 100){
                esValido = false;
                tfPrecioFinal.setStyle(tfPrecioFinal.getStyle() + estiloError);
                Utilidades.mostrarDialogoSimple("Error en la cantidad de descuento",
                        "Por favor ingrese una cantidad de descuento válida", Alert.AlertType.WARNING);
            }
        }catch(NumberFormatException e){
            esValido = false;
            tfPrecioFinal.setStyle(tfPrecioFinal.getStyle() + estiloError);
        }
        
        double descuentoReal = 1 - (descuento / 100);
        double precioUnidad = descuentoReal * productoGuardado.getPrecioUnitario();
        double precioFinal = 0;
        for(int i = 1; i <= cantidad; i++){
            precioFinal += precioUnidad;
        }        
        if(esValido == true){           
            
            productoGuardado.setPrecioFinal(precioFinal);
            productoGuardado.setCantidad(cantidad);
            PromocionProducto producNuevo = productoGuardado;
            carrito.remove(posicion);
            carrito.add(producNuevo);
            actualizarTablaPromocion();
            limpiarCeldasProducto();            
        }
               
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
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbPrecioFinal.getScene().getWindow();
        escenarioBase.close();
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
    
}
