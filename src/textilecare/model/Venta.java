package textilecare.model;

// Importa la clase de conexión para gestionar la comunicación con la base de datos
import Conexion.Conexion;

// Importa clases de JDBC para manejar conexiones, consultas preparadas, conjuntos de resultados y la recuperación de claves generadas
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Venta {

    private int id;
    private int idVendedor;
    private String fecha;
    private String metodoPago;
    private int total;

    public Venta() {
    }

    // Registra la cabecera de la venta y devuelve el id generado
    public int registrar(int idVendedor, String metodoPago, int total) {
        String sql = "INSERT INTO ventas (id_vendedor, fecha, metodo_pago, total) VALUES (?, CURDATE(), ?, ?)";
        int idGenerado = 0;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idVendedor);
            ps.setString(2, metodoPago);
            ps.setInt(3, total);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (Exception ex) {
            System.out.println("Error al registrar venta: " + ex.getMessage());
        }

        return idGenerado;
    }

    // Registra un producto vendido dentro de una venta
    public void registrarDetalle(int idVenta, int idProducto, int cantidad, int precioUnitario, int subtotal) {
        String sql = "INSERT INTO venta_detalle (id_venta, id_producto, cantidad, precio_unitario, subtotal) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setInt(4, precioUnitario);
            ps.setInt(5, subtotal);
            ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error al registrar detalle de venta: " + ex.getMessage());
        }
    }

    // ── GETTERS Y SETTERS ──
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}