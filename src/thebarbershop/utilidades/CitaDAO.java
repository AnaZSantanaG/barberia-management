/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 *
 * @author Mamore e Ika
 */
public class CitaDAO {
    // Obtener todos los barberos disponibles
    public static void cargarBarberos(javax.swing.JComboBox<String> combo) {
        String sql = "SELECT p.nombre_completo FROM peluqueros p JOIN users u ON p.id_users = u.idusers WHERE u.tipo = 'peluquero'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            combo.removeAllItems();
            combo.addItem("Seleccione Barbero...");
            while (rs.next()) {
                combo.addItem(rs.getString("nombre_completo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Obtener todos los estilos de corte
    public static void cargarServicios(javax.swing.JComboBox<String> combo) {
        String sql = "SELECT nombre_estilo FROM estilos_corte";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            combo.removeAllItems();
            combo.addItem("Seleccione Servicio...");
            while (rs.next()) {
                combo.addItem(rs.getString("nombre_estilo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
 * Obtiene los horarios disponibles de un barbero para un día de la semana
 * @param nombreBarbero Nombre del barbero
 * @param diaSemana Día de la semana (ej: "LUNES")
 * @return Lista de horarios disponibles en formato "HH:mm"
 */
    public static List<String> obtenerHorariosDisponibles(String nombreBarbero, String diaSemana) {
        List<String> horarios = new ArrayList<>();
        String sql = "SELECT dp.hora_inicio, dp.hora_fin " +
                     "FROM disponibilidad_pel dp " +
                     "JOIN peluqueros p ON dp.id_peluquero = p.id_Peluquero " +
                     "WHERE p.nombre_completo = ? AND dp.dia_semana = ? " +
                     "ORDER BY dp.hora_inicio";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreBarbero);
            ps.setString(2, diaSemana.toUpperCase());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Time inicio = rs.getTime("hora_inicio");
                Time fin = rs.getTime("hora_fin");

                // Generar intervalos de 30 min o 45 min según duración del corte
                Calendar cal = Calendar.getInstance();
                cal.setTime(inicio);

                while (cal.getTime().before(fin)) {
                    String hora = new SimpleDateFormat("HH:mm").format(cal.getTime());
                    // Verificar si ya hay cita en ese horario
                    if (esDisponible(nombreBarbero, diaSemana, hora)) {
                        horarios.add(hora);
                    }
                    cal.add(Calendar.MINUTE, 30); // Ajusta según duración del servicio
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

/**
 * Verifica si una hora específica está disponible (no hay cita)
 */
    // Verificar si un barbero está disponible en una fecha y hora
    public static boolean esDisponible(String nombreBarbero, String fecha, String hora) {
        String sql = "SELECT COUNT(*) FROM citas c " +
                     "JOIN peluqueros p ON c.id_peluquero = p.id_Peluquero " +
                     "WHERE p.nombre_completo = ? AND c.fecha_cita = ? AND c.estado = 'PENDIENTE'";
        
        String fechaHora = fecha + " " + hora; // "2025-04-05 09:00:00"

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreBarbero);
            ps.setString(2, fechaHora);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true si no hay cita
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Agendar cita
    public static boolean agendarCita(
        String emailCliente, String nombreBarbero, String servicio, 
        String fecha, String hora, String notas
    ){
        String sqlCliente = "SELECT id_clientes FROM clientes c JOIN users u ON c.id_users = u.idusers WHERE u.email = ?";
        String sqlBarbero = "SELECT id_Peluquero FROM peluqueros WHERE nombre_completo = ?";
        String sqlEstilo = "SELECT id_estilos FROM estilos_corte WHERE nombre_estilo = ?";
        String sqlCita = "INSERT INTO citas (id_cliente, id_peluquero, id_estilo, fecha_cita, estado, notas, metodo_pago) VALUES (?, ?, ?, ?, 'PENDIENTE', ?, 'EFECTIVO')";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int idCliente = 0, idBarbero = 0, idEstilo = 0;

            // Obtener id_cliente
            try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
                ps.setString(1, emailCliente);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idCliente = rs.getInt(1); else throw new SQLException("Cliente no encontrado");
            }

            // Obtener id_peluquero
            try (PreparedStatement ps = conn.prepareStatement(sqlBarbero)) {
                ps.setString(1, nombreBarbero);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idBarbero = rs.getInt(1); else throw new SQLException("Barbero no encontrado");
            }

            // Obtener id_estilo
            try (PreparedStatement ps = conn.prepareStatement(sqlEstilo)) {
                ps.setString(1, servicio);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idEstilo = rs.getInt(1); else throw new SQLException("Servicio no encontrado");
            }

            // Insertar cita
            try (PreparedStatement ps = conn.prepareStatement(sqlCita)) {
                ps.setInt(1, idCliente);
                ps.setInt(2, idBarbero);
                ps.setInt(3, idEstilo);
                ps.setString(4, fecha + " " + hora);
                ps.setString(5, notas);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try (var conn = DatabaseConnection.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    

    /**
     * Obtiene la próxima cita pendiente del cliente
     * @param emailCliente Correo del cliente
     * @return String con mensaje de la cita o null si no hay
     */
    public static String obtenerCitaCliente(String emailCliente) {
        String sql = "SELECT c.fecha_cita, p.nombre_completo " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id_clientes " +
                     "JOIN users u ON cl.id_users = u.idusers " +
                     "JOIN peluqueros p ON c.id_peluquero = p.id_Peluquero " +
                     "WHERE u.email = ? AND c.estado = 'PENDIENTE' " +
                     "ORDER BY c.fecha_cita LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDateTime fechaHora = rs.getTimestamp("fecha_cita").toLocalDateTime();
                String fecha = fechaHora.toLocalDate().toString();
                String hora = fechaHora.toLocalTime().toString();

                // Formato de hora: solo HH:mm
                String horaCita = hora.substring(0, 5);

                return "Tienes una cita para " + fecha + " a las " + horaCita;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}