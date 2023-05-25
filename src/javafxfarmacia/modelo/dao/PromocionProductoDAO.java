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
import javafxfarmacia.modelo.pojo.PromocionProducto;
import javafxfarmacia.modelo.pojo.PromocionProductoRespuesta;
import javafxfarmacia.utils.Constantes;
import oracle.jrockit.jfr.tools.ConCatRepository;

/**
 *
 * @author jasie
 */
public class PromocionProductoDAO {
    public static PromocionProductoRespuesta obtenerInformacion(int idPromocion){
        PromocionProductoRespuesta respuesta = new PromocionProductoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try{
                String sentencia = "select idPromocion_Producto,idPromocion,promocion_producto.idProducto,\n" +
                    "producto.nombre as nombreProducto,promocion_producto.cantidad,precioUnitario,precioFinal\n" +
                    "from promocion_producto\n" +
                    "inner join producto on promocion_producto.idProducto = producto.idProducto\n" +
                    "where idPromocion = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1,idPromocion);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<PromocionProducto> promocionesTemporales = new ArrayList();
                while (resultado.next()){
                    PromocionProducto productoPromocionTemp = new PromocionProducto();
                    productoPromocionTemp.setIdPromocionProducto(resultado.getInt("idPromocion_Producto"));
                    productoPromocionTemp.setIdProducto(resultado.getInt("idProducto"));
                    productoPromocionTemp.setIdPromocion(resultado.getInt("idPromocion"));
                    productoPromocionTemp.setCantidad(resultado.getInt("cantidad"));
                    productoPromocionTemp.setPrecioUnitario(resultado.getDouble("precioUnitario"));
                    productoPromocionTemp.setPrecioFinal(resultado.getDouble("precioFinal"));
                    productoPromocionTemp.setNombreProducto(resultado.getString("nombreProducto"));
                    promocionesTemporales.add(productoPromocionTemp);
                }
                respuesta.setPromocionesProductoRespuesta(promocionesTemporales);
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
    
    public static int guardarPromocionProducto (PromocionProducto promocionProductoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "insert into promocion_producto (idPromocion,idProducto,"
                    + "cantidad,precioUnitario,precioFinal)"
                    + "values (?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, promocionProductoNuevo.getIdPromocion());
                prepararSentencia.setInt(2, promocionProductoNuevo.getIdProducto());
                prepararSentencia.setInt(3, promocionProductoNuevo.getCantidad());
                prepararSentencia.setDouble(4, promocionProductoNuevo.getPrecioUnitario());
                prepararSentencia.setDouble(5, promocionProductoNuevo.getPrecioFinal());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }

        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int actualizarPromocionProducto(PromocionProducto producPromoActualizar){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE promocion_producto SET  cantidad = ?, precioUnitario = ?, precioFinal = ? "
                        + "WHERE idPromocion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1,producPromoActualizar.getCantidad());
                prepararSentencia.setDouble(2, producPromoActualizar.getPrecioUnitario());
                prepararSentencia.setDouble(3, producPromoActualizar.getPrecioFinal());
                prepararSentencia.setInt(4, producPromoActualizar.getIdPromocion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                       
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarProductoPromocion (int idPromocion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "DELETE FROM promocion_producto WHERE idPromocion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPromocion);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
