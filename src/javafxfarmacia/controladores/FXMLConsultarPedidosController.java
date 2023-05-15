/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.PedidoDAO;
import javafxfarmacia.modelo.pojo.Pedido;
import javafxfarmacia.modelo.pojo.PedidoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLConsultarPedidosController implements Initializable {

    @FXML
    private TableView<Pedido> tvPedidos;
    @FXML
    private TableColumn colPedido;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colFechaPedido;
    @FXML
    private TableColumn colFechaEntrega;

    
private ObservableList<Pedido> pedidos;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    public void configurarTabla(){
    colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
    colFechaEntrega.setCellValueFactory(new PropertyValueFactory("fecha_entrega"));
    colFechaPedido.setCellValueFactory(new PropertyValueFactory("fecha_pedido"));
    colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
    colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    colPedido.setCellValueFactory(new PropertyValueFactory("IdPedido"));
    
    
    }
    
    
    public void cargarInformacionTabla(){
    pedidos = FXCollections.observableArrayList();
    PedidoRespuesta respuestaBD = PedidoDAO.obtenerInformacionPedido();
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
    
}
