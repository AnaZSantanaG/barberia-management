
package thebarbershop.db;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Mamore e Ika
 */
public class TestConnection {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Intenta obtener la conexión
            conn = DatabaseConnection.getConnection();
            
            // Si llega aquí, la conexión fue exitosa
            if (conn != null && !conn.isClosed()) {
                System.out.println("🎉 ¡Conexión establecida correctamente!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra la conexión
            DatabaseConnection.closeConnection();
        }
    }
}
