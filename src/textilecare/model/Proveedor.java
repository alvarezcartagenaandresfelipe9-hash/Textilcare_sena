package textilecare.model;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private int id;
    private String nombreEmpresa;
    private String nit;
    private String telefono;
    private String correo;
    private String productos;
    private String estado;
    private int idSupervisor;
    private String nombreSupervisor;

    public Proveedor() {
    }

    // Trae todos los proveedores con el nombre del supervisor que los registro
    public List<Proveedor> listarTodos() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT pr.id, pr.nombre_empresa, pr.nit, pr.telefono, pr.correo, "
                   + "pr.productos, pr.estado, u.nombre AS nombre_supervisor "
                   + "FROM proveedores pr "
                   + "LEFT JOIN usuarios u ON pr.id_supervisor = u.id";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                p.setNit(rs.getString("nit"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setProductos(rs.getString("productos"));
                p.setEstado(rs.getString("estado"));

                String supervisor = rs.getString("nombre_supervisor");
                if (supervisor == null) {
                    supervisor = "Sin asignar";
                }
                p.setNombreSupervisor(supervisor);

                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar proveedores: " + ex.getMessage());
        }

        return lista;
    }

    // Trae los nombres de todos los proveedores activos (para combos, como en Inventario)
    public List<String> listarNombres() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre_empresa FROM proveedores WHERE estado = 'Activo'";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre_empresa"));
            }

        } catch (Exception ex) {
            System.out.println("Error al listar nombres de proveedores: " + ex.getMessage());
        }

        return nombres;
    }

    // Busca el id de un proveedor dado su nombre
    public int buscarIdPorNombre(String nombreEmpresa) {
        String sql = "SELECT id FROM proveedores WHERE nombre_empresa = ?";
        int idEncontrado = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombreEmpresa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idEncontrado = rs.getInt("id");
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar id de proveedor: " + ex.getMessage());
        }

        return idEncontrado;
    }

    // Cuenta el total de proveedores registrados
    public int contarTotal() {
        String sql = "SELECT COUNT(*) AS total FROM proveedores";
        int total = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception ex) {
            System.out.println("Error al contar proveedores: " + ex.getMessage());
        }

        return total;
    }

    // Cuenta los proveedores con estado Activo
    public int contarActivos() {
        String sql = "SELECT COUNT(*) AS total FROM proveedores WHERE estado = 'Activo'";
        int total = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception ex) {
            System.out.println("Error al contar proveedores activos: " + ex.getMessage());
        }

        return total;
    }

    // Cuenta los proveedores registrados el dia de hoy
    public int contarNuevosHoy() {
        String sql = "SELECT COUNT(*) AS total FROM proveedores WHERE fecha_registro = CURDATE()";
        int total = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception ex) {
            System.out.println("Error al contar proveedores nuevos: " + ex.getMessage());
        }

        return total;
    }

    // Cambia el estado de un proveedor (Activo <-> Inactivo)
    public boolean cambiarEstado(String nombreEmpresa, String nuevoEstado) {
        String sql = "UPDATE proveedores SET estado = ? WHERE nombre_empresa = ?";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, nombreEmpresa);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al cambiar estado: " + ex.getMessage());
        }

        return exito;
    }

    // Registra un proveedor nuevo
    public boolean registrar(String nombreEmpresa, String nit, String telefono, String correo, String productos, int idSupervisor) {
        String sql = "INSERT INTO proveedores (nombre_empresa, nit, telefono, correo, productos, estado, id_supervisor) "
                   + "VALUES (?, ?, ?, ?, ?, 'Activo', ?)";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombreEmpresa);
            ps.setString(2, nit);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setString(5, productos);
            ps.setInt(6, idSupervisor);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al registrar proveedor: " + ex.getMessage());
        }

        return exito;
    }

    // ── GETTERS Y SETTERS ──
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
    }
}