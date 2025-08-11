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
     * Obtiene el nombre de la barberia a la que pertenece el barbero usando su email.
     * @param emailBarbero El correo del barbero
     * @return El nombre de la barberia, o cadena vacía si no se encuentra
     */
    public static String obtenerNombreBarberia(String emailBarbero) {
    String nombreBarberia = "";
    String query = "SELECT p.nombreBarberia " +
                       "FROM peluqueros p " +
                       "JOIN users u ON p.id_users = u.idusers " +
                       "WHERE u.idusers = p.id_users";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, emailBarbero);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nombreBarberia = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreBarberia;
    }
}
