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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxfarmacia.modelo.dao.SesionDAO;
import javafxfarmacia.modelo.pojo.Usuario;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pwfContraseña;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIngresar(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorContraseña.setText("");
        validarCampos();
    }
    
    private void validarCampos(){
        String usuario = tfUsuario.getText();
        String password = pwfContraseña.getText();
        boolean sonValidos = true;
        if(usuario.isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El nombre de usuario es requerido");
        }
        if(password.length() == 0){
            sonValidos = false;
            lbErrorContraseña.setText("La contraseña es requerida");
        }
        if(sonValidos){
            validarCredencialesUsuario(usuario, password);
        }
    }
    private void irPantallaPrincipal(){
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializaEscena("vistas/FXMLMenuPrincipal.fxml"));
        escenarioBase.setTitle("Menú Principal");
        escenarioBase.show();
    }
    
    private void validarCredencialesUsuario(String usuario, String password){
        Usuario usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        switch(usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de Conexión",
                        "Por el momento no hay conexión, por favor inténtelo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                       "Por el momento no se puede procesar la solicitud de verificación", 
                       Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(usuarioRespuesta.getIdUsuario() > 0){
                    Utilidades.mostrarDialogoSimple("Usuario verificado",
                            "Bienvenid@ " + usuarioRespuesta.toString() + " al sistema...", 
                            Alert.AlertType.INFORMATION);
                     irPantallaPrincipal();
                }else{
                    Utilidades.mostrarDialogoSimple("Credenciales Incorrectas",
                            "El usuario y/o contraseña son incorrectos, por favor verifica la información",
                            Alert.AlertType.WARNING);
                }
                break;
            default:
                Utilidades.mostrarDialogoSimple("Error de petición", 
                        "El sistema no está disponible por el momento...",
                        Alert.AlertType.ERROR);
        }
    }
    
}
