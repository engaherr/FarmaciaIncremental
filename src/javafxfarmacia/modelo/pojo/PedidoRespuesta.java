/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author dplat
 */
public class PedidoRespuesta {
     private int codigoRespuesta;
    private ArrayList<Pedido> pedido;

    public PedidoRespuesta() {
    }

    public PedidoRespuesta(int codigoRespuesta, ArrayList<Pedido> pedido) {
        this.codigoRespuesta = codigoRespuesta;
        this.pedido = pedido;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedido;
    }

    public void setProductos(ArrayList<Producto> pedido) {
        this.pedido = pedido;
    }
    
    
  
}
