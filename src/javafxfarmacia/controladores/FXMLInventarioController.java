/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxfarmacia.JavaFXFarmacia;
import javafxfarmacia.interfaz.INotificacionOperacion;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

public class FXMLInventarioController implements Initializable, INotificacionOperacion {

    @FXML
    private TableView<Producto> tvInventario;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colFechaVencimiento;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colSucursal;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TextField tfBusqueda;
    
    private ObservableList<Producto> productos;
    private SortedList<Producto> sortedListProductos;
    
    @FXML
    private TableColumn colPresentacion;
    @FXML
    private TableColumn<Producto, Boolean> colControlada;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        
        tvInventario.setOnMouseClicked(event -> {
            Producto productoSeleccionado = tvInventario.getSelectionModel().getSelectedItem();
            if(event.getClickCount() == 2){
                if(productoSeleccionado != null){
                    mostrarDetalles(productoSeleccionado);
                }
            } else if(event.getClickCount() == 1){
                if(productoSeleccionado != null){
                    btnModificar.setDisable(false);
                    btnEliminar.setDisable(false);
                }
            }
        });
    }    
    
    public void configurarTabla(){
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colFechaVencimiento.setCellValueFactory(new PropertyValueFactory("fechaVencimiento"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colSucursal.setCellValueFactory(new PropertyValueFactory("nombreSucursal"));
        colControlada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, 
                Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue().isVentaControlada());
            }
        });
        colControlada.setCellFactory(column -> new TableCell<Producto, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(item ? "Sí" : "No");
                }
            }
        });
        colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        tvInventario.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, 
                    Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvInventario.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable,
                            Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    public void cargarInformacionTabla(){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta respuestaBD = ProductoDAO.obtenerInformacionProducto();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", 
                        "No se pudo conectar con la base de datos. Intente de nuevo o hágalo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información por favor inténtelo de nuevo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                productos.addAll(respuestaBD.getProductos());
                tvInventario.setItems(productos);
                configurarBusquedaTabla();
                break;
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        int posicion = tvInventario.getSelectionModel().getSelectedIndex();
        boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion("Eliminar Producto", 
                "¿Eliminar Artículo del inventario?");
        if(borrarRegistro){
            int codigoRespuesta = ProductoDAO.eliminarProducto(sortedListProductos.get(posicion).getIdProducto());
            switch(codigoRespuesta){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de Conexión",
                            "El artículo no pudo ser eliminado debido a un error de conexión "
                                    + "verifique su conexión e inténtelo más tarde",
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al eliminar",
                            "El artículo no pudo ser elimado, por favor inténtelo más tarde",
                            Alert.AlertType.WARNING);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Producto eliminado",
                            "El artículo fue eliminado exitosamente del inventario",
                            Alert.AlertType.INFORMATION);
                    cargarInformacionTabla();
                    break;
            }
            
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    private void configurarBusquedaTabla(){
        if(!productos.isEmpty()){
            FilteredList<Producto> filtradoProductos = new FilteredList<>(productos, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    filtradoProductos.setPredicate(productoFiltro -> {
                        
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        
                        String lowerNewValue = newValue.toLowerCase();
                        if(productoFiltro.getNombre().toLowerCase().contains(lowerNewValue))
                            return true;
                        else if(productoFiltro.getPresentacion().toLowerCase().contains(lowerNewValue))
                            return true;
                        else if(productoFiltro.getNombreSucursal().toLowerCase().contains(lowerNewValue))
                            return true;
                        return false;
                    });
                }
            });
            sortedListProductos = new SortedList<>(filtradoProductos);
            sortedListProductos.comparatorProperty().bind(tvInventario.comparatorProperty());
            tvInventario.setItems(sortedListProductos);
        }
    }

    private void irFormulario(boolean esEdicion, Producto productoEdicion) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXFarmacia.class.getResource("vistas/FXMLProductoFormulario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLProductoFormularioController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion, productoEdicion, this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario de Producto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException e) {
            e.getMessage();
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void mostrarDetalles(Producto productoSeleccion) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(
                    JavaFXFarmacia.class.getResource("vistas/FXMLProductoDetalles.fxml"));
            Parent vista = accesoControlador.load();
            FXMLProductoDetallesController detalles = accesoControlador.getController();
            detalles.inicializarInformacionDetalles(productoSeleccion);
            
            Stage escenarioDetalles = new Stage();
            escenarioDetalles.setScene(new Scene(vista));
            escenarioDetalles.setTitle("Detalles de Producto");
            escenarioDetalles.initModality(Modality.APPLICATION_MODAL);
            escenarioDetalles.showAndWait();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void notificarOperacionGuardar() {
        cargarInformacionTabla();
    }

    @Override
    public void notificarOperacionEditar() {
        cargarInformacionTabla();
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        irFormulario(false, null);
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        int posicion = tvInventario.getSelectionModel().getSelectedIndex();
        if(posicion != -1)
            irFormulario(true, sortedListProductos.get(posicion));
    }
}
