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
    private String precioFinal;
    private String fechaInicio;
    private String fechaTermino;
    private byte[] imagen;
    private PromocionProductoRespuesta productos;
    private String productosPromo;

    public Promocion() {
    }

    public Promocion(int idPromocion, String descripcion, String precioFinal, String fechaInicio, 
            String fechaTermino, byte[] imagen, PromocionProductoRespuesta productos, String productosPromo ) {
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
    
    
    

    public PromocionProductoRespuesta getProductos() {
        return productos;
    }

    public void setProductos(PromocionProductoRespuesta productos) {
        this.productos = productos;
    }

 
    

    public int getIdPromocion() {
        return idPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecioFinal() {
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

    public void setPrecioFinal(String precioFinal) {
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

    public void juntarProductos(){
        ArrayList<PromocionProducto> productosPromoc = productos.getPromocionesProductoRespuesta();
        StringBuilder sb = new StringBuilder();
        for(PromocionProducto produc : productosPromoc){
            sb.append(produc.getNombreProducto()).append(", ");
        }
        
        if(sb.length() > 0){
            sb.setLength(sb.length() - 2);
        }
        
        this.productosPromo = sb.toString();
        
        double precioPromo = 0;
        for(PromocionProducto produc : productosPromoc){
            precioPromo += produc.getPrecioFinal();
        }
       
        String finalString = String.format("%.2f", precioPromo);
        finalString = finalString + " MXN";
        this.precioFinal = finalString;

    }
    


            
}
