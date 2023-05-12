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

    private final int idPedido;
    private final String nombre;
    private final String fecha_pedido;
    private final String fecha_entrega;
    private final int cantidad;
    private final String estado;
    

public Pedido (int idPedido, String nombre,String fecha_pedido, String fecha_entrega, int cantidad, String estado) {
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

    public String getNombre() {
        return nombre;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public int setIdPedido() {
        return idPedido;
    }

    public String setNombre() {
        return nombre;
    }

    public String setFecha_pedido() {
        return fecha_pedido;
    }

    public String setFecha_entrega() {
        return fecha_entrega;
    }

    public int setCantidad() {
        return cantidad;
    }

    public String setEstado() {
        return estado;
    }

}