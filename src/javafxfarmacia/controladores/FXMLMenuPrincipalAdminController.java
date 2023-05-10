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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author dplat
 */
public class FXMLMenuPrincipalAdminController implements Initializable {

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
    private void clicInventario(ActionEvent event) {
    }

    @FXML
    private void clicReadquisicion(ActionEvent event) {
        Stage escenarioReadquisicion = new Stage();
        escenarioReadquisicion.setScene(Utilidades.inicializaEscena("vistas/FXMLReadquisicion.fxml"));
        escenarioReadquisicion.setTitle("Readquisicion");
        escenarioReadquisicion.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisicion.show();
    }

    @FXML
    private void clicAbasto(ActionEvent event) {
    }

    @FXML
    private void clicPromociones(ActionEvent event) {
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
    }
    
}
