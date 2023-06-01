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
           String consulta = "SELECT p.idpedido, p.fecha_pedido, p.fecha_entrega, GROUP_CONCAT(pr.nombre SEPARATOR ', ') AS nombres_productos,\n" +
"       CASE\n" +
"         WHEN p.idProveedor IS NOT NULL THEN prov.nombre\n" +
"         ELSE suc.nombreSucursal\n" +
"       END AS nombre_proveedor\n" +
"FROM pedidos p\n" +
"JOIN producto_pedido pp ON p.idpedido = pp.idPedido\n" +
"JOIN producto pr ON pp.idProducto = pr.idProducto\n" +
"LEFT JOIN proveedor prov ON p.idProveedor = prov.idProveedor\n" +
"LEFT JOIN sucursal suc ON p.idSucursal = suc.idSucursal\n" +
"GROUP BY p.idpedido, p.fecha_pedido, p.fecha_entrega, nombre_proveedor;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Pedido> pedidoConsulta = new ArrayList();
            while (resultado.next()) {
                Pedido pedido = new Pedido();
                pedido.setFecha_pedido(resultado.getString("fecha_pedido"));
                pedido.setFecha_entrega(resultado.getString("fecha_entrega"));
                pedido.setNombre_proveedor(resultado.getString("nombre_proveedor"));
                pedido.setNombre(resultado.getString("nombres_productos"));
                pedido.setIdPedido(resultado.getInt("idpedido"));
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
                  prepararSentencia.setInt(3, pedidoNuevo.getIdProveedor());
                  
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
                prepararSentencia.setInt(3, pedidoNuevo.getIdSucursal());

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

     
       public static int IdentificadorProveedor(String proveedor) {
        int idRespuesta = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT id, nombre\n" +
        "FROM (\n" +
        "    SELECT idSucursal AS id, nombreSucursal AS nombre\n" +
        "    FROM sucursal\n" +
        "    UNION ALL\n" +
        "    SELECT idProveedor AS id, nombre\n" +
        "    FROM proveedor\n" +
        ") AS result\n" +
        "WHERE nombre = ? ;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, proveedor);
                ResultSet respuesta = prepararSentencia.executeQuery();
                    if (respuesta.next()) {
               idRespuesta = respuesta.getInt("id");
                
                
            }
            
                conexionBD.close();
            } catch (SQLException e) {
                idRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            idRespuesta = Constantes.ERROR_CONEXION;
        }
        return idRespuesta;
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
                pedido.setNombre_proveedor(resultado.getString("nombreSucursal"));
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

    public static int modificarPedidoInterno(Pedido pedidoActualizar) {
          int respuesta;  
          Connection conexionBD = ConexionBD.abrirConexionBD();
          if (conexionBD != null) {
              try {
                  String sentencia = "UPDATE pedidos SET fecha_entrega = ?,  idSucursal = ? WHERE idPedido = ?;";
                  PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                  prepararSentencia.setString(1, pedidoActualizar.getFecha_entrega());
                  prepararSentencia.setInt(2, pedidoActualizar.getIdSucursal());
                  prepararSentencia.setInt(3, pedidoActualizar.getIdPedido());
                  
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
    
        public static int modificarPedidoExterno(Pedido pedidoActualizar) {
          int respuesta;  
          Connection conexionBD = ConexionBD.abrirConexionBD();
          if (conexionBD != null) {
              try {
                  String sentencia = "UPDATE pedidos SET fecha_entrega = ?,  idProveedor = ? WHERE idPedido = ?;";
                  PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                  prepararSentencia.setString(1, pedidoActualizar.getFecha_entrega());
                  prepararSentencia.setInt(2, pedidoActualizar.getIdProveedor());
                  prepararSentencia.setInt(3, pedidoActualizar.getIdPedido());
                  System.out.println("MODIFICAR PEDIDO EXTERNO :" + prepararSentencia);
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
                pedido.setNombre_proveedor(resultado.getString("nombre"));
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
