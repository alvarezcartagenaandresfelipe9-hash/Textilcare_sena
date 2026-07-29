package textilecare.model;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FotoPrenda {

    // Busca en la tabla prenda_fotos una foto que sea de ESTA prenda y de ESTE estado.
    // Una foto no se guarda como "imagen", se guarda como una cadena larga de bytes.
    public byte[] buscarFoto(int idPrenda, String estado) {
        String sql = "SELECT foto FROM prenda_fotos WHERE id_prenda = ? AND estado = ? LIMIT 1";
        byte[] foto = null; // si no hay foto, se queda en null

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPrenda);
            ps.setString(2, estado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                foto = rs.getBytes("foto"); // trae los bytes tal cual estan guardados
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar foto: " + ex.getMessage());
        }

        return foto; // devuelve los bytes, o null si no encontro nada
    }
}