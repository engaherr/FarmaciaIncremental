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
import javafxfarmacia.modelo.pojo.Producto;
import javafxfarmacia.modelo.pojo.ProductoRespuesta;
import javafxfarmacia.utils.Constantes;

/**
 *
 * @author dplat
 */
public class ProductoDAO {
    public static ProductoRespuesta obtenerInformacionProducto(int idProducto){
    ProductoRespuesta respuesta = new ProductoRespuesta();
    Connection conexionBD = ConexionBD.abrirConexionBD();
      if(conexionBD != null){
            try{
                String consulta = "SELECT idProducto,nombre,precio FROM producto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Producto> productos = new ArrayList();
                
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setPrecio(resultado.getFloat("precio"));
                    productos.add(producto);
                    
                
                }
                respuesta.setProductos(productos);
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
    
    
    
    
    
    
    
         public static ProductoRespuesta obtenerInformacionBusqueda(String busqueda){
    ProductoRespuesta respuesta = new ProductoRespuesta();
    Connection conexionBD = ConexionBD.abrirConexionBD();
      if(conexionBD != null){
            try{
                String consulta = "SELECT idProducto,nombre FROM producto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Producto> productos = new ArrayList();
                
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    productos.add(producto);
                    
                
                }
                respuesta.setProductos(productos);
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
    
    
         public static ProductoRespuesta obtenerInformacionPedido(int idPedido){
    ProductoRespuesta respuesta = new ProductoRespuesta();
    Connection conexionBD = ConexionBD.abrirConexionBD();
      if(conexionBD != null){
            try{
                String consulta = "SELECT producto.nombre, producto.precio,  producto_pedido.idProducto, producto_pedido.cantidad \n" +
            "FROM producto_pedido \n" +
            "INNER JOIN producto ON producto.idProducto = producto_pedido.idProducto\n" +
            " WHERE idPedido = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                   prepararSentencia.setInt(1, idPedido);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Producto> productos = new ArrayList();
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setPrecioUnitario(resultado.getInt("precio"));
                    producto.setCantidad(resultado.getInt("cantidad"));
                    productos.add(producto);
                    System.out.println("ID PRODUCTO: "+producto.getIdProducto());
                
                }
                respuesta.setProductos(productos);
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
    
}
