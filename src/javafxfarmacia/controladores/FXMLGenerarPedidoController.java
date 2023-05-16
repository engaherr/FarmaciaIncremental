package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafxfarmacia.modelo.dao.ProductoDAO;
import javafxfarmacia.modelo.dao.TipoProductoDAO;
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.modelo.pojo.Tipo;
import javafxfarmacia.modelo.pojo.TipoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

public class FXMLGenerarPedidoController implements Initializable {


    private ObservableList<Tipo> tipos;
    @FXML
    private ComboBox<?> cbProveedor;
    @FXML
    private ComboBox<Producto> cbProducto;
    
    private ObservableList<Producto> productos;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TableView<Producto> tvCarrito;
    @FXML
    private DatePicker dpDiaEntrega;
@FXML
private TextField tfBusqueda;
private ObservableList<Producto> productosBusqueda;

private ObservableList<Producto> carrito;

    @FXML
    private TableColumn<Producto, Integer> tcCantidad;
    @FXML
    private TableColumn<Producto, String> tcProducto;
    @FXML
    private TableColumn<Producto, Float> tcPrecioUnidad;
    @FXML
    private TableColumn<Producto, Float> tcPrecioFinal;
    @FXML
    private Label txTotal;
    @FXML
    private Button btnEliminar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        cargarInformacionProducto(0);
        carrito = FXCollections.observableArrayList();
  tcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    tcProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    tcPrecioUnidad.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
    tcPrecioFinal.setCellValueFactory(new PropertyValueFactory<>("precioFinal"));
    btnEliminar.setDisable(true);
    tvCarrito.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Producto>() {
    @Override
    public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
        // Habilita el botón "Eliminar" solo si hay una fila seleccionada
        btnEliminar.setDisable(newValue == null);
    }
});

   
    }



private void cargarInformacionProducto(int idProducto) {
    productos = FXCollections.observableArrayList();
    ProductoRespuesta productosBD = ProductoDAO.obtenerInformacionProducto( idProducto);
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
    Producto productoSeleccionado = cbProducto.getSelectionModel().getSelectedItem();
    int cantidad = Integer.parseInt(tfCantidad.getText());
   
    if (productoSeleccionado != null && cantidad > 0) {
        // Crear una nueva instancia de Producto con los mismos datos
        Producto productoEnCarrito = new Producto();
        productoEnCarrito.setIdProducto(productoSeleccionado.getIdProducto());
        productoEnCarrito.setNombre(productoSeleccionado.getNombre());
        productoEnCarrito.setPrecio(productoSeleccionado.getPrecio());
        productoEnCarrito.setCantidad(cantidad);
        
        // Calcular el precio unitario y el precio final
        float precioUnitario = productoSeleccionado.getPrecio();
        float precioFinal = precioUnitario * cantidad;
        productoEnCarrito.setPrecioUnitario(precioUnitario);
        productoEnCarrito.setPrecioFinal(precioFinal);
        
        carrito.add(productoEnCarrito);
       
        actualizarTablaCarrito();
        
    
        tfCantidad.clear();


        cbProducto.getSelectionModel().clearSelection();

    }
}




private void actualizarTablaCarrito() {
    // Crear una lista observable a partir de la lista 'carrito'
    ObservableList<Producto> listaCarrito = FXCollections.observableArrayList(carrito);

    // Calcular el precio final para cada producto y actualizar la lista 'carrito'
    for (Producto producto : listaCarrito) {
        float precioFinal = producto.getPrecioUnitario() * producto.getCantidad();
        producto.setPrecioFinal(precioFinal);
    }

    // Asignar la lista observable a la tabla
    tvCarrito.setItems(listaCarrito);
    
    float sumaPrecios = 0;

// Calcular la suma de los precios finales
for (Producto producto : listaCarrito) {
    sumaPrecios += producto.getPrecioFinal();
}

// Mostrar la suma en el Label txTotal
txTotal.setText("$"+String.valueOf(sumaPrecios)+" mxn");

}




    @FXML
    private void clicGenerar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
         Producto productoSeleccionado = tvCarrito.getSelectionModel().getSelectedItem();
    if (productoSeleccionado != null) {
        // Elimina el producto seleccionado de la tabla
        tvCarrito.getItems().remove(productoSeleccionado);

        // Elimina el producto seleccionado del listado 'carrito'
        carrito.remove(productoSeleccionado);
        
        // Deshabilita el botón "Eliminar" después de la eliminación
        btnEliminar.setDisable(true);
        }
    }
    
    
private void buscarProducto(KeyEvent event) {
        String busqueda = tfBusqueda.getText();
        productosBusqueda = FXCollections.observableArrayList();
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
                cbProducto.setItems(FXCollections.observableList(productosBusqueda));
                break;
        }
    }


    
}
