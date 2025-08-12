// thebarbershop.utilidades.EstiloCorteDAO
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mamore e Ika
 */
public class EstiloCorteDAO {
    public static class Estilo {
        public int id;
        public String nombre;

        public Estilo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return id + " - " + nombre;
        }
    }

    public static List<Estilo> obtenerEstilos() {
        List<Estilo> estilos = new ArrayList<>();
        String sql = "SELECT id_estilos, nombre_estilo FROM estilos_corte";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                estilos.add(new Estilo(
                    rs.getInt("id_estilos"),
                    rs.getString("nombre_estilo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estilos;
    }
    
    public static boolean guardarNuevoEstilo(String nombreEstilo) {
    String sql = "INSERT INTO estilos_corte (nombre_estilo) VALUES (?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, nombreEstilo);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
