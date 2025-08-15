/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop;

/**
 *
 * @author jaelj
 */
// ===== CLASE CLIENTE =====
public class Cliente extends Usuario {
    
    public Cliente(String nombre, String email, String ciudad, String telefono, String contraseña,byte[] fotoPerfil) {
        super(nombre, email, ciudad, telefono, contraseña,fotoPerfil);
    }
    
    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }
    
    @Override
    public String toString() {
        return super.toString() + " [CLIENTE]";
    }
}
