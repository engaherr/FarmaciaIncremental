/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxfarmacia.JavaFXFarmacia;
import javafxfarmacia.interfaz.INotificacionOperacion;
import javafxfarmacia.modelo.dao.PromocionDAO;
import javafxfarmacia.modelo.dao.PromocionProductoDAO;
import javafxfarmacia.modelo.pojo.Promocion;
import javafxfarmacia.modelo.pojo.PromocionProducto;
import javafxfarmacia.modelo.pojo.PromocionRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jasie
 */
public class FXMLPromocionesController implements Initializable, INotificacionOperacion {
    @FXML
    private TableView<Promocion> tvPromociones;
    @FXML
    private TableColumn colDescripcion;
    @FXML
  
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colfechaTermino;
    
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colPrecioFinal;
    
    private ObservableList <Promocion> promociones;
    private ObservableList <Promocion> promocionesBusqueda;
    @FXML
    private TextField tfBusqueda;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();
 
    }  
    
    private void configurarTabla(){
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colProducto.setCellValueFactory(new PropertyValueFactory("productosPromo"));
        colPrecioFinal.setCellValueFactory(new PropertyValueFactory("precioFinal"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colfechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));

        
    }
    private void cargarInformacion(){
        promociones = FXCollections.observableArrayList();
        PromocionRespuesta respuestaBD = PromocionDAO.obtenerInformacionPromociones();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, por "
                        + "el momento no hay conexion", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar los datos"
                        + " inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                promociones.addAll(respuestaBD.getPromociones());
                tvPromociones.setItems(promociones);
                configurarBusquedaTabla();
                break;
             
                
             
        }
    }
    
 

    @FXML
    private void clicRegistrarPromocion(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializaEscena("vistas/FXMLRegistroPromocion.fxml"));
        escenarioFormulario.setTitle("Formulario");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();
    }

    @FXML
    private void clicModiificarPromocion(ActionEvent event) {
        Promocion promocionSeleccionada = tvPromociones.getSelectionModel().getSelectedItem();
        if(promocionSeleccionada != null){
            irFormulario(true,promocionSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Atención","Por favor selecciona "
                    + "una promoción para poder editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicEliminarPromocion(ActionEvent event) {
        Promocion promocionSeleccionada = tvPromociones.getSelectionModel().getSelectedItem(); 
        if(promocionSeleccionada != null){
            ArrayList<PromocionProducto> productosBorrar = promocionSeleccionada.getProductos().getPromocionesProductoRespuesta();

            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion("Confirmar eliminacion"
                    ,"¿Estás seguro de eliminar la promoción con los productos "+promocionSeleccionada.getProductosPromo()+" ?");
            if(borrarRegistro){
                eliminarProductosPromocion(productosBorrar);
                int codigoRespuesta = PromocionDAO.eliminarPromocion(promocionSeleccionada.getIdPromocion());
                switch(codigoRespuesta){
                                
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Lo sentimos, "
                        + "por el momento no hay conexión", Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al realizar la operación",
                        "Hubo un error al realizar la eliminación, por favor inténtelo más tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Operación realizada",
                        "La eliminación de la promoción se realizó de forma exitosa",
                        Alert.AlertType.INFORMATION);
                cargarInformacion();
                break;
                }
            }
            
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un alumno", "Para eliminar un "
                    + "alumno debes seleccionarlo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }
    
    
    private void eliminarProductosPromocion(ArrayList<PromocionProducto> producEliminar){
            int codigoRespuesta = PromocionProductoDAO.eliminarProductoPromocion(producEliminar.get(0).getIdPromocion());
            switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error  BD al eliminar los productos de la promoción", "Por el momento no hay conexion, "
                        + "por favor inténtalos más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en el proceso", "Hubo un problema al intentar "
                        + "borrar los productos de la promoción", Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Eliminación de productos de promocion hecha", "La eliminación de los productos de la "
                        + "promoción se realizó con éxito", Alert.AlertType.INFORMATION);
                
                break;
            }
        
    }

    @FXML
    private void clicVolverVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    private void irFormulario(boolean esEdicion, Promocion promocion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXFarmacia.class.getResource("vistas/FXMLRegistroPromocion.fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLRegistroPromocionController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion,promocion,this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario: actualización");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLRegistroPromocionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacionGuardar(String descripcionPromocion) {
        Utilidades.mostrarDialogoSimple("Notificacion","Se registró de forma "
                + "exitosa la promoción", Alert.AlertType.INFORMATION);
        cargarInformacion();
    }

    @Override
    public void notificarOperacionActualizar(String descripcionPromocion) {
        Utilidades.mostrarDialogoSimple("Notificación","Se actualizaron "
                + "los datos de la promocion", Alert.AlertType.INFORMATION);
        cargarInformacion();
    }
    
  
    private void configurarBusquedaTabla (){
        if(!promociones.isEmpty()){
            FilteredList<Promocion> filtradoPromociones = new FilteredList<>(promociones,p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtradoPromociones.setPredicate(promocionFiltro -> {
                        
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //criterio de busqueda
                        String lowerNewValue = newValue.toLowerCase();
                        if(promocionFiltro.getDescripcion().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }else if(promocionFiltro.getProductosPromo().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        return false;
                    });
                    
                }
            });
            SortedList<Promocion> sortedListaPromociones = new SortedList<>(filtradoPromociones);
            sortedListaPromociones.comparatorProperty().bind(tvPromociones.comparatorProperty());
            tvPromociones.setItems(sortedListaPromociones);
        
        }
    }


    
}
