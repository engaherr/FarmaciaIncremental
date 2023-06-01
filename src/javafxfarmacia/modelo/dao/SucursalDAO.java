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
import javafxfarmacia.modelo.pojo.Sucursal;
import javafxfarmacia.modelo.pojo.SucursalRespuesta;
import javafxfarmacia.utils.Constantes;

/**
 *
 * @author kikga
 */
public class SucursalDAO {
    public static SucursalRespuesta obtenerInformacionSucursales(){
        SucursalRespuesta respuesta = new SucursalRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select idSucursal, nombreSucursal from sucursal";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Sucursal> sucursalesConsulta = new ArrayList();
                while (resultado.next()) {
                    Sucursal sucursal = new Sucursal();
                    sucursal.setIdSucursal(resultado.getInt("idSucursal"));
                    sucursal.setNombre(resultado.getString("nombreSucursal"));
                    sucursalesConsulta.add(sucursal);
                }
                respuesta.setSucursales(sucursalesConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
