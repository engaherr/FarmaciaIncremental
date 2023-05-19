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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
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
    private void clicReadquisicion(ActionEvent event) {
        Stage escenarioReadquisicion = new Stage();
        escenarioReadquisicion.setScene(Utilidades.inicializaEscena("vistas/FXMLReadquisicion.fxml"));
        escenarioReadquisicion.setTitle("Readquisicion");
        escenarioReadquisicion.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisicion.show();
    }
    
    @FXML
    private void clicInventario(ActionEvent event) {
        Stage escenarioInventario = new Stage();
        escenarioInventario.setScene(Utilidades.inicializaEscena("vistas/FXMLInventario.fxml"));
        escenarioInventario.setTitle("Gestión de Inventario");
        escenarioInventario.initModality(Modality.APPLICATION_MODAL);
        escenarioInventario.showAndWait();
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
         Utilidades.mostrarDialogoSimple("Cierre de sesión",
            "Adiós usuario,vuelva pronto",
            Alert.AlertType.INFORMATION);
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializaEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de Sesión");
        escenarioBase.show();
    }

    @FXML
    private void clicCorte(ActionEvent event) {
        Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicEmpleados(ActionEvent event) {
        Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }
    
}
