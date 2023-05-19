/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author jasie
 */
public class PromocionProductoRespuesta {
    private int codigoRespuesta;
    private ArrayList<PromocionProducto> promocionesProductoRespuesta;

    public PromocionProductoRespuesta() {
    }

    public PromocionProductoRespuesta(int codigoRespuesta, ArrayList<PromocionProducto> promocionesRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
        this.promocionesProductoRespuesta = promocionesRespuesta;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<PromocionProducto> getPromocionesProductoRespuesta() {
        return promocionesProductoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setPromocionesProductoRespuesta(ArrayList<PromocionProducto> promocionesRespuesta) {
        this.promocionesProductoRespuesta = promocionesRespuesta;
    }
    
    
          
}
