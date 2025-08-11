// thebarbershop.utilidades.ConsultaNombreBarberia
package thebarbershop.utilidades;
import thebarbershop.db.DatabaseConnection;
import java.sql.*;

/**
 *
 * @author Mamore e Ika
 */
public class ConsultaNombreBarberia {
     /**
     * Obtiene el nombre de la barbería a la que pertenece el barbero usando su email.
     * @param emailBarbero El correo del barbero
     * @return El nombre de la barbería, o cadena vacía si no se encuentra
     */
    public static String obtenerNombreBarberia(String emailBarbero) {
    String nombreBarberia = "";
    String query = "SELECT p.nombreBarberia " +
                       "FROM peluqueros p " +
                       "JOIN users u ON p.id_users = u.idusers " +
                       "WHERE u.email = ?"; //Filtra por email
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, emailBarbero);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nombreBarberia = rs.getString("nombreBarberia"); // Nombre correcto
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al consultar el nombre de la barbería: " + e.getMessage());
        }

        return nombreBarberia;
    }
}
