/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafxfarmacia.modelo.ConexionBD;
import javafxfarmacia.utils.Constantes;

/**
 *
 * @author dplat
 */
public class PedidoDAO {
     public static PedidoRespuesta obtenerInformacionPedido(){
        PedidoRespuesta respuesta = new PedidoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT p.idpedido, p.estado, p.fecha_pedido, p.fecha_entrega, p.cantidad, pr.nombre \n" +
"FROM pedidos p \n" +
"JOIN producto pr ON p.idProducto = pr.idProducto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Pedido> pedidoConsulta = new ArrayList();
                while(resultado.next()){
                     pedido.setIdpedido(resultado.getInt("idPedido"));
                    pedido.setFecha_Pedido(resultado.getString("fecha_pedido"));
                    pedido.setFecha_Entrega(resultado.getString("fecha_entrega"));
                    pedido.setNombre(resultado.getString("nombre"));
                    pedido.setCantidad(resultado.getInt("cantidad"));
                    pedido.setEstad(resultado.getString("estado"));

                    pedido.setVentaControlada(resultado.getBoolean("ventaControlada"));
                    pedido.setIdSucursal(resultado.getInt("sucursal_idSucursal"));
                    pedido.setNombreSucursal(resultado.getString("nombreSucursal"));
                    pedido.setCantidad(resultado.getInt("cantidad"));
                    pedido.setPresentacion(resultado.getString("presentacion"));
                    pedidoConsulta.add(pedido);

                }
                respuesta.setPedidos(pedidoConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
     
     
     public static PedidoRespuesta obtenerInformacionPedido(){
        PedidoRespuesta respuesta = new PedidoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT p.idpedido, p.estado, p.fecha_pedido, p.fecha_entrega, p.cantidad, pr.nombre \n" +
"FROM pedidos p \n" +
"JOIN producto pr ON p.idProducto = pr.idProducto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Pedido> pedidoConsulta = new ArrayList();
                while(resultado.next()){
                     pedido.setIdpedido(resultado.getInt("idPedido"));
                    pedido.setFecha_Pedido(resultado.getString("fecha_pedido"));
                    pedido.setFecha_Entrega(resultado.getString("fecha_entrega"));
                    pedido.setNombre(resultado.getString("nombre"));
                    pedido.setCantidad(resultado.getInt("cantidad"));
                    pedido.setEstad(resultado.getString("estado"));

                    pedido.setVentaControlada(resultado.getBoolean("ventaControlada"));
                    pedido.setIdSucursal(resultado.getInt("sucursal_idSucursal"));
                    pedido.setNombreSucursal(resultado.getString("nombreSucursal"));
                    pedido.setCantidad(resultado.getInt("cantidad"));
                    pedido.setPresentacion(resultado.getString("presentacion"));
                    pedidoConsulta.add(pedido);

                }
                respuesta.setPedidos(pedidoConsulta);
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
