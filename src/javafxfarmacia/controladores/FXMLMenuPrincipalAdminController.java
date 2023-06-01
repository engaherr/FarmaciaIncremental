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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ImageView ivPromocion;
    @FXML
    private Button btnAnteriorImagen;
    @FXML 
    private Button btnSiguienteImagen;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cursorEnBotonIzq(MouseEvent event) {
        btnAnteriorImagen.setOpacity(1.0);
    }
    
    @FXML
    private void salidaCursorBotonIzq(MouseEvent event){
        btnAnteriorImagen.setOpacity(0.5);
    }

    @FXML
    private void clicAnterior(ActionEvent event) {
    }

    @FXML
    private void cursorEnBotonDer(MouseEvent event) {
        btnSiguienteImagen.setOpacity(1.0);
    }
    
    @FXML
    private void salidaCursorBotonDer(MouseEvent event){
        btnSiguienteImagen.setOpacity(0.5);
    }
    
    @FXML
    private void clicSiguiente(ActionEvent event) {
        
    }

    @FXML
    private void clicInventario(MouseEvent event) {
        Stage escenarioInventario = new Stage();
        escenarioInventario.setScene(Utilidades.inicializaEscena("vistas/FXMLInventario.fxml"));
        escenarioInventario.setTitle("Gestión de Inventario");
        escenarioInventario.initModality(Modality.APPLICATION_MODAL);
        escenarioInventario.showAndWait();
    }

    @FXML
    private void clicReadquisicion(MouseEvent event) {
        Stage escenarioReadquisicion = new Stage();
        escenarioReadquisicion.setScene(Utilidades.inicializaEscena("vistas/FXMLReadquisicion.fxml"));
        escenarioReadquisicion.setTitle("Readquisicion");
        escenarioReadquisicion.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisicion.show();
    }

    @FXML
    private void clicCorte(MouseEvent event) {
        Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicEmpleados(MouseEvent event) {
        Utilidades.mostrarDialogoSimple("Funcionalidad en progreso",
            "Estamos trabajando para desarrollar esta funcionalidad",
            Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        Utilidades.mostrarDialogoSimple("Cierre de sesión",
            "Adiós usuario,vuelva pronto",
            Alert.AlertType.INFORMATION);
        Stage escenarioBase = (Stage) ivPromocion.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializaEscena("vistas/FXMLInicioSesion.fxml"));
        escenarioBase.setTitle("Inicio de Sesión");
        escenarioBase.show();
    }
}
