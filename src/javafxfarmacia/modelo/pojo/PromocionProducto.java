/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author jasie
 */
public class PromocionProducto {
    private int idPromocionProducto;
    private int idPromocion;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double precioFinal;
    private String nombreProducto;

    public PromocionProducto() {
    }

    public PromocionProducto(int idPromocionProducto, int idPromocion, int idProducto, int cantidad, double precioUnitario, double precioFinal, String nombreProducto) {
        this.idPromocionProducto = idPromocionProducto;
        this.idPromocion = idPromocion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioFinal = precioFinal;
        this.nombreProducto = nombreProducto;
    }

    public int getIdPromocionProducto() {
        return idPromocionProducto;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setIdPromocionProducto(int idPromocionProducto) {
        this.idPromocionProducto = idPromocionProducto;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    
    
}
