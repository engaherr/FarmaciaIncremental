/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxfarmacia.controladores;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafxfarmacia.modelo.pojo.Producto;

/**
 * FXML Controller class
 *
 * @author kikga
 */
public class FXMLProductoDetallesController implements Initializable {

    @FXML
    private Label lbProducto;
    @FXML
    private Label lbHdrVencimiento;
    @FXML
    private Label lbHdrPresentacion;
    @FXML
    private Label lbSucursal;
    @FXML
    private Label lbCantidad;
    @FXML
    private Label lbPrecio;
    @FXML
    private Label lbFechaVencimiento;
    
    private Producto productoDetalles;
    @FXML
    private Label lbPresentacion;
    @FXML
    private ImageView ivFotoProducto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionDetalles(Producto producto){
        this.productoDetalles = producto;
        cargarInformacionDetalles();
    }
    
    private void cargarInformacionDetalles(){
        lbCantidad.setText(Integer.toString(productoDetalles.getCantidad()));
        if(productoDetalles.getFechaVencimiento() == null){
            lbHdrVencimiento.setVisible(false);
            lbFechaVencimiento.setVisible(false);
        }else{
            lbFechaVencimiento.setText(productoDetalles.getFechaVencimiento());
        }
        lbPrecio.setText("$" + productoDetalles.getPrecio());
        lbProducto.setText(productoDetalles.getNombre());
        lbSucursal.setText(productoDetalles.getNombreSucursal());
        if("N/A".equals(productoDetalles.getPresentacion())){
            lbHdrPresentacion.setVisible(false);
            lbPresentacion.setVisible(false);
        }else{
            lbPresentacion.setText(productoDetalles.getPresentacion());
        }
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(productoDetalles.getFoto());
            Image imagenProducto = new Image(bais);
            ivFotoProducto.setPreserveRatio(true);
            ivFotoProducto.setImage(imagenProducto);
        }catch(Exception e){
        }
        
    }
}
