/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbTitulo;
    
    int contador = 1; 
    @FXML
    private AnchorPane panel1;
    @FXML
    private AnchorPane panel2;
    @FXML
    private AnchorPane panel3;
    
    int mostrar = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animacionTransicion(0.5, panel1, 829);
        animacionTransicion(0.5, panel2, 829);
       
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
        if(mostrar == 0){
            animacionTransicion(0.5,panel1,-829);
            mostrar++;
            
        }else if(mostrar == 1){
            animacionTransicion(0.5,panel2,-829);
            mostrar++;
        }    
    }

    @FXML
    private void clicAnterior(ActionEvent event) {
        if(mostrar == 1){
            animacionTransicion(0.5,panel1,829);
            mostrar--;
        }else if(mostrar == 2){
            animacionTransicion(0.5,panel2,829);
            mostrar--;
        }
    }
    
    public void animacionTransicion(double duracion, Node nodo, double anchura){
        TranslateTransition transicion = new TranslateTransition(Duration.seconds(duracion), nodo);
        transicion.setByX(anchura);
        transicion.play();
    }
    
}
