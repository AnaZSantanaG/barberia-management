// thebarbershop.utilidades.EliminarPerfil
package thebarbershop.utilidades;
import thebarbershop.db.DatabaseConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
/**
 *
 * @author Mamore e Ika
 */
public class EliminarPerfil {
    /**
     * Elimina completamente el perfil del cliente (desactiva en clientes y elimina de users)
     * @param frameActual La ventana actual (this)
     * @param emailUsuario El correo del usuario a eliminar
     */
    public static boolean eliminarPerfilCliente(JFrame parent, String emailUsuario) {
    Connection conn = null;
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        // Verificar si tiene citas pendientes
        String sqlCheck = "SELECT COUNT(*) FROM citas WHERE id_cliente = (SELECT id_clientes FROM clientes c JOIN users u ON c.id_users = u.idusers WHERE u.email = ?) AND estado = 'PENDIENTE'";
        try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
            ps.setString(1, emailUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(parent, "No puede eliminar su cuenta: tiene citas pendientes.");
                return false;
            }
        }

        // 1. Eliminar de clientes
        String sqlCliente = "DELETE FROM clientes WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
            ps.setString(1, emailUsuario);
            ps.executeUpdate();
        }

        // 2. Eliminar de users
        String sqlUser = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setString(1, emailUsuario);
            int filas = ps.executeUpdate();
            if (filas == 0) throw new SQLException("Usuario no encontrado");
        }

        conn.commit();
        JOptionPane.showMessageDialog(parent, "Cuenta eliminada con éxito.");
        return true;

    } catch (SQLException e) {
        try { conn.rollback(); } catch (Exception ex) {}
        JOptionPane.showMessageDialog(parent, "Error al eliminar cuenta: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }
}
}