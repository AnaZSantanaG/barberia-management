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
    private String nombreBarberia;
    private String horarioAtencion;
    private boolean disponible;
    private int experiencia; //varible que no estaba pero se solicita en el frame.
    
    // NUEVO: Lista para guardar las rutas de las imágenes del portafolio
    private List<File> imagenesPortafolio;
    
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
                   String contraseña, int experiencia, String nombreBarberia,byte[] fotoPerfil) {
        super(nombre, email, ciudad, telefono, contraseña,fotoPerfil);
        this.nombreBarberia = nombreBarberia;
        this.horarioAtencion = "9:00 AM - 6:00 PM"; // Horario por defecto
        this.disponible = true;
        this.experiencia = experiencia;
        
        // NUEVO: Inicializar la lista de imágenes vacía
        this.imagenesPortafolio = new ArrayList<>();
    }

    
    
    @Override
    public String getTipoUsuario() {
        return "Barbero";
    }
    
    // Getters y Setters específicos del barbero 
    public String getNombreBarberia() { return nombreBarberia; }
    public void setNombreBarberia(String nombreBarberia) { this.nombreBarberia = nombreBarberia; }
    
    public String getHorarioAtencion() { return horarioAtencion; }
    public void setHorarioAtencion(String horarioAtencion) { this.horarioAtencion = horarioAtencion; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    
    public int getExperiencia() {return experiencia;}
    public void setExperiencia(int experiencia) {this.experiencia = experiencia;}
    
    
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

