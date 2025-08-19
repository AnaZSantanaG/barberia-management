/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author Mamore e Ika
 */
public class MoraDAO {
    public static void aplicarMoraPorNoShow(int idCita) {
    Connection conn = null;
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);

        // Verificar si ya tiene mora
        String sqlCheck = "SELECT mora_aplicada FROM citas WHERE id_cita = ?";
        boolean yaTieneMora = false;
        try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) yaTieneMora = rs.getBoolean("mora_aplicada");
        }
        if (yaTieneMora) return;

        // Obtener precio del estilo
        String sqlPrecio = "SELECT est.precio FROM citas c " +
                "JOIN estilos_corte est ON c.id_estilo = est.id_estilos " +
                "WHERE c.id_cita = ?";
        double precio = 0;
        try (PreparedStatement ps = conn.prepareStatement(sqlPrecio)) {
            ps.setInt(1, idCita);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) precio = rs.getDouble("precio");
        }

        double mora = precio * 0.05;

        // Insertar mora
        String sqlMora = "INSERT INTO moras (id_cita, monto) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlMora)) {
            ps.setInt(1, idCita);
            ps.setDouble(2, mora);
            ps.executeUpdate();
        }

        // Marcar cita como con mora
        String sqlUpdate = "UPDATE citas SET mora_aplicada = TRUE WHERE id_cita = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setInt(1, idCita);
            ps.executeUpdate();
        }

        conn.commit();
        System.out.println("Mora de $" + mora + " aplicada a la cita " + idCita);

    } catch (SQLException e) {
        try { conn.rollback(); } catch (Exception ex) {}
        e.printStackTrace();
    } finally {
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }
}
}
