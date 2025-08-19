/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    // Archivo: thebarbershop.utilidades.CitaDAO.java
package thebarbershop.utilidades;

import thebarbershop.db.DatabaseConnection;
import java.sql.*;
/**
 *
 * @author Mamore e Ika
 */
public class CancelarCita {


    public class CitaDAO {

        public static boolean cancelarCitaEnBD(String emailCliente, Timestamp fechaCita) {
            String sql = "UPDATE citas c " +
                    "JOIN clientes cl ON c.id_cliente = cl.id_clientes " +
                    "JOIN users u ON cl.id_users = u.idusers " +
                    "SET c.estado = 'CANCELADO' " +
                    "WHERE u.email = ? AND c.fecha_cita = ? AND c.estado = 'PENDIENTE'";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, emailCliente);
                ps.setTimestamp(2, fechaCita);
                int filas = ps.executeUpdate();
                return filas > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    
    public static boolean marcarComoRealizada(Timestamp fechaCita, String emailBarbero) {
        String sql = "UPDATE citas c " +
                "JOIN peluqueros p ON c.id_peluquero = p.id_Peluquero " +
                "JOIN users u ON p.id_users = u.idusers " +
                "SET c.estado = 'REALIZADO' " +
                "WHERE u.email = ? AND c.fecha_cita = ? AND c.estado = 'PENDIENTE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailBarbero);
            ps.setTimestamp(2, fechaCita);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
