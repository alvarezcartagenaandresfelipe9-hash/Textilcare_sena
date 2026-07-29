package textilecare.model;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDate;

public class Reparacion {

    // Guarda la reparacion y devuelve el id generado (lo necesitamos para los materiales)
    public int guardar(int idPrenda, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        String sql = "INSERT INTO reparaciones (id_prenda, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idPrenda);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));
            ps.setString(4, estado);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception ex) {
            System.out.println("Error al guardar reparacion: " + ex.getMessage());
        }

        return 0;
    }

    // Guarda una foto asociada a la prenda y al estado en que se tomo
    public void guardarFoto(int idPrenda, String estado, byte[] foto) {
        String sql = "INSERT INTO prenda_fotos (id_prenda, estado, foto) VALUES (?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPrenda);
            ps.setString(2, estado);
            ps.setBytes(3, foto);
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al guardar foto: " + ex.getMessage());
        }
    }

    // Guarda un material usado en una reparacion
    public void guardarMaterialUsado(int idReparacion, int idProducto, int cantidad) {
        String sql = "INSERT INTO materiales_usados (id_reparacion, id_producto, cantidad) VALUES (?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idReparacion);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al guardar material usado: " + ex.getMessage());
        }
    }

    // Actualiza el estado general de la prenda (se ve reflejado en la tabla del tecnico y del cliente)
    public void actualizarEstadoPrenda(int idPrenda, String estado) {
        String sql = "UPDATE prendas SET estado = ? WHERE id = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idPrenda);
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al actualizar estado de prenda: " + ex.getMessage());
        }
    }
}