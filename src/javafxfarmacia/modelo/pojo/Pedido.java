/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import javafxfarmacia.modelo.dao.PedidoDAO.TipoProveedor;

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
    private float total;
    private int idProducto;
    private int idProveedor;
    private int idSucursal;
private TipoProveedor tipoProveedor;



    public Pedido(int idPedido, String nombre, String fecha_pedido, String fecha_entrega, int cantidad, String estado, float total, int idProducto, int idProveedor, int idSucursal, TipoProveedor tipoProveedor) {
        this.idPedido = idPedido;
        this.nombre = nombre;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega = fecha_entrega;
        this.cantidad = cantidad;
        this.estado = estado;
        this.total = total;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.idSucursal = idSucursal;
        this.tipoProveedor = tipoProveedor;
    }

    public TipoProveedor getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(TipoProveedor TipoProveedor) {
        this.tipoProveedor = TipoProveedor;
    }
    

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }



    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

 

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    public Pedido() {
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



