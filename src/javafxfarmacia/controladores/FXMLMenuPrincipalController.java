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
import javafx.stage.Stage;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLMenuPrincipalController implements Initializable {

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
    private void clicVender(ActionEvent event) {
        Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicInventario(ActionEvent event) {
                Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicAbasto(ActionEvent event) {
                Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicPromociones(ActionEvent event) {
                Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
         Utilidades.mostrarDialogoSimple("Cierre de sesión",
            "Adiós usuario,vuelva pronto",
            Alert.AlertType.INFORMATION);
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializaEscena("vistas/InicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de Sesión");
        escenarioBase.show();
    }
    
}
