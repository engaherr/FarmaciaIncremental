/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxfarmacia.modelo.pojo;

/**
 *
 * @author kikga
 */
public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String username;
    private String password;
    private int codigoRespuesta;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String username, String password, int codigoRespuesta) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.username = username;
        this.password = password;
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    @Override
    public String toString(){
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    
}
