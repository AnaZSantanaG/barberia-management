/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop;

/**
 *
 * @author jaelj
 */
// ===== CLASE USUARIO (CLASE BASE) =====
public abstract class Usuario {
    protected String nombre;
    protected String email;
    protected String ciudad;
    protected String telefono;
    protected String contraseña;
    protected byte[] fotoPerfil;
    
    
    // Constructor
    
    public Usuario(String nombre, String email, String ciudad, String telefono, String contraseña, byte[] fotoPerfil) {
        this.nombre = nombre;
        this.email = email;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.fotoPerfil = fotoPerfil;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public byte[] getFotoPerfil(){return fotoPerfil;}
    public void setFotoPerfil(byte[] fotoPerfil){ this.fotoPerfil = fotoPerfil; }
    
    // Método abstracto que cada tipo de usuario implementará
    public abstract String getTipoUsuario();
    
    @Override
    public String toString() {
        return "Usuario: " + nombre + " (" + email + ") - " + ciudad;
    }
}
