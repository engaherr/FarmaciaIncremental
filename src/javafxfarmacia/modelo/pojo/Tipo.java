/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author dplat
 */

 
public class Tipo {
    private int idTipo;
    private String nombre;
    private int idPresentacion;

    public Tipo() {
    }

    public Tipo(int idTipo, String nombre, int idPresentacion) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.idPresentacion = idPresentacion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(int idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}


