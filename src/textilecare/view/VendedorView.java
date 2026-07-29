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

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnRegistrarVenta;
    private JButton btnReporte;
    private JButton btnSalir;
    private JPanel panelTarjetas;

    private final Color cafe = new Color(181, 137, 103);
    private final Color marronOscuro = new Color(90, 58, 35);
    private final Color fondoBeige = new Color(245, 240, 233);

    public VendedorView(String nombreVendedor) {
        setTitle("TextilCare - Vendedor");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreVendedor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    // Barra lateral con logo, saludo y seccion activa
    private JPanel crearBarraLateral(String nombreVendedor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(marronOscuro);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

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

        JLabel lblSaludo = new JLabel(nombreVendedor, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Vendedor", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(230, 210, 195));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblSeccion = new JLabel("  Productos", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        lblSeccion.setBackground(Color.WHITE);
        lblSeccion.setForeground(marronOscuro);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));
        panel.add(lblSeccion);

        panel.add(Box.createVerticalGlue());

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setBackground(cafe);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(190, 36));
        panel.add(btnSalir);

        return panel;
    }

    // Panel principal: titulo, buscador, botones de accion, y las tarjetas de productos
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        panel.add(crearEncabezado(), BorderLayout.NORTH);
        panel.add(crearPanelTarjetas(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitulo = new JLabel("Productos");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(marronOscuro);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelBusqueda.setBackground(fondoBeige);

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(220, 32));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(cafe);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        panelAcciones.setBackground(fondoBeige);

        btnRegistrarVenta = new JButton("+ Registrar Venta");
        btnRegistrarVenta.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrarVenta.setBackground(cafe);
        btnRegistrarVenta.setForeground(Color.WHITE);
        btnRegistrarVenta.setFocusPainted(false);
        btnRegistrarVenta.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btnRegistrarVenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnReporte = new JButton("Generar Reporte");
        btnReporte.setFont(new Font("Arial", Font.BOLD, 13));
        btnReporte.setBackground(marronOscuro);
        btnReporte.setForeground(Color.WHITE);
        btnReporte.setFocusPainted(false);
        btnReporte.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btnReporte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelAcciones.add(btnReporte);
        panelAcciones.add(btnRegistrarVenta);

        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setBackground(fondoBeige);
        filaSuperior.add(lblTitulo, BorderLayout.WEST);
        filaSuperior.add(panelAcciones, BorderLayout.EAST);

        panel.add(filaSuperior, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearPanelTarjetas() {
        panelTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelTarjetas.setBackground(fondoBeige);

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        return scroll;
    }

    // Vacia y vuelve a pintar las tarjetas de productos
    public void mostrarProductos(List<Producto> productos) {
        panelTarjetas.removeAll();

        for (Producto p : productos) {
            panelTarjetas.add(crearTarjeta(p.getNombre(), p.getTalla(), p.getStock(), p.getPrecio()));
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    private JPanel crearTarjeta(String nombre, String talla, int stock, int precio) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 210, 200)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        tarjeta.setPreferredSize(new Dimension(190, 140));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 15));
        lblNombre.setForeground(marronOscuro);

        JLabel lblTalla = new JLabel("Talla: " + talla);
        lblTalla.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblStock = new JLabel("Stock: " + stock);
        lblStock.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblPrecio = new JLabel("$" + formatearPrecio(precio));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrecio.setForeground(cafe);

        tarjeta.add(lblNombre);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjeta.add(lblTalla);
        tarjeta.add(lblStock);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjeta.add(lblPrecio);

        return tarjeta;
    }

    // Convierte 45000 en "45.000" (formato de precio en pesos)
    private String formatearPrecio(int precio) {
        return String.format("%,d", precio).replace(",", ".");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS que usa el Controlador ──
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnRegistrarVenta() {
        return btnRegistrarVenta;
    }

    public JButton getBtnReporte() {
        return btnReporte;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    // Solo para ver rapido como se ve la ventana
    public static void main(String[] args) {
        VendedorView vista = new VendedorView("Pedro Vendedor");
        vista.setVisible(true);
    }
}