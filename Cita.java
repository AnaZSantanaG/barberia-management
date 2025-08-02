/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop;

/**
 *
 * @author jaelj
 */
// ===== CLASE CITA =====
public class Cita {
    private static int contadorId = 1;
    private int id;
    private Cliente cliente;
    private Barbero barbero;
    private String fecha;
    private String hora;
    private String lugar;
    private String estado; // "Agendada", "Completada", "Cancelada"
    private double precio;
    
    public Cita(Cliente cliente, Barbero barbero, String fecha, String hora, String lugar) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.barbero = barbero;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.estado = "Agendada";
        this.precio = 15.0; // Precio por defecto
    }
    
    // Getters y Setters
    public int getId() { return id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Barbero getBarbero() { return barbero; }
    public void setBarbero(Barbero barbero) { this.barbero = barbero; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    // Métodos útiles
    public void completarCita() {
        this.estado = "Completada";
    }
    
    public void cancelarCita() {
        this.estado = "Cancelada";
    }
    
    @Override
    public String toString() {
        return "Cita #" + id + " - " + cliente.getNombre() + 
               " con " + barbero.getNombre() + 
               " (" + fecha + " a las " + hora + ") - " + estado;
    }
}
