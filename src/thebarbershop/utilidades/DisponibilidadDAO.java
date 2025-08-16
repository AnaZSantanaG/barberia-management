// thebarbershop.utilidades.DisponibilidadDAO
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Mamore e Ika
 */
public class DisponibilidadDAO {
    // Modelo interno para horarios
    public static class Horario {
        public String diaSemana;
        public String horaInicio;
        public String horaFin;

        public Horario(String dia, String inicio, String fin) {
            this.diaSemana = dia;
            this.horaInicio = inicio;
            this.horaFin = fin;
        }

        @Override
        public String toString() {
            return diaSemana + " (" + horaInicio + " - " + horaFin + ")";
        }
    }

    // MÉTODO CORREGIDO: Obtener horarios por ID de peluquero con formato AM/PM
    public static List<String> obtenerHorariosDisponibles(int idPeluquero, String nombreBarbero, String diaSemana) {
        List<String> horarios = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT dp.hora_inicio, dp.hora_fin " +
            "FROM disponibilidad_pel dp " +
            "JOIN peluqueros p ON dp.id_peluquero = p.id_Peluquero " +
            "WHERE p.id_Peluquero = ?"
        );
        
        // Manejar nombreBarbero
        if (nombreBarbero != null) {
            sql.append(" AND p.nombre_completo = ?");
        }
        
        // Manejar diaSemana
        if (diaSemana != null) {
            sql.append(" AND dp.dia_semana = ?");
        }
        
        sql.append(" ORDER BY dp.hora_inicio");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            ps.setInt(paramIndex++, idPeluquero);
            
            if (nombreBarbero != null) {
                ps.setString(paramIndex++, nombreBarbero);
            }
            
            if (diaSemana != null) {
                ps.setString(paramIndex++, diaSemana.toUpperCase());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Time inicio = rs.getTime("hora_inicio");
                Time fin = rs.getTime("hora_fin");
                
                // FORMATO CORREGIDO: Convertir a AM/PM
                SimpleDateFormat formatoAMPM = new SimpleDateFormat("hh:mm a");
                String inicioStr = formatoAMPM.format(inicio);
                String finStr = formatoAMPM.format(fin);
                
                horarios.add(inicioStr + " - " + finStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    // Agregar nuevo horario
    public static boolean agregarHorario(int idPeluquero, String diaSemana, String horaInicio, String horaFin) {
        String sql = "INSERT INTO disponibilidad_pel (id_peluquero, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeluquero);
            ps.setString(2, diaSemana);
            ps.setTime(3, Time.valueOf(horaInicio + ":00"));
            ps.setTime(4, Time.valueOf(horaFin + ":00"));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean eliminarHorarios(int idPeluquero) {
        String sql = "DELETE FROM disponibilidad_pel WHERE id_peluquero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPeluquero);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // MÉTODO CORREGIDO: Ajustar hora para turno AM/PM
    public static String ajustarHoraParaTurno(String hora, String turno) {
        try {
            // Si la hora ya tiene formato AM/PM, devolverla tal como está
            if (hora.toUpperCase().contains("AM") || hora.toUpperCase().contains("PM")) {
                return hora;
            }
            
            // Si es hora en formato 24h o simple
            String[] partes = hora.split(":");
            int horaNum = Integer.parseInt(partes[0]);
            String minutos = partes.length > 1 ? partes[1] : "00";
            
            if (turno.equals("PM") && horaNum < 12) {
                horaNum += 12;
            } else if (turno.equals("AM") && horaNum == 12) {
                horaNum = 0;
            }
            
            // Convertir de vuelta a formato AM/PM
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, horaNum);
            cal.set(Calendar.MINUTE, Integer.parseInt(minutos));
            
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            return sdf.format(cal.getTime());
            
        } catch (Exception e) {
            e.printStackTrace();
            return hora + " " + turno;
        }
    }
    
    // MÉTODO NUEVO: Convertir hora de AM/PM a formato 24h para la base de datos
    public static String convertirAMPMa24h(String horaAMPM) {
        try {
            SimpleDateFormat formatoAMPM = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat formato24h = new SimpleDateFormat("HH:mm");
            return formato24h.format(formatoAMPM.parse(horaAMPM));
        } catch (Exception e) {
            e.printStackTrace();
            return horaAMPM;
        }
    }
    
    // MÉTODO NUEVO: Convertir hora de 24h a AM/PM
    public static String convertir24hAAmPm(String hora24h) {
        try {
            SimpleDateFormat formato24h = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatoAMPM = new SimpleDateFormat("hh:mm a");
            return formatoAMPM.format(formato24h.parse(hora24h));
        } catch (Exception e) {
            e.printStackTrace();
            return hora24h;
        }
    }
}
