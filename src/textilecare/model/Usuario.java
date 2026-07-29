package textilecare.model;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int id;
    private String documento;
    private String correo;
    private String telefono;
    private String contrasena;
    private String rol;
    private String nombre;
    private String estado;

    public Usuario() {
    }

    // Consulta la base de datos y devuelve un Usuario si las credenciales son correctas
    public Usuario buscarUsuario(String documento, String contrasena, String rol) {
        String sql = "SELECT id, nombre, estado FROM usuarios WHERE documento = ? AND contrasena = ? AND rol = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, documento);
            ps.setString(2, contrasena);
            ps.setString(3, rol);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setDocumento(documento);
                usuario.setRol(rol);
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEstado(rs.getString("estado"));
                return usuario;
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar usuario: " + ex.getMessage());
        }

        return null;
    }

    // Trae los nombres de todos los usuarios con un rol especifico (para llenar combos)
    public List<String> listarPorRol(String rol) {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM usuarios WHERE rol = ? AND estado = 'Activo'";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, rol);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (Exception ex) {
            System.out.println("Error al listar usuarios por rol: " + ex.getMessage());
        }

        return nombres;
    }

    // Trae TODOS los datos (no solo el nombre) de los usuarios de un rol, para la tabla del Administrador
    public List<Usuario> listarPorRolCompleto(String rol) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, documento, correo, telefono, estado FROM usuarios WHERE rol = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, rol);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getString("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setEstado(rs.getString("estado"));
                u.setRol(rol);
                lista.add(u);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar usuarios: " + ex.getMessage());
        }

        return lista;
    }

    // Busca usuarios de un rol especifico cuyo nombre o documento contenga el texto buscado
    public List<Usuario> buscarPorTextoYRol(String texto, String rol) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, documento, correo, telefono, estado FROM usuarios "
                   + "WHERE rol = ? AND (nombre LIKE ? OR documento LIKE ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setString(2, "%" + texto + "%");
            ps.setString(3, "%" + texto + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getString("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setEstado(rs.getString("estado"));
                u.setRol(rol);
                lista.add(u);
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar usuarios: " + ex.getMessage());
        }

        return lista;
    }

    // Busca el id de un usuario dado su nombre y rol
    public int buscarIdPorNombreYRol(String nombre, String rol) {
        String sql = "SELECT id FROM usuarios WHERE nombre = ? AND rol = ?";
        int idEncontrado = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, rol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idEncontrado = rs.getInt("id");
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar id de usuario: " + ex.getMessage());
        }

        return idEncontrado;
    }

    // Registra un usuario nuevo con el rol indicado. Devuelve false si el documento ya existe o hay un error.
    public boolean registrar(String nombre, String documento, String correo, String telefono, String contrasena, String rol) {
        String sql = "INSERT INTO usuarios (nombre, documento, correo, telefono, contrasena, rol, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, 'Activo')";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, documento);
            ps.setString(3, correo);
            ps.setString(4, telefono);
            ps.setString(5, contrasena);
            ps.setString(6, rol);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al registrar usuario: " + ex.getMessage());
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}