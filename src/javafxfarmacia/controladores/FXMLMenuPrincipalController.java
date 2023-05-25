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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
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
    @FXML
    private ImageView imPromociones;
    
    int contador = 1; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    





    @FXML
    private void clicPromociones(ActionEvent event) {
        Stage escenarioPromociones = new Stage ();
        escenarioPromociones.setScene(Utilidades.inicializaEscena("vistas/FXMLPromociones.fxml"));
        escenarioPromociones.setTitle("Lista de promociones");
        escenarioPromociones.initModality(Modality.APPLICATION_MODAL);
        escenarioPromociones.showAndWait();
        
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
    private void clicSiguiente(ActionEvent event) {
        contador += 1;
    }

    @FXML
    private void clicAnterior(ActionEvent event) {
        contador -= 1;
        //File
    }
    
}
