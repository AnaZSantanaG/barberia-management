// thebarbershop.utilidades.CerrarSesion
package thebarbershop.utilidades;
import thebarbershop.Jframe.IniciarSesion;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 *  Clase utilitaria para manejar el cierre de sesión y salida de la aplicación
 * @author Mamore e Ika
 */
public class CerrarSesion {

    /**
     * Muestra un diálogo con opciones para salir del sistema o cerrar sesión
     * @param frameActual La ventana actual desde donde se llama al método
     */
    public static void mostrarOpcionesSalida(JFrame frameActual) {
        Object[] opciones = {"Salir del sistema", "Cerrar sesión", "Cancelar"};
        
        int opcion = JOptionPane.showOptionDialog(
            frameActual,
            "¿Qué acción deseas realizar?",
            "Opciones de salida",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[2]
        );
        
        switch(opcion) {
            case 0:
                confirmarSalidaSistema(frameActual);
                break;
            case 1:
                cerrarSesionUser(frameActual);
                break;
            case 2:
            default:
                break;
        }
    }
    
    /**
     * Cierra la sesión del usuario y vuelve a la pantalla de inicio de sesión
     * @param frameActual La ventana actual que se cerrará
     */
    public static void cerrarSesionUser(JFrame frameActual) {
        int confirmacion = JOptionPane.showConfirmDialog(
            frameActual,
            "<html>¿Está seguro de que desea cerrar sesión?<br>Se perderán los cambios no guardados.</html>",
            "Cerrar Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            frameActual.dispose();
            new IniciarSesion().setVisible(true);
        }
    }
    
    /**
     * Confirma y ejecuta la salida completa del sistema
     * @param frameActual La ventana actual desde donde se llama al método
     */
    private static void confirmarSalidaSistema(JFrame frameActual) {
        int confirmacion = JOptionPane.showConfirmDialog(
            frameActual, 
            "¿Estás seguro que quieres salir del sistema?", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}