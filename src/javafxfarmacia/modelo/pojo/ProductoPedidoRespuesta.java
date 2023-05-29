/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dplat
 */
public class ProductoPedidoRespuesta {
       private int codigoRespuesta;
    private ArrayList<ProductoPedido> productosPedidosRespuesta;

    public ProductoPedidoRespuesta() {
    }

    public ProductoPedidoRespuesta(int codigoRespuesta, ArrayList<ProductoPedido> pedidoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
        this.productosPedidosRespuesta = pedidoRespuesta;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<ProductoPedido> getProductoPedidoRespuesta() {
        return productosPedidosRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setPedidosProductoRespuesta(ArrayList<ProductoPedido> pedidoRespuesta) {
        this.productosPedidosRespuesta = pedidoRespuesta;
    }
    
      private ArrayList<ProductoPedido> productos;

    public ArrayList<ProductoPedido> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoPedido> productos) {
        this.productos = productos;
    }
          
}


