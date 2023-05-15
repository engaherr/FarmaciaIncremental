/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.sql.Date;

/**
 *
 * @author jasie
 */
public class Promocion {
    private int idPromocion;
    private String descripcion;
    private int idProducto;
    private String nombreProducto;
    private double precioInicial;
    private double  descuento;
    private double precioFinal;
    private String fechaInicio;
    private String fechaTermino;

    public Promocion() {
    }

    public Promocion(int idPromocion, String descripcion, int idProducto, String nombreProducto, 
            double precioInicial,double descuento, double precioFinal,String fechaInicio, String fechaTermino) {
        this.idPromocion = idPromocion;
        this.descripcion = descripcion;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioInicial = precioInicial;
        this.descuento = descuento;
        this.precioFinal = precioFinal;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecioInicial() {
        return precioInicial;
    }

    public double getDescuento() {
        return descuento;
    }

    public double  getPrecioFinal() {
        return precioFinal;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecioInicial(double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
    
    

            
}
