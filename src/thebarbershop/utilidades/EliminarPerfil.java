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
     * Desactiva el perfil del cliente y cierra la sesión.
     * @param frameActual La ventana actual (this)
     * @param emailUsuario El correo del usuario a eliminar
     */
        public static void eliminarPerfilCliente(JFrame frameActual, String emailUsuario) {
            // 1. Pedir confirmación inicial
        int opcion = JOptionPane.showConfirmDialog(
            frameActual,
            "<html><b>¿Está seguro de que desea eliminar su perfil?</b><br><br>" +
            "Esta acción desactivará su cuenta permanentemente.<br>" +
            "No podrá iniciar sesión nuevamente.<br><br>" +
            "<font color='red'>Esta acción no se puede deshacer.</font></html>",
            "Eliminar Perfil",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        // 2. Pedir contraseña actual
        JPasswordField passwordField = new JPasswordField();
        Object[] mensaje = {
            "Para confirmar, ingrese su contraseña actual:", passwordField
        };

        int resultado = JOptionPane.showConfirmDialog(
            frameActual,
            mensaje,
            "Verificación de Seguridad",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) {
            return; // Canceló
        }

        String contraseñaIngresada = new String(passwordField.getPassword());

        // 3. Verificar contraseña
        if (!ClienteDAO.verificarContraseña(emailUsuario, contraseñaIngresada)) {
            JOptionPane.showMessageDialog(
                frameActual,
                "❌ Contraseña incorrecta. No se puede eliminar el perfil.",
                "Error de Autenticación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 4. Contraseña correcta → desactivar cuenta
        String sql = "UPDATE clientes SET activo = 0 WHERE id_users = (SELECT idusers FROM users WHERE email = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            ps.setString(1, emailUsuario);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(
                    frameActual,
                    "❌ Tu cuenta ha sido eliminada. Hasta luego.",
                    "Cuenta Eliminada",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // 5. Cerrar sesión
                frameActual.dispose();
                new IniciarSesion().setVisible(true);
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(
                    frameActual,
                    "No se encontró tu perfil para eliminar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                frameActual,
                "Error al eliminar el perfil: " + e.getMessage(),
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}