// thebarbershop.utilidades.DisponibilidadDAO
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
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

    // Obtener horarios por ID de peluquero
    public static List<Horario> obtenerHorarios(int idPeluquero) {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT dia_semana, hora_inicio, hora_fin FROM disponibilidad_pel WHERE id_peluquero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPeluquero);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horarios.add(new Horario(
                        rs.getString("dia_semana"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fin")
                    ));
                }
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
    
    
    public static String ajustarHoraParaTurno(String hora, String turno) {
    if (turno.equals("PM") && !hora.contains(":")) {
        // Convertir hora simple a formato PM (ej: "2" -> "14:00")
        int horaNum = Integer.parseInt(hora.split(":")[0]);
        if (horaNum < 12) {
            horaNum += 12;
        }
        return horaNum + ":00";
    }
    return hora + (hora.contains(":") ? "" : ":00");
}
}
