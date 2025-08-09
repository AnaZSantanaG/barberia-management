// thebarbershop.utilidades.Seguridad
package thebarbershop.utilidades;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Mamore e Ika
 */
public class Seguridad {
    public static String encriptarContraseña(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verificarContraseña(String passwordPlana, String hash) {
        return BCrypt.checkpw(passwordPlana, hash);
    }
}
