package javafxfarmacia.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafxfarmacia.modelo.dao.TipoProductoDAO;
import javafxfarmacia.modelo.pojo.Tipo;
import javafxfarmacia.modelo.pojo.TipoRespuesta;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.utils.Utilidades;

public class FXMLGenerarPedidoController implements Initializable {

    @FXML
    private ComboBox<Tipo> cbTipo;

    private ObservableList<Tipo> tipos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionTipo();
    }

    private void cargarInformacionTipo() {
        tipos = FXCollections.observableArrayList();
        TipoRespuesta tiposBD = TipoProductoDAO.obtenerInformacionTipo();
        switch (tiposBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", "Error de conexión con la base de datos", Alert.AlertType.ERROR);
                break;

            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se puede mostrar la información", Alert.AlertType.WARNING);
                break;

            case Constantes.OPERACION_EXITOSA:
                tipos.addAll(tiposBD.getTipos());
                cbTipo.setItems(tipos);
                break;
        }
    }
}
