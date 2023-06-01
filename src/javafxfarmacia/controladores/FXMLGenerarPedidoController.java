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
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;
import java.util.stream.Collectors;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxfarmacia.interfaz.INotificacionOperacion;
import javafxfarmacia.modelo.dao.PedidoDAO;
import static javafxfarmacia.modelo.dao.PedidoDAO.guardarPedidoExterno;
import static javafxfarmacia.modelo.dao.PedidoDAO.guardarPedidoInterno;
import javafxfarmacia.modelo.dao.ProductoPedidoDAO;
import javafxfarmacia.modelo.pojo.Pedido;
import javafxfarmacia.modelo.pojo.PedidoRespuesta;
import javafxfarmacia.modelo.pojo.ProductoPedido;
import org.controlsfx.control.textfield.TextFields;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;




public class FXMLGenerarPedidoController implements Initializable {

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
    private ObservableList<Pedido> proveedoresExternos;
    private ObservableList<Pedido> todosLosProveedores;
    
    private ObservableList<String> possibleSuggestions;


    @FXML
    private TextField autoTextField;
    private boolean esEdicion;
    private Pedido pedido;
    private INotificacionOperacion interfazNotificacion;
    private Set<String> productosEnCarrito;


    
    @Override
    
     public void initialize(URL url, ResourceBundle rb) {
    ProductoRespuesta respuestaProductos = ProductoDAO.obtenerInformacionProducto(0);
    if (respuestaProductos.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        productos = FXCollections.observableArrayList();
        productos.addAll(respuestaProductos.getProductos());
        productosEnCarrito = new HashSet<>();


 
        possibleSuggestions = FXCollections.observableArrayList(
            productos.stream()
                .map(Producto::getNombre)
                .collect(Collectors.toList())
        );

  
        TextFields.bindAutoCompletion(autoTextField, possibleSuggestions);
    }

        rbInternos.setSelected(true);
        proveedoresInternos = FXCollections.observableArrayList();
        

    PedidoRespuesta respuesta = PedidoDAO.obtenerProveedoresInternos();
    if (respuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        ArrayList<Pedido> proveedoresInternosList = respuesta.getPedidos();
        proveedoresInternos.addAll(proveedoresInternosList);
    } else {
        Utilidades.mostrarDialogoSimple("Error de conexión", 
                "Error de conexión con la base de datos", Alert.AlertType.ERROR);
    }
    
    proveedoresExternos = FXCollections.observableArrayList();
PedidoRespuesta respuestaExternos = PedidoDAO.obtenerProveedoresExternos();
if (respuestaExternos.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
    ArrayList<Pedido> proveedoresExternosList = respuestaExternos.getPedidos();
    proveedoresExternos.addAll(proveedoresExternosList);
} else {
    Utilidades.mostrarDialogoSimple("Error de conexión", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
}
  
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
              
                btnEliminar.setDisable(newValue == null);
            }
        });

        cbProveedor.setConverter(new StringConverter<Pedido>() {
    @Override
    public String toString(Pedido pedido) {
        return pedido != null ? pedido.getNombre_proveedor(): "";
    }

    @Override
    public Pedido fromString(String string) {
     
        return null;
    }
    
    });
       
        cbProveedor.setItems(proveedoresInternos);

        todosLosProveedores = FXCollections.observableArrayList();
        todosLosProveedores.addAll(proveedoresInternos);
        todosLosProveedores.addAll(proveedoresExternos);

        actualizarProveedores();

         ToggleGroup proveedoresToggleGroup = new ToggleGroup();
    rbInternos.setToggleGroup(proveedoresToggleGroup);
    rbExternos.setToggleGroup(proveedoresToggleGroup);
    
    proveedoresToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            actualizarProveedores();
        }
    });
        
    tfCantidad.addEventFilter(KeyEvent.KEY_TYPED, event -> {

        String character = event.getCharacter();

    
        if (!character.matches("[0-9]")) {
            
            event.consume();
        }
    });

    UnaryOperator<TextFormatter.Change> filter = change -> {
    String text = change.getControlNewText();
    if (Pattern.matches("[0-9/]*", text)) {
        return change;
    }
    return null;
};

TextFormatter<String> formatter = new TextFormatter<>(filter);
dpDiaEntrega.getEditor().setTextFormatter(formatter);

 
    
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
        String nombreProducto = productoSeleccionado.getNombre();

        if (productosEnCarrito.contains(nombreProducto)) {
            Utilidades.mostrarDialogoSimple("Error", "El producto ya está en el carrito", Alert.AlertType.ERROR);
        } else {
         
            Producto productoEnCarrito = new Producto();
            productoEnCarrito.setIdProducto(productoSeleccionado.getIdProducto());
            productoEnCarrito.setNombre(nombreProducto);
            productoEnCarrito.setPrecio(productoSeleccionado.getPrecio());
            productoEnCarrito.setCantidad(cantidad);

     
            float precioUnitario = (float) productoSeleccionado.getPrecio();
            float precioFinal = precioUnitario * cantidad;
            productoEnCarrito.setPrecioUnitario(precioUnitario);
            productoEnCarrito.setPrecioFinal(precioFinal);

            carrito.add(productoEnCarrito);
            productosEnCarrito.add(nombreProducto);

            actualizarTablaCarrito();

            tfCantidad.clear();
            cbProducto.getSelectionModel().clearSelection();
        }
    }
}


private void actualizarTablaCarrito() {
    
    ObservableList<Producto> listaCarrito = FXCollections.observableArrayList(carrito);

    
    for (Producto producto : listaCarrito) {
        double precioFinal = producto.getPrecioUnitario() * producto.getCantidad();
        producto.setPrecioFinal(precioFinal);
    }

    tvCarrito.setItems(listaCarrito);

    double sumaPrecios = 0;


    for (Producto producto : listaCarrito) {
        sumaPrecios += producto.getPrecioFinal();
    }


    txTotal.setText("$" + String.valueOf(sumaPrecios) + " MXN");
}

private void actualizarProveedores() {
    if (rbInternos.isSelected()) {
        cbProveedor.setItems(proveedoresInternos);
    } else if (rbExternos.isSelected()) {
        cbProveedor.setItems(proveedoresExternos);
    
    }
}


@FXML
private void clicGenerar(ActionEvent event) {

    LocalDate fechaEntrega = dpDiaEntrega.getValue();

    if (fechaEntrega == null) {
        Utilidades.mostrarDialogoSimple("Error", "Por favor, seleccione una fecha de entrega", Alert.AlertType.ERROR);
        return;
    }
    
     LocalDate fechaActual = LocalDate.now();
    

    long diferenciaDias = ChronoUnit.DAYS.between(fechaActual, fechaEntrega);
    

    if (diferenciaDias <= 2) {
        Utilidades.mostrarDialogoSimple("Error", "La fecha de entrega debe ser mayor a 2 días a partir de la fecha de pedido, es tiempo más rápido de envío.", Alert.AlertType.ERROR);
        return;
    }
    

    Pedido proveedorSeleccionado = cbProveedor.getSelectionModel().getSelectedItem();
    if (proveedorSeleccionado == null) {
        Utilidades.mostrarDialogoSimple("Error", "Por favor, seleccione un proveedor", Alert.AlertType.ERROR);
        return;
    }


    ObservableList<Producto> productosCarrito = tvCarrito.getItems();

    if (productosCarrito.isEmpty()) {
        Utilidades.mostrarDialogoSimple("Error", "El carrito está vacío", Alert.AlertType.ERROR);
        return;
    }


        Pedido pedidoNuevo = new Pedido();
        pedidoNuevo.setFecha_pedido(Utilidades.obtenerFechaActual());
        pedidoNuevo.setFecha_entrega(fechaEntrega.toString());

      if (esEdicion){
                if (rbExternos.isSelected()){
                    pedidoNuevo.setIdPedido(pedido.getIdPedido());
                    int idProveedor = PedidoDAO.IdentificadorProveedor(cbProveedor.getValue().toString());              
                    pedidoNuevo.setIdProveedor(idProveedor);
                    actualizarPedidoExterno(pedidoNuevo);
             
              
           
      }
      
      if ( rbInternos.isSelected()){
                
                pedidoNuevo.setIdPedido(pedido.getIdPedido());
                 int idProveedores = PedidoDAO.IdentificadorProveedor(cbProveedor.getValue().toString());              
                pedidoNuevo.setIdSucursal(idProveedores);
                System.out.println("idpedido" + pedido.getIdPedido() + "id sucursal" + pedidoNuevo.getIdSucursal());
                actualizarPedidoInterno(pedidoNuevo);
            

      }   
      }else{
      
      if (rbExternos.isSelected()){
          pedidoNuevo.setIdProveedor(proveedorSeleccionado.getIdProveedor());
          guardarPedidoExterno(pedidoNuevo);
          Pedido pedidoTemporal = PedidoDAO.obtenerUltimoPedidoGuardado();
                int idPedido = pedidoTemporal.getIdPedido();
          
                registrarProductosPedido( idPedido);
                
           
      }
      
      if ( rbInternos.isSelected()){
              pedidoNuevo.setIdSucursal(proveedorSeleccionado.getIdSucursal());
          guardarPedidoInterno(pedidoNuevo);
           Pedido pedidoTemporal = PedidoDAO.obtenerUltimoPedidoGuardado();
               int idPedido = pedidoTemporal.getIdPedido();
              
                registrarProductosPedido( idPedido);
      
      }   
      
  
    carrito.clear();
    
    actualizarTablaCarrito();
    cbProveedor.getSelectionModel().clearSelection();
    dpDiaEntrega.setValue(null);

  
    Utilidades.mostrarDialogoSimple("Pedido Generado", "El pedido ha sido generado exitosamente", Alert.AlertType.INFORMATION);
    cerrarVentana();
      }
}
    
private void registrarProductosPedido(int productoNuevo) {
    int tamano = carrito.size() - 1;
    for (int i = 0; i <= tamano; i++) {
        Producto producto = carrito.get(i);


        ProductoPedido productoPedido = new ProductoPedido(
          producto.getIdPedido(),
            /* idProducto */ producto.getIdProducto(),   
            /* Cantidad */ producto.getCantidad(),
            /* idProductoPedido */ productoNuevo
        );
        productoPedido.setIdPedido(productoNuevo);
        System.out.println("idpedido "+producto.getIdPedido());
        int codigoRespuesta = ProductoPedidoDAO.guardarProductoPedido(productoPedido);
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", "Por el momento no hay conexión, "
                        + "por favor inténtalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al registrar los productos ,"
                        + " por favor inténtalo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Pedido Generado", "El pedido de los productos  "
                        + "se realizó con éxito", Alert.AlertType.INFORMATION);
                break;
        }
    }
}



@FXML
private void clicEliminar(ActionEvent event) {
    Producto productoSeleccionado = tvCarrito.getSelectionModel().getSelectedItem();
    if (productoSeleccionado != null) {

        tvCarrito.getItems().remove(productoSeleccionado);

      
        carrito.remove(productoSeleccionado);


        btnEliminar.setDisable(true);
    }
}


    @FXML
    private void clicRegresar(MouseEvent event) {
   
       Stage escenarioPrincipal = (Stage) cbProducto.getScene().getWindow();
        escenarioPrincipal.close();
    }

 @FXML
private void clicBuscarProducto(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        String texto = autoTextField.getText();
        Producto productoSeleccionado = productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(texto))
                .findFirst()
                .orElse(null);
        
        if (productoSeleccionado != null) {
            cbProducto.getSelectionModel().select(productoSeleccionado);
            autoTextField.clear();
        }
    }
}


 public void inicializarInformacionFormulario(boolean esEdicion, Pedido pedido, INotificacionOperacion interfazNotificacion){
        this.esEdicion = esEdicion;
        this.pedido = pedido;
        
        if(esEdicion){
            cargarInformacionPedido();
      
        }else{
            
        }
    }

  private void cargarInformacionPedido(){
      
       cbProveedor.setValue(pedido);
       Pedido proveedor = cbProveedor.getValue(); 
      System.out.println("pedido para la lista" + pedido);
       proveedoresExternos = FXCollections.observableArrayList();
      PedidoRespuesta respuestaExternos = PedidoDAO.obtenerProveedoresExternos();
    if (respuestaExternos.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
        ArrayList<Pedido> proveedoresExternosList = respuestaExternos.getPedidos();
         proveedoresExternos.addAll(proveedoresExternosList);
         if (proveedoresExternosList.stream().map(Object::toString).anyMatch(pedido.toString()::equals)) {
            rbExternos.setSelected(true);
                  }
        }
    String fechaEntregaString = pedido.getFecha_entrega();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
    LocalDate fechaEntrega = LocalDate.parse(fechaEntregaString, formatter); 
    dpDiaEntrega.setValue(fechaEntrega); 
      System.out.println("ID PEDIDO ANTES DE CARGAR PRODUCTOS" + pedido.getFecha_entrega() + pedido.getNombre_proveedor() + pedido.getIdPedido());
    cargarProductosPedido();
    
    }

 private void cargarProductosPedido(){
        carrito = FXCollections.observableArrayList();
        System.out.println("IDPEDIDO" + pedido.getIdPedido());
         ProductoRespuesta respuestaBD =  ProductoDAO.obtenerInformacionPedido(pedido.getIdPedido());
      
        carrito.addAll(respuestaBD.getProductos());
        tvCarrito.setItems(carrito);
        actualizarTablaCarrito();
             
    }

 private void actualizarPedidoExterno(Pedido pedidoActualizar){
        int codigoRespuesta = PedidoDAO.modificarPedidoExterno(pedidoActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion","Por el momento no hay conexión, "
                        + "por favor inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al modificar el pedido,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING); 
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Pedido modificado", "La actualización de el "
                        + "pedido se realizó con éxito", Alert.AlertType.INFORMATION);
                     ProductoPedidoDAO.eliminarProductoPedido(pedido.getIdPedido());
                    registrarProductosPedido( pedido.getIdPedido());
                
              
        }
}

 
 
 private void actualizarPedidoInterno(Pedido pedidoActualizar){
        int codigoRespuesta = PedidoDAO.modificarPedidoInterno(pedidoActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion","Por el momento no hay conexión, "
                        + "por favor inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Ocurrió un error al modificar el pedido,"
                        + " por favor inténtelo más tarde", Alert.AlertType.WARNING); 
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Pedido modificado", "La actualización del "
                        + "pedido se realizó con éxito", Alert.AlertType.INFORMATION);
                     ProductoPedidoDAO.eliminarProductoPedido(pedido.getIdPedido());
                    registrarProductosPedido( pedido.getIdPedido());
              
              
        }
}

    @FXML
    private void clicAyudaBusqueda(MouseEvent event) {
        Utilidades.mostrarDialogoSimple("Ayuda con el buscador", "Selecciona la barra y escribe el nombre del articulo que buscas," 
         +" puedes elegirlo con las teclas de las flechas y Enter o con el clic izquierdo del mouse, después presiona Enter y estará listo para elegir cantidad y agregar al carrito. ", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicAyudaGenerar(MouseEvent event) {
                Utilidades.mostrarDialogoSimple("¿Cómo generar un pedido?", "Para generar un pedido selecciona el articulo en la caja de productos o con ayuda del buscador que deseas añadir al carrito, después ingresa la cantidad en el cuadro de cantidad y presiona añadir al carrito." +
                 " \n \nDespués, con ayuda de los botones arriba del proveedor, podrás seleccionar el tipo de proveedor que quieras y elegirlo en la caja proveedor. Si deseas eliminar una carrito del producto, seleccionalo y presiona el botón eliminar."
                 + "\n\nFinalmente presiona el botón del calendario y selecciona una fecha. Con esto puedes presionar Generar Pedido para concluir.  ", Alert.AlertType.INFORMATION);

    }
 
     private void cerrarVentana(){
        Stage escenarioBase = (Stage) cbProducto.getScene().getWindow();
        escenarioBase.close();
    }
 
}
    



