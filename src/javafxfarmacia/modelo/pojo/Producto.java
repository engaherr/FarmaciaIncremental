/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

public class Producto {
    private int idProducto;
    private String nombre;
    private String fechaVencimiento;
    private double precioUnitario;
    private boolean ventaControlada;
    private String nombreSucursal;
    private int idSucursal;
    private int cantidad;
    private String presentacion;
    
    private double precioFinal;
    private byte[] foto;


    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto(int idProducto, String nombre, String fechaVencimiento, double precioUnitario, boolean ventaControlada, String nombreSucursal, int idSucursal, int cantidad, String presentacion, byte[] foto, double precioFinal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.fechaVencimiento = fechaVencimiento;
        this.precioUnitario = precioUnitario;
        this.ventaControlada = ventaControlada;
        this.nombreSucursal = nombreSucursal;
        this.idSucursal = idSucursal;
        this.cantidad = cantidad;
        this.presentacion = presentacion;
        this.foto = foto;
        this.precioFinal = precioFinal;
    }
    

    public Producto() {

    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getPrecio() {
        return precioUnitario;
    }

    public void setPrecio(double precio) {
        this.precioUnitario = precio;
    }

    public boolean isVentaControlada() {
        return ventaControlada;
    }

    public void setVentaControlada(boolean ventaControlada) {
        this.ventaControlada = ventaControlada;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    @Override
    public String toString() {
        return nombre;
    }

    
}