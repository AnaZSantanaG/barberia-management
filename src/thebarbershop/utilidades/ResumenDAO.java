// thebarbershop.utilidades.ResumenDAO
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;

/**
 *
 * @author Mamore e Ika
 */
public class ResumenDAO {
    public static class Resumen {
        public int totalCitas;
        public double totalIngresos;

        public Resumen(int citas, double ingresos) {
            this.totalCitas = citas;
            this.totalIngresos = ingresos;
        }
    }

    public static Resumen obtenerResumen(int idPeluquero) {
        String sql = "SELECT COUNT(*) as total_citas, COALESCE(SUM(monto), 0) as total_ingresos " +
                     "FROM citas c JOIN transacciones t ON c.id_cita = t.id_cita " +
                     "WHERE c.id_peluquero = ? AND c.estado = 'REALIZADO'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPeluquero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Resumen(
                        rs.getInt("total_citas"),
                        rs.getDouble("total_ingresos")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Resumen(0, 0);
    }
}
