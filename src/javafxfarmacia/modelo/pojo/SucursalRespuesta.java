/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author kikga
 */
public class SucursalRespuesta {
    private int codigoRespuesta;
    private ArrayList<Sucursal> sucursales;

    public SucursalRespuesta() {
    }

    public SucursalRespuesta(int codigoRespuesta, ArrayList<Sucursal> sucursales) {
        this.codigoRespuesta = codigoRespuesta;
        this.sucursales = sucursales;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
    

}
