/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxfarmacia.modelo.pojo.Usuario;
import javafxfarmacia.utils.Constantes;
import javafxfarmacia.modelo.ConexionBD;

/**
 *
 * @author kikga
 */
public class SesionDAO {
    
    public static Usuario verificarUsuarioSesion(String usuario, String password){
        Usuario usuarioVerificado = new Usuario();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            try {
                String consulta = "select * from usuario where username = ? and password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setEmail(resultado.getString("email"));
                    usuarioVerificado.setUsername(resultado.getString("username"));
                    usuarioVerificado.setPassword(resultado.getString("password"));
                    usuarioVerificado.setTipo(resultado.getInt("tipoUsuario"));
                }
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
}
