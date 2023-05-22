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
            String consulta = "SELECT pe.fecha_pedido, pe.fecha_entrega, "
                    + "COALESCE(pr.nombre, su.nombreSucursal) AS nombre_proveedor, "
                    + "GROUP_CONCAT(DISTINCT p.nombre) AS productos_combinados "
                    + "FROM pedidos AS pe "
                    + "JOIN producto AS p ON pe.idProducto = p.idProducto "
                    + "LEFT JOIN proveedor AS pr ON pe.idProveedor = pr.idProveedor "
                    + "LEFT JOIN sucursal AS su ON pe.idSucursal = su.idSucursal "
                    + "GROUP BY pe.fecha_pedido, pe.fecha_entrega, pe.idProveedor, pe.idSucursal "
                    + "HAVING COUNT(*) > 1";
            
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Pedido> pedidoConsulta = new ArrayList();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setFecha_pedido(resultado.getString("fecha_pedido"));
                pedido.setFecha_entrega(resultado.getString("fecha_entrega"));
                pedido.setNombre_proveedor(resultado.getString("nombre_proveedor"));
                pedido.setProductos_combinados(resultado.getString("productos_combinados"));
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

     
     
     
      public static int guardarPedidoExterno(Pedido pedidoNuevo) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO pedidos (fecha_pedido, fecha_entrega, cantidad, idProducto, idProveedor) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, pedidoNuevo.getFecha_pedido());
                prepararSentencia.setString(2, pedidoNuevo.getFecha_entrega());
                prepararSentencia.setInt(3, pedidoNuevo.getCantidad());
                prepararSentencia.setInt(4, pedidoNuevo.getIdProducto());
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
                String sentencia = "INSERT INTO pedidos (fecha_pedido, fecha_entrega, cantidad, idProducto, idSucursal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, pedidoNuevo.getFecha_pedido());
                prepararSentencia.setString(2, pedidoNuevo.getFecha_entrega());
                prepararSentencia.setInt(3, pedidoNuevo.getCantidad());
                prepararSentencia.setInt(4, pedidoNuevo.getIdProducto());
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

     
     
  public enum TipoProveedor {
    INTERNO,
    EXTERNO
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
