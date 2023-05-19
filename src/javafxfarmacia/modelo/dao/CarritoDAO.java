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

public class CarritoDAO {
    public static ProductoRespuesta obtenerInformacionCarrito(int idProducto) {
        ProductoRespuesta respuesta = new ProductoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProducto, nombre, precio FROM producto WHERE idProducto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProducto);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Producto> productos = new ArrayList<>();
                
                while (resultado.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setPrecio(resultado.getFloat("precio"));
                    productos.add(producto);
                }
                
                respuesta.setProductos(productos);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return respuesta;
    }
}
