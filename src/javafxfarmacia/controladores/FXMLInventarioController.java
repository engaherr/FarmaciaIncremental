/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

public class FXMLInventarioController implements Initializable {

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
    
    @FXML
    private TableColumn colPresentacion;
    @FXML
    private TableColumn<Producto, Boolean> colControlada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    public void configurarTabla(){
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colFechaVencimiento.setCellValueFactory(new PropertyValueFactory("fechaVencimiento"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colSucursal.setCellValueFactory(new PropertyValueFactory("nombreSucursal"));
        colControlada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
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
                break;
        }
    }


    @FXML
    private void clicRegistrar(ActionEvent event) {
    }

    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void buscarProducto(KeyEvent event) {
        String busqueda = tfBusqueda.getText();
        List<Producto> productosBusqueda = null;
        ProductoRespuesta respuestaBD = ProductoDAO.obtenerInformacionBusqueda(busqueda);
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
                productosBusqueda.addAll(respuestaBD.getProductos());
                tvInventario.setItems(FXCollections.observableList(productosBusqueda));
                break;
        }
    }
}
