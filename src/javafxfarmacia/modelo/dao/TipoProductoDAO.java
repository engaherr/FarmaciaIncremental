
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxfarmacia.modelo.ConexionBD;
import javafxfarmacia.modelo.pojo.Tipo;
import javafxfarmacia.modelo.pojo.TipoRespuesta;
import javafxfarmacia.utils.Constantes;

import javafxfarmacia.modelo.pojo.Tipo;

import java.sql.ResultSet;

import java.sql.PreparedStatement;

import java.sql.PreparedStatement;

import java.sql.Connection;

import java.sql.Connection;

import java.sql.Connection;

/**
 *
 * @author dplat
 */
public class TipoProductoDAO {
    public static TipoRespuesta obtenerInformacionTipo(){
    TipoRespuesta respuesta = new TipoRespuesta();
    Connection conexionBD = ConexionBD.abrirConexionBD();
      if(conexionBD != null){
            try{
                String consulta = "SELECT idProducto,presentacion FROM producto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Tipo> tipos = new ArrayList();
                
                while(resultado.next()){
                    Tipo tipo = new Tipo();
                    tipo.setIdTipo(resultado.getInt("idProducto"));
                    tipo.setNombre(resultado.getString("presentacion"));
                    tipos.add(tipo);
                    
                
                }
                respuesta.setTipos(tipos);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

                conexionBD.close();
          
                
            }catch(SQLException ex){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
   
    }
    
}