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
    
    
    
}
