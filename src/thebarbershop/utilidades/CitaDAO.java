/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
     * MÉTODO CORREGIDO: Obtiene los horarios disponibles de un barbero para un día específico
     * @param nombreBarbero
     * @param diaSemana
     * @return 
     */
    public static List<String> obtenerHorariosDisponibles(String nombreBarbero, String diaSemana) {
        List<String> horarios = new ArrayList<>();
        String sql = "SELECT dp.hora_inicio, dp.hora_fin " +
                     "FROM disponibilidad_pel dp " +
                     "JOIN peluqueros p ON dp.id_peluquero = p.id_Peluquero " +
                     "WHERE p.nombre_completo = ? AND dp.dia_semana = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreBarbero);
            ps.setString(2, diaSemana.toUpperCase());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Time horaInicio = rs.getTime("hora_inicio");
                Time horaFin = rs.getTime("hora_fin");
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(horaInicio);
                
                Calendar fin = Calendar.getInstance();
                fin.setTime(horaFin);
                
                // FORMATO CORREGIDO: AM/PM
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                
                // Generar horarios cada 30 minutos
                while (cal.before(fin)) {
                    String hora = sdf.format(cal.getTime());
                    horarios.add(hora);
                    cal.add(Calendar.MINUTE, 30);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    /**
     * MÉTODO CORREGIDO: Verifica si un barbero trabaja en un día específico
     * @param nombreBarbero
     * @param diaSemana
     * @return 
     */
    public static boolean trabajaDia(String nombreBarbero, String diaSemana) {
        String sql = "SELECT COUNT(*) FROM disponibilidad_pel dp " +
                     "JOIN peluqueros p ON dp.id_peluquero = p.id_Peluquero " +
                     "WHERE p.nombre_completo = ? AND dp.dia_semana = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreBarbero);
            ps.setString(2, diaSemana.toUpperCase());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * MÉTODO CORREGIDO: Verifica disponibilidad específica de hora
     * @param nombreBarbero
     * @param fecha
     * @param hora
     * @return 
     */
    public static boolean esDisponible(String nombreBarbero, String fecha, String hora) {
        // Convertir hora de formato "hh:mm AM/PM" a "HH:mm:ss"
        String horaConvertida = convertirHoraABD(hora);
        
        String sql = "SELECT COUNT(*) FROM citas c " +
                     "JOIN peluqueros p ON c.id_peluquero = p.id_Peluquero " +
                     "WHERE p.nombre_completo = ? AND DATE(c.fecha_cita) = STR_TO_DATE(?, '%d/%m/%Y') " +
                     "AND TIME(c.fecha_cita) = ? AND c.estado = 'PENDIENTE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreBarbero);
            ps.setString(2, fecha);
            ps.setString(3, horaConvertida);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true si NO hay cita
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * MÉTODO AUXILIAR: Convierte hora de formato AM/PM a 24 horas
     */
    private static String convertirHoraABD(String horaAMPM) {
        try {
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss");
            return sdf24.format(sdf12.parse(horaAMPM));
        } catch (Exception e) {
            e.printStackTrace();
            return "00:00:00";
        }
    }

    /**
     * MÉTODO CORREGIDO: Agendar cita con validaciones mejoradas
     * @param emailCliente
     * @param nombreBarbero
     * @param servicio
     * @param fecha
     * @param hora
     * @param notas
     * @return 
     */
    public static boolean agendarCita(String emailCliente, String nombreBarbero, 
                                    String servicio, String fecha, String hora, String notas) {
        
        // 1. Validar que el barbero trabaje ese día
        String diaSemana = obtenerDiaSemana(fecha);
        if (!trabajaDia(nombreBarbero, diaSemana)) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "El barbero " + nombreBarbero + " no trabaja los " + diaSemana.toLowerCase());
            return false;
        }

        // 2. Validar disponibilidad de hora
        if (!esDisponible(nombreBarbero, fecha, hora)) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "El barbero ya tiene una cita programada en ese horario");
            return false;
        }

        // 3. Proceder con el agendamiento
        String sqlCliente = "SELECT id_clientes FROM clientes c JOIN users u ON c.id_users = u.idusers WHERE u.email = ?";
        String sqlBarbero = "SELECT id_Peluquero FROM peluqueros WHERE nombre_completo = ?";
        String sqlEstilo = "SELECT id_estilos FROM estilos_corte WHERE nombre_estilo = ?";
        String sqlCita = "INSERT INTO citas (id_cliente, id_peluquero, id_estilo, fecha_cita, estado, notas, metodo_pago) VALUES (?, ?, ?, ?, 'PENDIENTE', ?, 'EFECTIVO')";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int idCliente = 0, idBarbero = 0, idEstilo = 0;

            // Obtener IDs
            try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
                ps.setString(1, emailCliente);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idCliente = rs.getInt(1); 
                else throw new SQLException("Cliente no encontrado");
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlBarbero)) {
                ps.setString(1, nombreBarbero);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idBarbero = rs.getInt(1); 
                else throw new SQLException("Barbero no encontrado");
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlEstilo)) {
                ps.setString(1, servicio);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idEstilo = rs.getInt(1); 
                else throw new SQLException("Servicio no encontrado");
            }

            // Insertar cita
            try (PreparedStatement ps = conn.prepareStatement(sqlCita)) {
                ps.setInt(1, idCliente);
                ps.setInt(2, idBarbero);
                ps.setInt(3, idEstilo);
                
                // Convertir fecha y hora para MySQL
                String fechaHora = convertirFechaHoraParaMySQL(fecha, hora);
                ps.setString(4, fechaHora);
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
     * MÉTODO AUXILIAR: Obtiene el día de la semana de una fecha
     */
    private static String obtenerDiaSemana(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(fecha));
            
            String[] dias = {"", "DOMINGO", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"};
            return dias[cal.get(Calendar.DAY_OF_WEEK)];
        } catch (Exception e) {
            e.printStackTrace();
            return "LUNES";
        }
    }

    /**
     * MÉTODO AUXILIAR: Convierte fecha y hora para MySQL
     */
    private static String convertirFechaHoraParaMySQL(String fecha, String hora) {
        try {
            // Convertir fecha de dd/MM/yyyy a yyyy-MM-dd
            SimpleDateFormat fechaInput = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat fechaOutput = new SimpleDateFormat("yyyy-MM-dd");
            String fechaMySQL = fechaOutput.format(fechaInput.parse(fecha));
            
            // Convertir hora de AM/PM a 24 horas
            String horaMySQL = convertirHoraABD(hora);
            
            return fechaMySQL + " " + horaMySQL;
        } catch (Exception e) {
            e.printStackTrace();
            return fecha + " 00:00:00";
        }
    }

    /**
     * Obtiene la próxima cita pendiente del cliente
     * @param emailCliente
     * @return 
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
                String horaCita = hora.substring(0, 5);

                return "Tienes una cita para " + fecha + " a las " + horaCita;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}