/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author dplat
 */
public class ProductoPedido {
    
    private int idPedido;
    private int idProducto;
    private int Cantidad;
    private int idProductoPedido;
    private String nombre;

    public ProductoPedido(int idPedido, int idProducto, int Cantidad, int idProductoPedido,String nombre) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.Cantidad = Cantidad;
        this.idProductoPedido = idProductoPedido;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
  

    public int getIdProductoPedido() {
        return idProductoPedido;
    }

    public void setIdProductoPedido(int idProductoPedido) {
        this.idProductoPedido = idProductoPedido;
    }

    public ProductoPedido(int idPedido, int idProducto, int Cantidad, int idProductoPedido) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.Cantidad = Cantidad;
        this.idProductoPedido = idProductoPedido;
    }

    
    
    
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    
    
}
