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
import javafxfarmacia.modelo.pojo.Pedido;
import javafxfarmacia.modelo.pojo.PedidoRespuesta;
import javafxfarmacia.modelo.pojo.Producto;
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
                String consulta = "SELECT p.idpedido, p.estado, p.fecha_pedido, p.fecha_entrega, p.cantidad, pr.nombre FROM pedidos p INNER JOIN producto pr ON p.idProducto = pr.idProducto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Pedido> pedidoConsulta = new ArrayList();
                while(resultado.next()){
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(resultado.getInt("idPedido"));
                    pedido.setFecha_pedido(resultado.getString("fecha_pedido"));
                    pedido.setFecha_entrega(resultado.getString("fecha_entrega"));
                    pedido.setNombre(resultado.getString("nombre"));
                    pedido.setCantidad(resultado.getInt("cantidad"));
                    pedido.setEstado(resultado.getString("estado"));
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
     
     
     public static int guardarPedido(Pedido pedidoNuevo) {
    int respuesta;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String sentencia = "INSERT INTO pedidos (fecha_pedido, fecha_entrega, cantidad, idProducto) VALUES (?, ?, ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, pedidoNuevo.getFecha_pedido());
            prepararSentencia.setString(2, pedidoNuevo.getFecha_entrega());
            prepararSentencia.setInt(3, pedidoNuevo.getCantidad());
            prepararSentencia.setInt(4, pedidoNuevo.getIdProducto());
            int filasAfectadas = prepararSentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            conexionBD.close();
        } catch (SQLException e) {
            respuesta = Constantes.ERROR_CONSULTA;
        }
    } else {
        respuesta = Constantes.ERROR_CONEXION;
    }
    return respuesta;
}

}
