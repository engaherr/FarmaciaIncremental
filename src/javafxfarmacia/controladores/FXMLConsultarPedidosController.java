/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;


import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.PedidoDAO;
import javafxfarmacia.modelo.pojo.Pedido;
import javafxfarmacia.modelo.pojo.PedidoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.stage.Modality;
import javafxfarmacia.JavaFXFarmacia;
import javafxfarmacia.interfaz.INotificacionOperacion;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLConsultarPedidosController implements Initializable, INotificacionOperacion {

    @FXML
    private TableView<Pedido> tvPedidos;

    @FXML
    private TableColumn colFechaPedido;
    @FXML
    private TableColumn colFechaEntrega;

    
private ObservableList<Pedido> pedidos;
    @FXML
    private TableColumn<?, ?> colProductosCombinados;
    @FXML
    private TableColumn<?, ?> colProveedor;
    TableColumn<Pedido, ImageView> colEstado = new TableColumn<>("Estado");
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    
    
    
   public void configurarTabla() {
    colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fecha_pedido"));
    colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fecha_entrega"));
    colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombre_proveedor"));
    colProductosCombinados.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, ImageView>, ObservableValue<ImageView>>() {
    @Override
    public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Pedido, ImageView> param) {
        Pedido pedido = param.getValue();
        ImageView imageView = new ImageView();

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Obtener la fecha en la columna colFechaPedido
        LocalDate fechaPedido = LocalDate.parse(pedido.getFecha_pedido());
         LocalDate fechaEntrega = LocalDate.parse(pedido.getFecha_entrega());

        // Verificar si la fecha es igual a la fecha actual
     if (fechaPedido.isEqual(fechaActual)) {
            // Cargar imagen para mostrar
            Image imagenEstado = new Image("javafxfarmacia/recursos/stepper1.png");
            imageView.setImage(imagenEstado);
             double anchoDeseado = 286; 
            double altoDeseado = 80; 
            imageView.setFitWidth(anchoDeseado);
            imageView.setFitHeight(altoDeseado);
        } else if (fechaEntrega.isEqual(fechaActual)  || fechaEntrega.isBefore(fechaActual)) {
            // Cargar otra imagen si la fecha de entrega es igual a la fecha actual
            Image otraImagen = new Image("javafxfarmacia/recursos/stepper3.png");
            imageView.setImage(otraImagen);
            double anchoDeseado = 286; 
            double altoDeseado = 82; 
            imageView.setFitWidth(anchoDeseado);
            imageView.setFitHeight(altoDeseado);
        } else {
            // Cargar imagen por defecto si no cumple ninguna de las condiciones anteriores
            Image imagenDefault = new Image("javafxfarmacia/recursos/stepper2.png");
            imageView.setImage(imagenDefault);
            double anchoDeseado = 286; 
            double altoDeseado = 82; 
            imageView.setFitWidth(anchoDeseado);
            imageView.setFitHeight(altoDeseado);
        }


        return new SimpleObjectProperty<>(imageView);
        
    }
});

 tvPedidos.getColumns().add(colEstado);
 
 // Establecer estilo de la tabla
tvPedidos.setStyle("-fx-background-color: #f8f2dc;");

// Establecer estilo de las filas
tvPedidos.setRowFactory(tv -> {
    TableRow<Pedido> row = new TableRow<>();
    row.setStyle("-fx-background-color: #f8f2dc;");
    return row;
});

// Establecer estilo de los encabezados de columna
colFechaPedido.setStyle("-fx-background-color: #f8f2dc;-fx-border-color: #2E2F40; -fx-border-width: 1px;");
colFechaEntrega.setStyle("-fx-background-color: #f8f2dc;-fx-border-color: #2E2F40;-fx-border-width: 1px;");
colProveedor.setStyle("-fx-background-color: #f8f2dc;-fx-border-color: #2E2F40;-fx-border-width: 1px;");
colProductosCombinados.setStyle("-fx-background-color: #f8f2dc;-fx-border-color: #2E2F40;-fx-border-width: 1px;");
colEstado.setStyle("-fx-background-color: #f8f2dc;-fx-border-color: #2E2F40;-fx-border-width: 1px;");

}

    
    
    public void cargarInformacionTabla() {
    pedidos = FXCollections.observableArrayList();
    PedidoRespuesta respuestaBD = PedidoDAO.obtenerInformacionPedido();
    switch (respuestaBD.getCodigoRespuesta()) {
        case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "No se pudo conectar con la base de datos. Intente de nuevo o hágalo más tarde",
                    Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información. Por favor, inténtelo de nuevo más tarde",
                    Alert.AlertType.WARNING);
            break;
        case Constantes.OPERACION_EXITOSA:
            pedidos.addAll(respuestaBD.getPedidos());
            tvPedidos.setItems(pedidos);
            break;
    }
}

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvPedidos.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
 private void clicModificar(ActionEvent event) {
    int posicion = tvPedidos.getSelectionModel().getSelectedIndex();
    if (posicion != -1) {
        Pedido pedido = pedidos.get(posicion);
        LocalDate fechaPedido = LocalDate.parse(pedido.getFecha_pedido());
        LocalDate fechaActual = LocalDate.now();
        
        if (fechaPedido.isEqual(fechaActual)) {
            irFormulario(true, pedido);
        } else {
            Utilidades.mostrarDialogoSimple("El pedido ya está en camino", "El pedido solo se puede modificar antes de que esté en camino.", Alert.AlertType.WARNING);
        }
    } else {
        Utilidades.mostrarDialogoSimple("Atención", "Por favor selecciona un pedido para poder modificarlo.", Alert.AlertType.WARNING);
    }
}


    
    
    
     private void irFormulario(boolean esEdicion, Pedido pedido){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                (JavaFXFarmacia.class.getResource("vistas/FXMLGenerarPedido.fxml"));
            Parent vista;
            vista = accesoControlador.load();
            
            FXMLGenerarPedidoController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion,pedido,this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario: registro de pedido");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();         
            
        }catch(IOException ex){
            Logger.getLogger(FXMLGenerarPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacionGuardarPedido(int idPedido) {
Utilidades.mostrarDialogoSimple("Notificacion","Se registró de forma "
                + "exitosa la promoción", Alert.AlertType.INFORMATION);
        cargarInformacionTabla();  
    }

    @Override
    public void notificarOperacionActualizarPedido(int idPedido) {
Utilidades.mostrarDialogoSimple("Notificación","Se ACTUALIZÓ "
                + "los datos de la promocion", Alert.AlertType.INFORMATION);
                cargarInformacionTabla();  

    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }
    
     
     
    


    
}
