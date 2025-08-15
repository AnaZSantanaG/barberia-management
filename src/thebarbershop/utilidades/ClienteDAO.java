// thebarbershop.dao.ClienteDAO
package thebarbershop.utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import thebarbershop.db.DatabaseConnection;
import thebarbershop.*;
/**
 *
 * @author Mamore e Ika
 */
public class ClienteDAO {
    // Método para obtener los datos del cliente por email (o id)
    public static Cliente obtenerClientePorEmail(String email) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DatabaseConnection.getConnection();
        String sql = "SELECT c.nombre, c.telefono, c.ciudad, c.foto_perfil, u.clave " +
                     "FROM clientes c " +
                     "JOIN users u ON c.id_users = u.idusers " +
                     "WHERE u.email = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        rs = stmt.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            String telefono = rs.getString("telefono");
            String ciudad = rs.getString("ciudad");
            String contraseña = rs.getString("clave");
            byte[] fotoPerfil = rs.getBinaryStream("foto_perfil") != null ? rs.getBytes("foto_perfil") : null;

            return new Cliente(nombre, email, ciudad, telefono, contraseña, fotoPerfil);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeResources(rs, stmt, conn);
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
    
    public static boolean actualizarFotoPerfil(String email, byte[] foto) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseConnection.getConnection();
        String sql = "UPDATE clientes SET foto_perfil = ? " +
                     "WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setBytes(1, foto);
        stmt.setString(2, email);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        closeResources(null, stmt, conn);
    }
}
    
    private static void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
    try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
    try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
    try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}


