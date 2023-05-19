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
import javafxfarmacia.modelo.pojo.Promocion;
import javafxfarmacia.modelo.pojo.PromocionRespuesta;
import javafxfarmacia.utils.Constantes;

/**
 *
 * @author jasie
 */
public class PromocionDAO {
    public static PromocionRespuesta obtenerInformacionPromociones(){
        PromocionRespuesta respuesta = new PromocionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "select idPromocion,descripcion,fechaInicia,fechaTermino,imagen from promocion;";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionConsulta = new ArrayList();
                
                while(resultado.next()){
                    Promocion promocionTemporal = new Promocion();
                    promocionTemporal.setIdPromocion(resultado.getInt("idPromocion"));
                    promocionTemporal.setDescripcion(resultado.getString("descripcion"));                 
                    promocionTemporal.setFechaInicio(resultado.getString("fechaInicia"));
                    promocionTemporal.setFechaTermino(resultado.getString("fechaTermino"));
                    promocionTemporal.setImagen(resultado.getBytes("imagen"));
                    promocionConsulta.add(promocionTemporal); 
                }
              
                respuesta.setPromociones(promocionConsulta);
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
    
        public static PromocionRespuesta obtenerInformacionBusqueda(String busqueda){
        PromocionRespuesta respuesta = new PromocionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idPromocion, descripcion,fechaInicia,fechaTermino, "
                        + "imagen from promocion \n" +
                        "where descripcion like 'pañal' order by fechaTermino asc;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, "%" + busqueda + "%");
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionConsulta = new ArrayList();
                while(resultado.next()){
                    Promocion promocionTemporal = new Promocion();
                    promocionTemporal.setIdPromocion(resultado.getInt("idPromocion"));
                    promocionTemporal.setDescripcion(resultado.getString("descripcion"));           
                    promocionTemporal.setFechaInicio(resultado.getString("fechaInicia"));
                    promocionTemporal.setFechaTermino(resultado.getString("fechaTermino"));
                    promocionConsulta.add(promocionTemporal);
                }
                respuesta.setPromociones(promocionConsulta);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int registrarPromocion(Promocion promocionNueva){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into promocion (descripcion,fechaInicia,fechaTermino,imagen)"
                        + "values (?,?,?,?)";
                PreparedStatement prepararSetencia = conexionBD.prepareStatement(sentencia);
                prepararSetencia.setString(1,promocionNueva.getDescripcion());
                prepararSetencia.setString(2,promocionNueva.getFechaInicio());
                prepararSetencia.setString(3,promocionNueva.getFechaTermino());
                prepararSetencia.setBytes(4,promocionNueva.getImagen());
                
                int filasAfectadas = prepararSetencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }   
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
