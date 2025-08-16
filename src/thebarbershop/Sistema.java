/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop;

/**
 *
 * @author jaelj
 */
// ===== CLASE SISTEMA (MANEJA LA LÓGICA PRINCIPAL) =====
import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Usuario> usuarios;
    private List<Cita> citas;
    private Usuario usuarioActual;
    
    public Sistema() {
        usuarios = new ArrayList<>();
        citas = new ArrayList<>();
        usuarioActual = null;
        
    }
    
    // Método para registrar usuario
    public boolean registrarUsuario(String nombre, String email, String ciudad, 
                                   String telefono, String contraseña,int experiencia, String tipoUsuario, 
                                   String nombreBarberia) {
        // Verificar si el email ya existe
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return false; // Email ya registrado
            }
        }
        
        byte[] fotoPerfil = null;
        Usuario nuevoUsuario;
        if (tipoUsuario.equals("Barbero")) {
            nuevoUsuario = new Barbero(nombre, email, ciudad, telefono, contraseña, experiencia, nombreBarberia,fotoPerfil);
        } else {
            nuevoUsuario = new Cliente(nombre, email, ciudad, telefono, contraseña, fotoPerfil);
        }
        
        usuarios.add(nuevoUsuario);
        return true;
    }
    
    // Método para iniciar sesión
    public boolean iniciarSesion(String email, String contraseña) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getContraseña().equals(contraseña)) {
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }
    
    // Método para cerrar sesión
    public void cerrarSesion() {
        usuarioActual = null;
    }
    
    // Getters
    public Usuario getUsuarioActual() { return usuarioActual; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Cita> getCitas() { return citas; }
    
    // Método para obtener barberos disponibles
    /*public List<Barbero> getBarberosDisponibles() {
        List<Barbero> barberos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Barbero && ((Barbero) u).isDisponible()) {
                barberos.add((Barbero) u);
            }
        }
        return barberos;
    }*/
    
    // Método para agendar cita
    public boolean agendarCita(Barbero barbero, String fecha, String hora, String lugar) {
        if (usuarioActual instanceof Cliente) {
            Cita nuevaCita = new Cita((Cliente) usuarioActual, barbero, fecha, hora, lugar);
            citas.add(nuevaCita);
            return true;
        }
        return false;
    }
    
    // Método para obtener citas de un usuario
    public List<Cita> getCitasUsuario(Usuario usuario) {
        List<Cita> citasUsuario = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getCliente().equals(usuario) || c.getBarbero().equals(usuario)) {
                citasUsuario.add(c);
            }
        }
        return citasUsuario;
    }
}