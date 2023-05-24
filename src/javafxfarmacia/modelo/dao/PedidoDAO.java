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
    public static PedidoRespuesta obtenerInformacionPedido() {
    PedidoRespuesta respuesta = new PedidoRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
           String consulta = "SELECT p.fecha_pedido, p.fecha_entrega, GROUP_CONCAT(pr.nombre SEPARATOR ', ') AS nombres_productos, "
                    + "CASE "
                    + "WHEN p.idProveedor IS NOT NULL THEN prov.nombre "
                    + "ELSE suc.nombreSucursal "
                    + "END AS nombre_proveedor "
                    + "FROM pedidos p "
                    + "JOIN producto_pedido pp ON p.idpedido = pp.idPedido "
                    + "JOIN producto pr ON pp.idProducto = pr.idProducto "
                    + "LEFT JOIN proveedor prov ON p.idProveedor = prov.idProveedor "
                    + "LEFT JOIN sucursal suc ON p.idSucursal = suc.idSucursal "
                    + "GROUP BY p.fecha_pedido, p.fecha_entrega, nombre_proveedor";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Pedido> pedidoConsulta = new ArrayList();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setFecha_pedido(resultado.getString("fecha_pedido"));
                pedido.setFecha_entrega(resultado.getString("fecha_entrega"));
                pedido.setNombre_proveedor(resultado.getString("nombre_proveedor"));
                pedido.setNombre(resultado.getString("nombres_productos"));
                pedidoConsulta.add(pedido);
            }
            respuesta.setPedidos(pedidoConsulta);
            conexionBD.close();
        } catch (SQLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}
    
  public enum TipoProveedor {
    INTERNO,
    EXTERNO
}
     
      public static int guardarPedidoExterno(Pedido pedidoNuevo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO pedidos (fecha_pedido, fecha_entrega, idProveedor) VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, pedidoNuevo.getFecha_pedido());
                prepararSentencia.setString(2, pedidoNuevo.getFecha_entrega());
                prepararSentencia.setInt(5, pedidoNuevo.getIdProveedor());

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

    public static int guardarPedidoInterno(Pedido pedidoNuevo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO pedidos (fecha_pedido, fecha_entrega, idSucursal) VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, pedidoNuevo.getFecha_pedido());
                prepararSentencia.setString(2, pedidoNuevo.getFecha_entrega());
                prepararSentencia.setInt(5, pedidoNuevo.getIdSucursal());

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

     
    public static Pedido obtenerUltimoPedidoGuardado(){
    
    Pedido ultimoPedido = new Pedido();
    
      Connection conexionBD = ConexionBD.abrirConexionBD();
    
    if (conexionBD != null) {
        try {
            String sentencia = "SELECT * FROM pedidos ORDER BY idPedido DESC LIMIT 1";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            ResultSet resultSet = prepararSentencia.executeQuery(sentencia);
            
            if (resultSet.next()) {
                int idPedido= resultSet.getInt("idPedido");
                String fecha_pedido = resultSet.getString("fecha_pedido");
                String fecha_entrega = resultSet.getString("fecha_entrega");
                int idProveedor = resultSet.getInt("idProveedor");
                int idSucursal = resultSet.getInt("idSucursal");
                
                ultimoPedido.setIdPedido(idPedido);
                ultimoPedido.setFecha_pedido(fecha_pedido);
                ultimoPedido.setFecha_entrega(fecha_entrega);
                ultimoPedido.setIdProveedor(idProveedor);
                ultimoPedido.setIdSucursal(idSucursal);
            }
            
            conexionBD.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    return ultimoPedido;
}
    
    
    

    public static PedidoRespuesta obtenerProveedoresInternos() {
    PedidoRespuesta respuesta = new PedidoRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "select idSucursal, nombreSucursal from sucursal;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Pedido> pedidoConsulta = new ArrayList<>();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdSucursal(resultado.getInt("idSucursal")); 
                pedido.setNombre(resultado.getString("nombreSucursal"));
                pedidoConsulta.add(pedido);
            }
            respuesta.setPedidos(pedidoConsulta);
            conexionBD.close();
        } catch (SQLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}

  
    public static PedidoRespuesta obtenerProveedoresExternos() {
    PedidoRespuesta respuesta = new PedidoRespuesta();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "select idProveedor, nombre from proveedor;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Pedido> pedidoConsulta = new ArrayList<>();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdProveedor(resultado.getInt("idProveedor")); 
                pedido.setNombre(resultado.getString("nombre"));
                pedidoConsulta.add(pedido);
            }
            respuesta.setPedidos(pedidoConsulta);
            conexionBD.close();
        } catch (SQLException e) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }
    return respuesta;
}
         
}
