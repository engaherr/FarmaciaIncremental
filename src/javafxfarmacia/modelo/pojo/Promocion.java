/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;

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
    private ArrayList<String> productos;
    private String productosPromo;

    public Promocion() {
    }

    public Promocion(int idPromocion, String descripcion, double precioFinal, String fechaInicio, 
            String fechaTermino, byte[] imagen, ArrayList<String> productos, String productosPromo ) {
        this.idPromocion = idPromocion;
        this.descripcion = descripcion;
        this.precioFinal = precioFinal;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.imagen = imagen;
        this.productos = productos;
        this.productosPromo = productosPromo;
    }

    public String getProductosPromo() {
        return productosPromo;
    }

    public void setProductosPromo(String productosPromo) {
        this.productosPromo = productosPromo;
    }
    
    
    

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) {
        this.productos = productos;
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

    public void juntarProductos(ArrayList<String> productos){
        StringBuilder stringBuilder = new StringBuilder();

        for (String elemento : productos) {
            stringBuilder.append(elemento).append(", ");
        }

        // Eliminar la última coma y el espacio extra
        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        setProductosPromo(stringBuilder.toString());
    }


            
}
