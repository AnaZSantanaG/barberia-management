// thebarbershop.utilidades.CerrarSesion
package thebarbershop.utilidades;
import thebarbershop.Jframe.IniciarSesion;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Mamore e Ika
 */
public class CerrarSesion {

    /**
     * Cierra la sesión del usuario.
     * @param frameActual La ventana actual (this) que se cerrará
     */
    public static void cerrarSesion(JFrame frameActual) {
        int opcion = JOptionPane.showConfirmDialog(
            frameActual,
            "<html>¿Está seguro de que desea cerrar sesión?<br>Se perderán los cambios no guardados.</html>",
            "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            frameActual.dispose(); // Cierra la ventana actual
            new IniciarSesion().setVisible(true); // Abre la pantalla de inicio de sesión
        }
    }
}