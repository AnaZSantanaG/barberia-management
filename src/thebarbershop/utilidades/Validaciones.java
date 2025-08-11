// thebarbershop.utilidades.Validaciones y limpieza
package thebarbershop.utilidades;
import javax.swing.*;
/**
 *
 * @author Mamore e Ika
 */
public class Validaciones {
    public static boolean validarNombre(JTextField campo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            return false;
        }
        return true;
    }

    public static boolean validarEmail(JTextField campo) {
        String email = campo.getText().trim();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Ingrese un email válido.");
            return false;
        }
        return true;
    }

    public static boolean validarCiudad(JComboBox<String> combo) {
        String ciudad = (String) combo.getSelectedItem();
        if (ciudad == null || ciudad.equals("Seleccione....")) {
            JOptionPane.showMessageDialog(null, "Seleccione una ciudad.");
            return false;
        }
        return true;
    }
    
    public static boolean validarYearExperiencia(JComboBox<String> combo) {
        String experiencia = (String) combo.getSelectedItem();
        if (experiencia == null || experiencia.equals("Seleccione....")) {
            JOptionPane.showMessageDialog(null, "Seleccione su experiencia.");
            return false;
        }
        return true;
    }
    
    public static boolean validarNombreBarberia(JTextField campo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de la barbería es obligatorio.");
            return false;
        }
        return true;
    }

    public static boolean validarTelefono(String telefonoFormateado) {
        String telefono = telefonoFormateado.replaceAll("[^0-9]", "");
        if (telefono.length() < 10) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener al menos 10 dígitos.");
            return false;
        }
        return true;
    }

    public static boolean validarContraseña(JPasswordField campo) {
        String password = new String(campo.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La contraseña es obligatoria.");
            return false;
        }
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres.");
            return false;
        }
        return true;
    }

    public static boolean validarTipoUsuario(boolean clienteSeleccionado, boolean barberoSeleccionado) {
        if (!clienteSeleccionado && !barberoSeleccionado) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de usuario.");
            return false;
        }
        return true;
    }
    
    /**
 * Limpia cualquier combinación de componentes de formulario.
 * Soporta: JTextField, JPasswordField, JComboBox, JRadioButton
 * 
 * @param componentes Componentes a limpiar (pueden ser JTextField, JComboBox, etc.)
 */
public static void limpiarCampos(Object... componentes) {
    for (Object comp : componentes) {
        if (comp == null) continue;

        if (comp instanceof JTextField) {
            ((JTextField) comp).setText("");
        } 
        else if (comp instanceof JPasswordField) {
            ((JPasswordField) comp).setText("");
        } 
        else if (comp instanceof JComboBox) {
            // Reinicia al primer elemento (ej: "Seleccione...")
            ((JComboBox<?>) comp).setSelectedIndex(0);
        } 
        else if (comp instanceof JRadioButton) {
            // Deselecciona el radio button
            ((JRadioButton) comp).setSelected(false);
        }
        // Puedes agregar más tipos si necesitas (JTextArea, etc.)
    }
}
}
