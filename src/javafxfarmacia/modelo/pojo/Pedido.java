/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author dplat
 */
public class Pedido {

    private int idPedido;
    private String nombre;
    private String fecha_pedido;
    private String fecha_entrega;
    private int cantidad;
    private String estado;
    
    public Pedido() {
    }

    public Pedido(int idPedido, String nombre, String fecha_pedido, String fecha_entrega, int cantidad, String estado) {
        this.idPedido = idPedido;
        this.nombre = nombre;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.cantidad = cantidad;
        this.estado = estado;
    }
    
    public int getIdPedido() {
        return idPedido;
    }
    
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getFecha_pedido() {
        return fecha_pedido;
    }
    
    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }
    
    public String getFecha_entrega() {
        return fecha_entrega;
    }
    
    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}



