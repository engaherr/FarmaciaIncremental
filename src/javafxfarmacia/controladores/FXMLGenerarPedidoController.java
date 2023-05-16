package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.TipoProductoDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.modelo.pojo.Tipo;
import javafxfarmacia.modelo.pojo.TipoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

public class FXMLGenerarPedidoController implements Initializable {

    @FXML
    private ComboBox<Tipo> cbTipo;

    private ObservableList<Tipo> tipos;
    @FXML
    private ComboBox<?> cbProveedor;
    @FXML
    private ComboBox<Producto> cbProducto;
    
    private ObservableList<Producto> productos;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TableColumn<?, ?> tvCarrito;
    @FXML
    private DatePicker dpDiaEntrega;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionTipo();
        cbTipo.valueProperty().addListener(new ChangeListener<Tipo>() {
            
            @Override
            public void changed(ObservableValue<? extends Tipo> observable, Tipo oldValue, Tipo newValue) {
                if(newValue != null){
                    cargarInformacionProducto(newValue.getIdTipo());
                }
            }
        });
    }

private void cargarInformacionTipo() {
    tipos = FXCollections.observableArrayList();
    TipoRespuesta tiposBD = TipoProductoDAO.obtenerInformacionTipo();
    switch (tiposBD.getCodigoRespuesta()) {
        case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Error de conexión", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
            break;

        case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se puede mostrar la información", Alert.AlertType.WARNING);
            break;

        case Constantes.OPERACION_EXITOSA:
            ArrayList<Tipo> tiposOriginales = tiposBD.getTipos();
            HashSet<String> nombresTipos = new HashSet<>();

            for (Tipo tipo : tiposOriginales) {
                if (nombresTipos.add(tipo.getNombre())) {
                    tipos.add(tipo);
                }
            }
            cbTipo.setItems(tipos);
            break;
    }
}

private void cargarInformacionProducto(int idProducto) {
    productos = FXCollections.observableArrayList();
    ProductoRespuesta productosBD = ProductoDAO.obtenerInformacionProducto();
    switch (productosBD.getCodigoRespuesta()) {
        case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Error de conexión", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
            break;

        case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se puede mostrar la información", Alert.AlertType.WARNING);
            break;

        case Constantes.OPERACION_EXITOSA:
            ArrayList<Producto> productosOriginales = productosBD.getProductos();
            HashSet<String> nombresProductos = new HashSet<>();

            for (Producto producto : productosOriginales) {
                if (nombresProductos.add(producto.getNombre())) {
                    productos.add(producto);
                }
            }
            cbProducto.setItems(productos);
            break;
    }
}



    @FXML
    private void clicAgregar(ActionEvent event) {
    }

    @FXML
    private void clicGenerar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }



}
