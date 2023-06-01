/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafxfarmacia.modelo.ConexionBD;
import javafxfarmacia.modelo.pojo.ProductoPedido;
import javafxfarmacia.utils.Constantes;

/**
 *
 * @author dplat
 */
public class ProductoPedidoDAO {
     public static int guardarProductoPedido (ProductoPedido ProductoPedidoNuevo){
        int respuesta;
             System.out.println("entró a guardarProductoPedido");
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO producto_pedido (idPedido, idProducto, cantidad)\n" +
"VALUES (?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, ProductoPedidoNuevo.getIdPedido());
                prepararSentencia.setInt(2, ProductoPedidoNuevo.getIdProducto());
                prepararSentencia.setInt(3, ProductoPedidoNuevo.getCantidad());
                 System.out.println("sentencia de prod-pedi:  "+ prepararSentencia);
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
     
     
      public static int eliminarProductoPedido (int idPedido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "DELETE FROM producto_pedido WHERE idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPedido);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas >= 1 ) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
                
            }catch(SQLException ex){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
