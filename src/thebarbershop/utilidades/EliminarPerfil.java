// thebarbershop.utilidades.EliminarPerfil
package thebarbershop.utilidades;
import thebarbershop.Jframe.IniciarSesion;
import thebarbershop.db.DatabaseConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public static void eliminarPerfilCliente(JFrame frameActual, String emailUsuario) {
        // 1. Confirmación inicial más severa
        int opcion = JOptionPane.showConfirmDialog(
            frameActual,
            "<html><b style='color:red;font-size:14px'>¡ADVERTENCIA!</b><br><br>" +
            "Está a punto de eliminar permanentemente su cuenta.<br>" +
            "<b>Consecuencias:</b><br>" +
            "- Todos sus datos personales serán eliminados<br>" +
            "- Perderá acceso permanente al sistema<br>" +
            "- Sus citas futuras serán canceladas<br><br>" +
            "<b style='color:red'>Esta acción NO se puede deshacer</b></html>",
            "Eliminación Permanente de Cuenta",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        // 2. Verificación de contraseña
        JPasswordField passwordField = new JPasswordField();
        int resultado = JOptionPane.showConfirmDialog(
            frameActual,
            new Object[]{"Confirmación final:\nIngrese su contraseña para continuar:", passwordField},
            "Verificación de Seguridad",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String contraseñaIngresada = new String(passwordField.getPassword());

        // 3. Verificar contraseña
        if (!ClienteDAO.verificarContraseña(emailUsuario, contraseñaIngresada)) {
            JOptionPane.showMessageDialog(
                frameActual,
                "Contraseña incorrecta. Operación cancelada.",
                "Error de Autenticación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 4. Proceso de eliminación (transacción)
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 4.1. Primero desactivar en clientes
            String sqlDesactivar = "UPDATE clientes SET activo = 0 WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";
            try (PreparedStatement psDesactivar = conn.prepareStatement(sqlDesactivar)) {
                psDesactivar.setString(1, emailUsuario);
                psDesactivar.executeUpdate();
            }

            // 4.2. Luego eliminar de users (esto impedirá el login)
            String sqlEliminar = "DELETE FROM users WHERE email = ?";
            try (PreparedStatement psEliminar = conn.prepareStatement(sqlEliminar)) {
                psEliminar.setString(1, emailUsuario);
                int filasEliminadas = psEliminar.executeUpdate();
                
                if (filasEliminadas > 0) {
                    conn.commit(); // Confirmar cambios
                    JOptionPane.showMessageDialog(
                        frameActual,
                        "Tu cuenta ha sido eliminada permanentemente del sistema.",
                        "Cuenta Eliminada",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Cerrar sesión y volver a login
                    frameActual.dispose();
                    new IniciarSesion().setVisible(true);
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(
                        frameActual,
                        "No se encontró el usuario para eliminar.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            JOptionPane.showMessageDialog(
                frameActual,
                "Error crítico al eliminar la cuenta: " + e.getMessage(),
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}