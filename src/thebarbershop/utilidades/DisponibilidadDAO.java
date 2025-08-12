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
    public static boolean agregarHorario(int idPeluquero, String dia, String inicio, String fin) {
        String sql = "INSERT INTO disponibilidad_pel (id_peluquero, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPeluquero);
            ps.setString(2, dia);
            ps.setString(3, inicio);
            ps.setString(4, fin);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar todos los horarios (para actualizar)
    public static boolean eliminarHorarios(int idPeluquero) {
        String sql = "DELETE FROM disponibilidad_pel WHERE id_peluquero = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPeluquero);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
