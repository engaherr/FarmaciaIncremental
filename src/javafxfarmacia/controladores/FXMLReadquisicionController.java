/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLReadquisicionController implements Initializable {

    @FXML
    private Label lbTitulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicGenerarPedido(ActionEvent event) {
        
         Stage escenarioPedido = new Stage();
        escenarioPedido.setScene(Utilidades.inicializaEscena("vistas/FXMLGenerarPedido.fxml"));
        escenarioPedido.setTitle("Generar pedido");
        escenarioPedido.initModality(Modality.APPLICATION_MODAL);
        escenarioPedido.show();
    }
  

    @FXML
    private void clicVerEstatus(ActionEvent event) {
        Stage escenarioConsultarPedidos = new Stage();
        escenarioConsultarPedidos.setScene(Utilidades.inicializaEscena("vistas/FXMLConsultarPedidos.fxml"));
        escenarioConsultarPedidos.setTitle("Consultar pedidos");
        escenarioConsultarPedidos.initModality(Modality.APPLICATION_MODAL);
        escenarioConsultarPedidos.show();
        
    }

    @FXML
    private void lbClicGenerarPedido(MouseEvent event) {
       Stage escenarioPedido = new Stage();
        escenarioPedido.setScene(Utilidades.inicializaEscena("vistas/FXMLGenerarPedido.fxml"));
        escenarioPedido.setTitle("Generar pedido");
        escenarioPedido.initModality(Modality.APPLICATION_MODAL);
        escenarioPedido.show();
    }

    @FXML
    private void lbClicVerEstatus(MouseEvent event) {
          Stage escenarioConsultarPedidos = new Stage();
        escenarioConsultarPedidos.setScene(Utilidades.inicializaEscena("vistas/FXMLConsultarPedidos.fxml"));
        escenarioConsultarPedidos.setTitle("Consultar pedidos");
        escenarioConsultarPedidos.initModality(Modality.APPLICATION_MODAL);
        escenarioConsultarPedidos.show();
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
         Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
    
    
}
