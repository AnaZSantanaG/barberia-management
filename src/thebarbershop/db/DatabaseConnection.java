
package thebarbershop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mamore e Ika
 */
public class DatabaseConnection {
    // Propiedades de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/barberia_system";
    private static final String user = "root";
    private static final String pass = "Admin";
    
    // Variable estática para almacenar la conexión
    private static Connection connection;
    
    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer la conexión
                connection = DriverManager.getConnection(URL, user, pass);
            } catch (ClassNotFoundException | SQLException e) {
                throw new SQLException("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
