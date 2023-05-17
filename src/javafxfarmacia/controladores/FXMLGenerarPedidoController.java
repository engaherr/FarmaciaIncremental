package javafxfarmacia.controladores;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.net.URL;
import java.time.LocalDate;
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
import java.util.function.Function;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.util.StringConverter;
import javafxfarmacia.modelo.dao.PedidoDAO;
import javafxfarmacia.modelo.pojo.Pedido;
import javafxfarmacia.modelo.pojo.PedidoRespuesta;


public class FXMLGenerarPedidoController implements Initializable {

    private ObservableList<Tipo> tipos;
    @FXML
    private ComboBox<Pedido> cbProveedor;
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
    @FXML
    private RadioButton rbInternos;
    @FXML
    private RadioButton rbExternos;
    private ObservableList<Pedido> proveedoresInternos;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        proveedoresInternos = FXCollections.observableArrayList();
    // Obtener los proveedores internos utilizando PedidoDAO
    PedidoRespuesta respuesta = PedidoDAO.obtenerProveedoresInternos();
    if (respuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        ArrayList<Pedido> proveedoresInternosList = respuesta.getPedidos();
        proveedoresInternos.addAll(proveedoresInternosList);
    } else {
Utilidades.mostrarDialogoSimple("Error de conexión", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
    }
        makeComboBoxSearchable(cbProducto, Producto::toString);
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

        cbProveedor.setConverter(new StringConverter<Pedido>() {
    @Override
    public String toString(Pedido pedido) {
        return pedido != null ? pedido.getNombre() : "";
    }

    @Override
    public Pedido fromString(String string) {
        // Implementación necesaria solo si se requiere la edición del ComboBox
        return null;
    }
    
    });
        // Agrega el listener para la búsqueda del producto
        tfBusqueda.setOnKeyReleased(this::buscarProducto);
        cbProveedor.setItems(proveedoresInternos);

    }

    private void cargarInformacionProducto(int idProducto) {
        productos = FXCollections.observableArrayList();
        ProductoRespuesta productosBD = ProductoDAO.obtenerInformacionProducto(idProducto);
        switch (productosBD.getCodigoRespuesta
()) {
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
    txTotal.setText("$" + String.valueOf(sumaPrecios) + " mxn");
}

@FXML
private void clicGenerar(ActionEvent event) {
     // Obtener la fecha de entrega seleccionada
    LocalDate fechaEntrega = dpDiaEntrega.getValue();
    
    // Verificar que se haya seleccionado una fecha de entrega
    if (fechaEntrega == null) {
        Utilidades.mostrarDialogoSimple("Error", "Por favor, seleccione una fecha de entrega", Alert.AlertType.ERROR);
        return;
    }
    
    // Crear el objeto Pedido con la fecha de pedido y fecha de entrega
    Pedido pedidoNuevo = new Pedido();
    pedidoNuevo.setFecha_pedido(Utilidades.obtenerFechaActual());
    pedidoNuevo.setFecha_entrega(fechaEntrega.toString());
    
    // Obtener los productos del carrito
    ObservableList<Producto> productosCarrito = tvCarrito.getItems();
    
    // Verificar que el carrito no esté vacío
    if (productosCarrito.isEmpty()) {
        Utilidades.mostrarDialogoSimple("Error", "El carrito está vacío", Alert.AlertType.ERROR);
        return;
    }
    
    // Guardar cada producto del carrito en la base de datos
    for (Producto producto : productosCarrito) {
        pedidoNuevo.setCantidad(producto.getCantidad());
        pedidoNuevo.setIdProducto(producto.getIdProducto());
        
        registrarPedido(pedidoNuevo);
    }
    
    // Limpiar el carrito
    carrito.clear();
    actualizarTablaCarrito();
    
    // Mostrar mensaje de éxito
    Utilidades.mostrarDialogoSimple("Pedido Generado", "El pedido ha sido generado exitosamente", Alert.AlertType.INFORMATION);
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
    switch (

respuestaBD.getCodigoRespuesta()) {
case Constantes.ERROR_CONEXION:
Utilidades.mostrarDialogoSimple("Sin conexión",
"No se pudo conectar con la base de datos. Intente de nuevo o hágalo más tarde",
Alert.AlertType.ERROR);
break;
case Constantes.ERROR_CONSULTA:
Utilidades.mostrarDialogoSimple("Error al cargar los datos",
"Hubo un error al cargar la información, por favor inténtelo de nuevo más tarde",
Alert.AlertType.WARNING);
break;
case Constantes.OPERACION_EXITOSA:
productosBusqueda.addAll(respuestaBD.getProductos());
cbProducto.setItems(FXCollections.observableList(productosBusqueda));
break;
}
}



public static <T> void makeComboBoxSearchable(ComboBox<T> comboBox, Function<T, String> toString) {
    comboBox.setConverter(new StringConverter<T>() {
        @Override
        public String toString(T t) {
            return t == null ? "" : toString.apply(t);
        }

        @Override
        public T fromString(String s) {
            return comboBox.getItems().stream()
                    .filter(item -> toString.apply(item).equalsIgnoreCase(s))
                    .findFirst()
                    .orElse(null);
        }
    });

    comboBox.setEditable(true);

    final FilteredList<T> filteredItems = new FilteredList<>(comboBox.getItems(), item -> true);
    comboBox.setItems(filteredItems);

    comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
        final T selected = comboBox.getSelectionModel().getSelectedItem();
        final TextField editor = comboBox.getEditor();

        Platform.runLater(() -> {
            if (selected == null || !toString.apply(selected).equalsIgnoreCase(editor.getText())) {
                filteredItems.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    return toString.apply(item).toLowerCase().contains(newValue.toLowerCase());
                });
            }
        });
    });
}

 private void registrarPedido(Pedido pedidoNuevo){
       int codigoRespuesta = PedidoDAO.guardarPedido(pedidoNuevo);
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