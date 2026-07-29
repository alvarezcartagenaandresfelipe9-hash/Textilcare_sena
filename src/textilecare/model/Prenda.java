package textilecare.model;

// Importa la clase de conexión para gestionar la comunicación con la base de datos
import Conexion.Conexion;

// Importa clases de JDBC para manejar conexiones, consultas preparadas y conjuntos de resultados
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Importa clases para el manejo de listas dinámicas
import java.util.ArrayList;
import java.util.List;

public class Prenda {

    private int id;
    private int idCliente;
    private int idTecnico;
    private String tipo;
    private String descripcion;
    private String estado;
    private String fecha;
    private String fechaInicio;
    private String fechaFin;
    private String nombreCliente;
    private String nombreTecnico;

    public Prenda() {
    }

    // Trae la lista de prendas de un cliente
    public List<Prenda> listarPorCliente(int idCliente) {
        List<Prenda> lista = new ArrayList<>();
        String sql = "SELECT id, tipo, descripcion, estado, fecha FROM prendas WHERE id_cliente = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prenda p = new Prenda();
                p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("tipo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setEstado(rs.getString("estado"));
                p.setFecha(rs.getString("fecha"));
                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar prendas del cliente: " + ex.getMessage());
        }

        return lista;
    }

    // Trae la lista de prendas asignadas a un tecnico
    public List<Prenda> listarPorTecnico(int idTecnico) {
        List<Prenda> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.tipo, p.fecha, p.estado, u.nombre AS nombre_cliente "
                   + "FROM prendas p "
                   + "JOIN usuarios u ON p.id_cliente = u.id "
                   + "WHERE p.id_tecnico = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idTecnico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prenda p = new Prenda();
                p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("tipo"));
                p.setFecha(rs.getString("fecha"));
                p.setEstado(rs.getString("estado"));
                p.setNombreCliente(rs.getString("nombre_cliente"));
                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar prendas del tecnico: " + ex.getMessage());
        }

        return lista;
    }

    // Trae TODAS las prendas del sistema, con nombre de tecnico y cliente (para el Supervisor)
    public List<Prenda> listarTodas() {
        List<Prenda> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.tipo, p.fecha, p.estado, "
                   + "cli.nombre AS nombre_cliente, "
                   + "tec.nombre AS nombre_tecnico "
                   + "FROM prendas p "
                   + "JOIN usuarios cli ON p.id_cliente = cli.id "
                   + "LEFT JOIN usuarios tec ON p.id_tecnico = tec.id";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prenda p = new Prenda();
                p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("tipo"));
                p.setFecha(rs.getString("fecha"));
                p.setEstado(rs.getString("estado"));
                p.setNombreCliente(rs.getString("nombre_cliente"));

                String tecnico = rs.getString("nombre_tecnico");
                if (tecnico == null) {
                    tecnico = "Sin asignar";
                }
                p.setNombreTecnico(tecnico);

                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar todas las prendas: " + ex.getMessage());
        }

        return lista;
    }

    // Trae el detalle completo de UNA prenda (usado por Cliente y Tecnico)
    public Prenda buscarPorId(int idPrenda) {
        String sql = "SELECT tipo, descripcion, estado, fecha_inicio, fecha_fin FROM prendas WHERE id = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPrenda);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Prenda p = new Prenda();
                p.setId(idPrenda);
                p.setTipo(rs.getString("tipo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setEstado(rs.getString("estado"));
                p.setFechaInicio(rs.getString("fecha_inicio"));
                p.setFechaFin(rs.getString("fecha_fin"));
                return p;
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar prenda: " + ex.getMessage());
        }

        return null;
    }

    // Trae el detalle completo de una prenda para la pantalla "Estado de prenda" del Supervisor
    public Prenda buscarDetalleCompleto(int idPrenda) {
        String sql = "SELECT p.tipo, p.fecha, p.descripcion, p.estado, "
                   + "cli.nombre AS nombre_cliente, tec.nombre AS nombre_tecnico "
                   + "FROM prendas p "
                   + "JOIN usuarios cli ON p.id_cliente = cli.id "
                   + "LEFT JOIN usuarios tec ON p.id_tecnico = tec.id "
                   + "WHERE p.id = ?";

        Prenda p = null;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPrenda);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Prenda();
                p.setId(idPrenda);
                p.setTipo(rs.getString("tipo"));
                p.setFecha(rs.getString("fecha"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setEstado(rs.getString("estado"));
                p.setNombreCliente(rs.getString("nombre_cliente"));

                String tecnico = rs.getString("nombre_tecnico");
                if (tecnico == null) {
                    tecnico = "Sin asignar";
                }
                p.setNombreTecnico(tecnico);
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar detalle de prenda: " + ex.getMessage());
        }

        return p;
    }

    // Registra una prenda nueva (el Supervisor la crea y asigna tecnico al mismo tiempo)
    public boolean registrar(String tipo, String descripcion, String fecha, int idCliente, int idTecnico) {
        String sql = "INSERT INTO prendas (tipo, descripcion, fecha, id_cliente, id_tecnico) VALUES (?, ?, ?, ?, ?)";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, tipo);
            ps.setString(2, descripcion);
            ps.setString(3, fecha);
            ps.setInt(4, idCliente);
            ps.setInt(5, idTecnico);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al registrar prenda: " + ex.getMessage());
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }
}