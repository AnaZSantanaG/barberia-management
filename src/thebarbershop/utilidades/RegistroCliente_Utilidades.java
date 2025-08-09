// thebarbershop.utilidades.RegistroCliente_Utilidades
package thebarbershop.utilidades;
import thebarbershop.db.DatabaseConnection;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author Mamore e Ika
 */
public class RegistroCliente_Utilidades {
    public static boolean registrar(
            String email,
            String password,
            String nombre,
            String telefono,
            String ciudad
    ) {
        String claveEncriptada = Seguridad.encriptarContraseña(password);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insertar en users
            String sqlUsers = "INSERT INTO users (email, clave, tipo) VALUES (?, ?, 'cliente')";
            pstmt = conn.prepareStatement(sqlUsers, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            pstmt.setString(2, claveEncriptada);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            int idUsersGenerado = rs.next() ? rs.getInt(1) : 0;

            // Insertar en clientes
            String sqlClientes = "INSERT INTO clientes (id_users, nombre, telefono, Ciudad) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlClientes);
            pstmt.setInt(1, idUsersGenerado);
            pstmt.setString(2, nombre);
            pstmt.setString(3, telefono);
            pstmt.setString(4, ciudad);
            pstmt.executeUpdate();

            conn.commit();
            JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.");
            return true;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (Exception ex) {}
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "El email ya está registrado.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        } finally {
            cerrarRecursos(conn, pstmt, rs);
        }
    }

    private static void cerrarRecursos(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }
}
