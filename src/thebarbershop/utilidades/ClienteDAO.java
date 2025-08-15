// thebarbershop.dao.ClienteDAO
package thebarbershop.utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import thebarbershop.db.DatabaseConnection;
import thebarbershop.*;
/**
 *
 * @author Mamore e Ika
 */
public class ClienteDAO {
    // Método para obtener los datos del cliente por email (o id)
    public static Cliente obtenerClientePorEmail(String email) {
        String sql = "SELECT u.email, c.nombre, c.telefono, c.Ciudad " +
                 "FROM users u " +
                 "JOIN clientes c ON u.idusers = c.id_users " +
                 "WHERE u.email = ? AND u.tipo = 'cliente' AND c.activo = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("Ciudad"),
                    rs.getString("telefono"),
                    "",// contraseña vacía (no se devuelve)
                    rs.getBytes("ruta_foto_perfil")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar los datos del cliente
    public static boolean actualizarCliente(Cliente cliente) {
        String sqlCliente = "UPDATE clientes SET nombre = ?, telefono = ?, Ciudad = ? WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        String sqlUser = "UPDATE users SET clave = ? WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Transacción

            // Actualizar en tabla `clientes`
            try (PreparedStatement psCliente = conn.prepareStatement(sqlCliente)) {
                psCliente.setString(1, cliente.getNombre());
                psCliente.setString(2, cliente.getTelefono());
                psCliente.setString(3, cliente.getCiudad());
                psCliente.setString(4, cliente.getEmail());

                int filasCliente = psCliente.executeUpdate();
                if (filasCliente == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Actualizar contraseña solo si no está vacía
            if (cliente.getContraseña() != null && !cliente.getContraseña().isEmpty()) {
            String contrasenaEncriptada = Seguridad.encriptarContraseña(cliente.getContraseña());
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, contrasenaEncriptada);
                psUser.setString(2, cliente.getEmail());
                psUser.executeUpdate();
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
    
    public static boolean verificarContraseña(String email, String passwordPlana) {
    String sql = "SELECT clave FROM users WHERE email = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            String hash = rs.getString("clave");
            return Seguridad.verificarContraseña(passwordPlana, hash); // BCrypt.checkpw
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    public static boolean actualizarFotoPerfil(String email, byte[] fotoPerfil) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "UPDATE clientes SET foto_perfil = ? WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setBytes(1, fotoPerfil); // Establecer la imagen como bytes
            stmt.setString(2, email); // Establecer el correo electrónico

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


