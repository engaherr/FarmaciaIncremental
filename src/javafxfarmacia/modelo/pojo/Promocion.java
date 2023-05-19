/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author jasie
 */
public class Promocion {
    private int idPromocion;
    private String descripcion;
    private double precioFinal;
    private String fechaInicio;
    private String fechaTermino;
    private byte[] imagen;

    public Promocion() {
    }

    public Promocion(int idPromocion, String descripcion, double precioFinal, String fechaInicio, String fechaTermino, byte[] imagen) {
        this.idPromocion = idPromocion;
        this.descripcion = descripcion;
        this.precioFinal = precioFinal;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.imagen = imagen;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    


            
}
