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
                String sentencia = "select idPromocion,descripcion,precioInicial,descuento,"
                        + "precioFinal,fechaInicia,fechaTermino,\n" +
                        "producto.idProducto, producto.nombre as nombreProducto from promocion\n" +
                        "inner join producto on promocion.idProducto = producto.idProducto\n" +
                        "inner join sucursal on producto.sucursal_idSucursal = sucursal.idSucursal order by "
                        + "fechaTermino asc;";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionConsulta = new ArrayList();
                
                while(resultado.next()){
                    Promocion promocionTemporal = new Promocion();
                    promocionTemporal.setIdPromocion(resultado.getInt("idPromocion"));
                    promocionTemporal.setDescripcion(resultado.getString("descripcion"));
                    promocionTemporal.setNombreProducto(resultado.getString("nombreProducto"));
                    promocionTemporal.setIdProducto(resultado.getInt("idProducto"));
                    promocionTemporal.setDescuento(resultado.getDouble("descuento"));
                    promocionTemporal.setPrecioInicial(resultado.getDouble("precioInicial"));
                    promocionTemporal.setPrecioFinal(resultado.getDouble("precioFinal"));
                    promocionTemporal.setFechaInicio(resultado.getString("fechaInicia"));
                    promocionTemporal.setFechaTermino(resultado.getString("fechaTermino"));
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
                String consulta = "select idPromocion,descripcion,precioInicial,descuento,"
                        + "precioFinal,fechaInicia,fechaTermino,\n" +
                        "producto.idProducto, producto.nombre as nombreProducto from promocion\n" +
                        "inner join producto on promocion.idProducto = producto.idProducto\n" +
                        "inner join sucursal on producto.sucursal_idSucursal = sucursal.idSucursal  \n" +
                        "where nombre like ? order by fechaTermino asc;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, "%" + busqueda + "%");
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionConsulta = new ArrayList();
                while(resultado.next()){
                    Promocion promocionTemporal = new Promocion();
                    promocionTemporal.setIdPromocion(resultado.getInt("idPromocion"));
                    promocionTemporal.setDescripcion(resultado.getString("descripcion"));
                    promocionTemporal.setNombreProducto(resultado.getString("nombreProducto"));
                    promocionTemporal.setIdProducto(resultado.getInt("idProducto"));
                    promocionTemporal.setDescuento(resultado.getDouble("descuento"));
                    promocionTemporal.setPrecioInicial(resultado.getDouble("precioInicial"));
                    promocionTemporal.setPrecioFinal(resultado.getDouble("precioFinal"));
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
    
    private static int registrarPromocion(Promocion promocionNueva){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "";
                PreparedStatement prepararSetencia = conexionBD.prepareStatement(sentencia);
                

                
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
