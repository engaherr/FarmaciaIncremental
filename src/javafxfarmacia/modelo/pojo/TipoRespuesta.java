/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * author dplat
 */
public class TipoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Tipo> tipos;

    public TipoRespuesta() {
    }

    public TipoRespuesta(int codigoRespuesta, ArrayList<Tipo> tipos) {
        this.codigoRespuesta = codigoRespuesta;
        this.tipos = tipos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(ArrayList<Tipo> tipos) {
        this.tipos = tipos;
    }
}
