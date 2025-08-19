// thebarbershop.utilidades.EliminarPerfil
package thebarbershop.utilidades;
import thebarbershop.db.DatabaseConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import thebarbershop.Jframe.IniciarSesion;
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

            // 3. Eliminar de users
           String sqlUser = "DELETE FROM users WHERE email = ?";
           try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
               ps.setString(1, emailUsuario);
               int filas = ps.executeUpdate();
               if (filas == 0) throw new SQLException("Usuario no encontrado");
           }

           conn.commit();

           // 4. Mostrar mensaje y cerrar sesión
           JOptionPane.showMessageDialog(parent, "Tu cuenta ha sido eliminada con éxito. Gracias por usar TheBarberShop.");
           parent.dispose(); // Cierra la ventana actual
           new IniciarSesion().setVisible(true); // Abre la pantalla de inicio de sesión

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
    
    /**
    * Elimina completamente el perfil del barbero (desactiva en peluqueros y elimina de users)
    * @param parent La ventana actual (this)
    * @param emailUsuario El correo del barbero a eliminar
    * @return true si se eliminó con éxito, false si hubo error
    */
   public static boolean eliminarPerfilBarbero(JFrame parent, String emailUsuario) {
       Connection conn = null;
       try {
           conn = DatabaseConnection.getConnection();
           conn.setAutoCommit(false);

           // 1. Verificar si tiene citas pendientes
           String sqlCheckCitas = "SELECT COUNT(*) FROM citas c " +
                   "JOIN peluqueros p ON c.id_peluquero = p.id_Peluquero " +
                   "JOIN users u ON p.id_users = u.idusers " +
                   "WHERE u.email = ? AND c.estado = 'PENDIENTE'";
           try (PreparedStatement ps = conn.prepareStatement(sqlCheckCitas)) {
               ps.setString(1, emailUsuario);
               ResultSet rs = ps.executeQuery();
               if (rs.next() && rs.getInt(1) > 0) {
                   JOptionPane.showMessageDialog(parent, "No puede eliminar su cuenta: tiene citas pendientes.");
                   return false;
               }
           }

           // 2. Eliminar de peluqueros
           String sqlPeluquero = "DELETE FROM peluqueros WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
           try (PreparedStatement ps = conn.prepareStatement(sqlPeluquero)) {
               ps.setString(1, emailUsuario);
               ps.executeUpdate();
           }

           // 3. Eliminar de users
           String sqlUser = "DELETE FROM users WHERE email = ?";
           try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
               ps.setString(1, emailUsuario);
               int filas = ps.executeUpdate();
               if (filas == 0) throw new SQLException("Usuario no encontrado");
           }

           conn.commit();

           // 4. Mostrar mensaje y cerrar sesión
           JOptionPane.showMessageDialog(parent, "Tu cuenta ha sido eliminada con éxito. Gracias por usar TheBarberShop.");
           parent.dispose(); // Cierra la ventana actual
           new IniciarSesion().setVisible(true); // Abre la pantalla de inicio de sesión

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