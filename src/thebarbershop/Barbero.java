/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jaelj
 */
// ===== CLASE BARBERO =====
public class Barbero extends Usuario {
    private  String nombre;
    private  String email;
    private  String ciudad;
    private  String telefono;
    private  String contraseña;
    private int experiencia;
    private String nombreBarberia;
    private  byte[] fotoPerfil;
    private String horarioAtencion;
    private boolean disponible;
    // NUEVO: Lista para guardar las rutas de las imágenes del portafolio
    private final List<File> imagenesPortafolio;
    
    /**
     *
     * @param nombre
     * @param email
     * @param ciudad
     * @param telefono
     * @param contraseña
     * @param experiencia
     * @param nombreBarberia
     * @param fotoPerfil
     */
    public Barbero(String nombre, String email, String ciudad, String telefono, 
                   String contraseña, int experiencia, String nombreBarberia, byte[] fotoPerfil) {
        super(nombre, email, ciudad, telefono, contraseña,fotoPerfil);
        this.nombre = nombre;
        this.email = email;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.experiencia = experiencia;
        this.nombreBarberia = nombreBarberia;
        this.fotoPerfil = fotoPerfil;
        // NUEVO: Inicializar la lista de imágenes vacía
        this.imagenesPortafolio = new ArrayList<>();
    }

    
    
    @Override
    public String getTipoUsuario() {
        return "Barbero";
    }
    
    // Getters y Setters específicos del barbero 
    @Override
    public String getNombre() { return nombre; }
    @Override
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    @Override
    public String getEmail() { return email; }
    @Override
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String getCiudad() { return ciudad; }
    @Override
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    @Override
    public String getTelefono() { return telefono; }
    @Override
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    @Override
    public String getContraseña() { return contraseña; }
    @Override
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }
    
    public String getNombreBarberia() { return nombreBarberia; }
    public void setNombreBarberia(String nombreBarberia) { this.nombreBarberia = nombreBarberia; }
    
    @Override
    public byte[] getFotoPerfil() { return fotoPerfil; }
    @Override
    public void setFotoPerfil(byte[] fotoPerfil) { this.fotoPerfil = fotoPerfil; }
    
    
    // Métodos para manejar el portafolio de imágenes
    public List<File> getImagenesPortafolio() { 
        return imagenesPortafolio; 
    }
    
    public void agregarImagenPortafolio(File imagen) {
        if (imagen != null && imagen.exists()) {
            this.imagenesPortafolio.add(imagen);
            System.out.println("Imagen agregada: " + imagen.getName());
        }
    }
    
    public void agregarVariasImagenes(File[] imagenes) {
        for (File imagen : imagenes) {
            agregarImagenPortafolio(imagen);
        }
    }
    
    public int getCantidadImagenes() {
        return imagenesPortafolio.size();
    }
    
    @Override
    public String toString() {
        return super.toString() + " [BARBERO - " + nombreBarberia + "] - " 
               + getCantidadImagenes() + " fotos en portafolio";
    }
}

