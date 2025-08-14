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
        public String descripcion;
        public double precio;
        public int tiempoEstimado;

        public Estilo(int id, String nombre, String descripcion, double precio, int tiempoEstimado) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.tiempoEstimado = tiempoEstimado;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

        public static List<Estilo> obtenerEstilos() {
        List<Estilo> estilos = new ArrayList<>();
        String sql = "SELECT id_estilos, nombre_estilo, decripcion, precio, tiempo_estimado FROM estilos_corte";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                estilos.add(new Estilo(
                    rs.getInt("id_estilos"),
                    rs.getString("nombre_estilo"),
                    rs.getString("decripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("tiempo_estimado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estilos;
    }

        public static boolean guardarNuevoEstiloCompleto(String nombre, String descripcion, double precio, int tiempo) {
        String sql = "INSERT INTO estilos_corte (nombre_estilo, decripcion, precio, tiempo_estimado) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, tiempo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
