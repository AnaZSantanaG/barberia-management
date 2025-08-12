/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop.utilidades;
import thebarbershop.Barbero;
import thebarbershop.db.DatabaseConnection;
import java.sql.*;
/**
 *
 * @author Mamore e Ika
 */
public class BarberoDAO {
    // Obtener datos del barbero por email
    public static Barbero obtenerBarberoPorEmail(String email) {
        String sql = "SELECT u.email, p.nombre_completo, p.telefono, p.Ciudad, p.years_experiencia, p.nombreBarberia, u.clave " +
                     "FROM users u " +
                     "JOIN peluqueros p ON u.idusers = p.id_users " +
                     "WHERE u.email = ? AND u.tipo = 'peluquero'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
                if (rs.next()) {
                // Manejar NULL en years_experiencia
                int experiencia = rs.getInt("years_experiencia");
                if (rs.wasNull()) {
                    experiencia = 0; // o 3 si quieres "1-2 años" por defecto
                }

                return new Barbero(
                    rs.getString("nombre_completo"),
                    rs.getString("email"),
                    rs.getString("Ciudad"),
                    rs.getString("telefono"),
                    rs.getString("clave"),
                    experiencia,
                    rs.getString("nombreBarberia")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar datos del barbero
    public static boolean actualizarBarbero(Barbero barbero) {
        String sqlBarbero = "UPDATE peluqueros SET nombre_completo = ?, telefono = ?, Ciudad = ?, years_experiencia = ?, nombreBarberia = ? WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        String sqlUser = "UPDATE users SET clave = ? WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Actualizar en peluqueros
            try (PreparedStatement ps = conn.prepareStatement(sqlBarbero)) {
                ps.setString(1, barbero.getNombre());
                ps.setString(2, barbero.getTelefono());
                ps.setString(3, barbero.getCiudad());
                ps.setInt(4, barbero.getExperiencia());
                ps.setString(5, barbero.getNombreBarberia());
                ps.setString(6, barbero.getEmail());
                int filas = ps.executeUpdate();
                if (filas == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Actualizar contraseña si se ingresó
            if (barbero.getContraseña() != null && !barbero.getContraseña().isEmpty()) {
                try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
                    ps.setString(1, barbero.getContraseña());
                    ps.setString(2, barbero.getEmail());
                    ps.executeUpdate();
                }
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
    
    public static int obtenerIdPeluquero(String email) {
        String sql = "SELECT p.id_Peluquero FROM peluqueros p JOIN users u ON p.id_users = u.idusers WHERE u.email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_Peluquero");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean actualizarNombreBarberia(String email, String nombreBarberia) {
        String sql = "UPDATE peluqueros SET nombreBarberia = ? WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreBarberia);
            ps.setString(2, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarEspecialidades(String email, String especialidades) {
        String sql = "UPDATE peluqueros SET especialidades = ? WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidades);
            ps.setString(2, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }      
}