package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;

import java.net.URL;
import java.util.List;

import textilecare.model.Producto;

public class VendedorView extends JFrame {

    // ── Colores originales del sistema ────────────────────────────────────────
    private final Color morado       = new Color(215, 155, 175);
    private final Color moradoOscuro = new Color(145,  75,  95);
    private final Color fondoLila    = new Color(248, 240, 243);

    // ── Componentes que el Controlador necesita ───────────────────────────────
    private JTextField txtBuscar;
    private JButton    btnBuscar;
    private JButton    btnRegistrarVenta;
    private JButton    btnReporte;
    private JButton    btnSalir;

    // Botón nuevo que abre la ventana de productos sin stock
    private JButton    btnSinStock;

    // Panel donde van las tarjetas de productos
    private JPanel     panelTarjetas;

    public VendedorView(String nombreVendedor) {
        setTitle("TextilCare - Vendedor");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barra lateral izquierda con logo y navegación
        add(crearBarraLateral(nombreVendedor), BorderLayout.WEST);

        // Panel central con productos
        add(crearContenido(), BorderLayout.CENTER);
    }

    // ── Barra lateral izquierda ───────────────────────────────────────────────
    private JPanel crearBarraLateral(String nombreVendedor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(moradoOscuro);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        // Logo de la aplicación
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }
        panel.add(lblLogo);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Nombre del vendedor
        JLabel lblSaludo = new JLabel(nombreVendedor, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        // Rol
        JLabel lblRol = new JLabel("Vendedor", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Botón "Productos" resaltado — sección activa
        JLabel lblSeccionProductos = new JLabel("  Productos", SwingConstants.LEFT);
        lblSeccionProductos.setOpaque(true);
        lblSeccionProductos.setBackground(Color.WHITE);
        lblSeccionProductos.setForeground(moradoOscuro);
        lblSeccionProductos.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccionProductos.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccionProductos.setMaximumSize(new Dimension(190, 38));
        lblSeccionProductos.setPreferredSize(new Dimension(190, 38));
        panel.add(lblSeccionProductos);

        panel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Botón "Productos sin stock" — justo debajo de "Productos"
        btnSinStock = new JButton("  Productos sin stock");
        btnSinStock.setFont(new Font("Arial", Font.BOLD, 13));
        btnSinStock.setBackground(new Color(180, 30, 30)); // rojo oscuro
        btnSinStock.setForeground(Color.WHITE);
        btnSinStock.setFocusPainted(false);
        btnSinStock.setBorderPainted(false);
        btnSinStock.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSinStock.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnSinStock.setMaximumSize(new Dimension(190, 38));
        btnSinStock.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(btnSinStock);

        // Espacio para empujar el botón "Salir" hacia abajo
        panel.add(Box.createVerticalGlue());

        // Botón salir
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setBackground(morado);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(190, 36));
        panel.add(btnSalir);

        return panel;
    }

    // ── Panel central ─────────────────────────────────────────────────────────
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        // Encabezado con título, buscador y botones de acción
        panel.add(crearEncabezado(),    BorderLayout.NORTH);

        // Scroll con las tarjetas de productos
        panel.add(crearScrollTarjetas(), BorderLayout.CENTER);

        return panel;
    }

    // ── Encabezado: título, buscador y botones ────────────────────────────────
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Título "Productos"
        JLabel lblTitulo = new JLabel("Productos");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(moradoOscuro);

        // Buscador
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelBusqueda.setBackground(fondoLila);

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(220, 32));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(morado);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        // Botones de acción a la derecha
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        panelAcciones.setBackground(fondoLila);

        btnRegistrarVenta = new JButton("+ Registrar Venta");
        btnRegistrarVenta.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrarVenta.setBackground(morado);
        btnRegistrarVenta.setForeground(Color.WHITE);
        btnRegistrarVenta.setFocusPainted(false);
        btnRegistrarVenta.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btnRegistrarVenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnReporte = new JButton("Generar Reporte");
        btnReporte.setFont(new Font("Arial", Font.BOLD, 13));
        btnReporte.setBackground(moradoOscuro);
        btnReporte.setForeground(Color.WHITE);
        btnReporte.setFocusPainted(false);
        btnReporte.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btnReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelAcciones.add(btnReporte);
        panelAcciones.add(btnRegistrarVenta);

        // Fila superior: título a la izquierda, botones a la derecha
        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setBackground(fondoLila);
        filaSuperior.add(lblTitulo,     BorderLayout.WEST);
        filaSuperior.add(panelAcciones, BorderLayout.EAST);

        panel.add(filaSuperior,  BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.SOUTH);

        return panel;
    }

    // ── Scroll con panel de tarjetas ──────────────────────────────────────────
    private JScrollPane crearScrollTarjetas() {
        panelTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelTarjetas.setBackground(fondoLila);

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        return scroll;
    }

    // ── Métodos que llama el Controlador ─────────────────────────────────────

    // Dibuja las tarjetas de productos en el panel central
    public void mostrarProductos(List<Producto> productos) {
        panelTarjetas.removeAll();

        for (Producto p : productos) {
            panelTarjetas.add(crearTarjeta(
                p.getNombre(), p.getTalla(), p.getStock(), p.getPrecio()
            ));
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    // Crea una tarjeta visual para un producto
    private JPanel crearTarjeta(String nombre, String talla, int stock, int precio) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 212, 238)),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        tarjeta.setPreferredSize(new Dimension(190, 140));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 15));
        lblNombre.setForeground(moradoOscuro);

        JLabel lblTalla = new JLabel("Talla: " + talla);
        lblTalla.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblStock = new JLabel("Stock: " + stock);
        lblStock.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblPrecio = new JLabel("$" + String.format("%,d", precio).replace(",", "."));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrecio.setForeground(morado);

        tarjeta.add(lblNombre);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjeta.add(lblTalla);
        tarjeta.add(lblStock);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjeta.add(lblPrecio);

        return tarjeta;
    }

    // Muestra un mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Muestra un mensaje de éxito
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS para el Controlador ───────────────────────────────────────────
    public JTextField getTxtBuscar()         { return txtBuscar;         }
    public JButton    getBtnBuscar()         { return btnBuscar;         }
    public JButton    getBtnRegistrarVenta() { return btnRegistrarVenta; }
    public JButton    getBtnReporte()        { return btnReporte;        }
    public JButton    getBtnSalir()          { return btnSalir;          }
    public JButton    getBtnSinStock()       { return btnSinStock;       }

    public static void main(String[] args) {
        VendedorView vista = new VendedorView("Pedro Vendedor");
        vista.setVisible(true);
    }
}