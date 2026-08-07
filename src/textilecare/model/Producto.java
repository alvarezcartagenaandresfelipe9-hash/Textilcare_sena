package textilecare.model;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private String talla;
    private int stock;
    private String unidad;
    private int precio;
    private String estado;
    private int idProveedor;
    private String nombreProveedor;

    public Producto() {
    }

    // Trae los nombres de productos que si tienen stock (para el combo de materiales del Tecnico)
    public List<String> listarNombresDisponibles() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM productos WHERE stock > 0";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (Exception ex) {
            System.out.println("Error al listar productos: " + ex.getMessage());
        }

        return nombres;
    }

    // Trae TODOS los nombres de productos (para el combo "Ingresar cantidad" del Inventario)
    public List<String> listarTodosLosNombres() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM productos";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (Exception ex) {
            System.out.println("Error al listar todos los productos: " + ex.getMessage());
        }

        return nombres;
    }

    // Trae todos los productos con el nombre de su proveedor (para la tabla de inventario del Supervisor)
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.categoria, p.stock, p.unidad, p.precio, p.estado, "
                   + "pr.nombre_empresa AS nombre_proveedor "
                   + "FROM productos p "
                   + "LEFT JOIN proveedores pr ON p.id_proveedor = pr.id";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setStock(rs.getInt("stock"));
                p.setUnidad(rs.getString("unidad"));
                p.setPrecio(rs.getInt("precio"));
                p.setEstado(rs.getString("estado"));

                String proveedor = rs.getString("nombre_proveedor");
                if (proveedor == null) {
                    proveedor = "Sin proveedor";
                }
                p.setNombreProveedor(proveedor);

                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar productos: " + ex.getMessage());
        }

        return lista;
    }

    // Trae los productos de la tienda (categoria = Ropa), para el Vendedor
    public List<Producto> listarProductosTienda() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, talla, stock, precio, estado FROM productos WHERE categoria = 'Ropa'";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setTalla(rs.getString("talla"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getInt("precio"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al listar productos de tienda: " + ex.getMessage());
        }

        return lista;
    }

    // Busca productos de tienda cuyo nombre contenga el texto buscado
    public List<Producto> buscarProductosTienda(String texto) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, talla, stock, precio, estado FROM productos "
                   + "WHERE categoria = 'Ropa' AND nombre LIKE ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setTalla(rs.getString("talla"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getInt("precio"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar productos de tienda: " + ex.getMessage());
        }

        return lista;
    }

    // Busca un producto por nombre (usado al descontar stock en una reparacion)
    public Producto buscarPorNombre(String nombre) {
        String sql = "SELECT id, stock FROM productos WHERE nombre = ?";
        Producto p = null;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(nombre);
                p.setStock(rs.getInt("stock"));
            }

        } catch (Exception ex) {
            System.out.println("Error al buscar producto: " + ex.getMessage());
        }

        return p;
    }

    // Descuenta stock y actualiza el estado (usado por Tecnico y por Vendedor al vender)
    public void descontarStock(int idProducto, int cantidadUsada, int stockActual) {
        int nuevoStock = stockActual - cantidadUsada;
        String nuevoEstado = calcularEstado(nuevoStock);

        String sql = "UPDATE productos SET stock = ?, estado = ? WHERE id = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setString(2, nuevoEstado);
            ps.setInt(3, idProducto);
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al descontar stock: " + ex.getMessage());
        }
    }

    // Registra un producto nuevo en el inventario
    public boolean registrar(String nombre, String categoria, int stock, String unidad, int precio, int idProveedor) {
        String estado = calcularEstado(stock);
        String sql = "INSERT INTO productos (nombre, categoria, stock, unidad, precio, estado, id_proveedor) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, categoria);
            ps.setInt(3, stock);
            ps.setString(4, unidad);
            ps.setInt(5, precio);
            ps.setString(6, estado);
            ps.setInt(7, idProveedor);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al registrar producto: " + ex.getMessage());
        }

        return exito;
    }

    // Suma cantidad al stock de un producto existente (reabastecimiento)
    public boolean ingresarCantidad(String nombreProducto, int cantidadAIngresar) {
        Producto actual = buscarPorNombre(nombreProducto);
        if (actual == null) {
            return false;
        }

        int nuevoStock = actual.getStock() + cantidadAIngresar;
        String nuevoEstado = calcularEstado(nuevoStock);

        String sql = "UPDATE productos SET stock = ?, estado = ? WHERE nombre = ?";
        boolean exito = false;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setString(2, nuevoEstado);
            ps.setString(3, nombreProducto);
            ps.executeUpdate();
            exito = true;

        } catch (Exception ex) {
            System.out.println("Error al ingresar cantidad: " + ex.getMessage());
        }

        return exito;
    }

    // Calcula el estado del producto segun su stock
    private String calcularEstado(int stock) {
        if (stock == 0) {
            return "Agotado";
        } else if (stock <= 10) {
            return "Stock bajo";
        } else {
            return "Disponible";
        }
    }

    // ── GETTERS Y SETTERS ──
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
    // Trae solo los productos de tienda que están AGOTADOS (stock = 0)
public List<Producto> listarProductosSinStock() {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT id, nombre, talla, stock, precio, estado FROM productos "
               + "WHERE categoria = 'Ropa' AND stock = 0";

    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setTalla(rs.getString("talla"));
            p.setStock(rs.getInt("stock"));
            p.setPrecio(rs.getInt("precio"));
            p.setEstado(rs.getString("estado"));
            lista.add(p);
        }

    } catch (Exception ex) {
        System.out.println("Error al listar productos sin stock: " + ex.getMessage());
    }

    return lista;
}
}