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

/**
 * Vista del vendedor (VendedorView) para TextilCare.
 * Proporciona la interfaz gráfica principal para el rol de vendedor, integrando la barra lateral
 * de navegación, buscador de productos, panel interactivo de tarjetas de inventario y opciones de gestión.
 */
public class VendedorView extends JFrame {

    // Componentes principales de la interfaz
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnRegistrarVenta;
    private JButton btnReporte;
    private JButton btnSalir;
    private JPanel panelTarjetas;

    // Paleta de colores institucional compartida en el sistema
    private final Color morado = new Color(215, 155, 175);         // Color de acento
    private final Color moradoOscuro = new Color(145, 75, 95);     // Color principal (barra lateral y encabezados)
    private final Color fondoLila = new Color(248, 240, 243);      // Color de fondo para paneles de contenido

    /**
     * Constructor principal: Inicializa la ventana del vendedor configurando
     * el diseño general, la barra lateral y el panel central de contenido.
     * @param nombreVendedor Nombre del vendedor autenticado que se mostrará en el saludo.
     */
    public VendedorView(String nombreVendedor) {
        setTitle("TextilCare - Vendedor");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreVendedor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Construye la barra lateral de navegación con el logotipo institucional, el saludo, el rol,
     * el indicador de sección y el botón de salida.
     * @param nombreVendedor Nombre del vendedor.
     * @return Panel lateral configurado.
     */
    private JPanel crearBarraLateral(String nombreVendedor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(moradoOscuro);
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
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblSeccion = new JLabel("  Productos", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        lblSeccion.setBackground(Color.WHITE);
        lblSeccion.setForeground(moradoOscuro);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));
        panel.add(lblSeccion);

        panel.add(Box.createVerticalGlue());

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

    /**
     * Construye el panel central con el encabezado de acciones y el contenedor de tarjetas de productos.
     * @return Panel de contenido configurado.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        panel.add(crearEncabezado(), BorderLayout.NORTH);
        panel.add(crearPanelTarjetas(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Construye el encabezado del panel central que incluye el título, la barra de búsqueda y los botones de acción.
     * @return Panel de encabezado configurado.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitulo = new JLabel("Productos");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(moradoOscuro);

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

        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setBackground(fondoLila);
        filaSuperior.add(lblTitulo, BorderLayout.WEST);
        filaSuperior.add(panelAcciones, BorderLayout.EAST);

        panel.add(filaSuperior, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Construye el panel deslizante que albergará las tarjetas de productos.
     * @return Panel deslizante (JScrollPane) configurado.
     */
    private JScrollPane crearPanelTarjetas() {
        panelTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelTarjetas.setBackground(fondoLila);

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        return scroll;
    }

    /**
     * Vacía y vuelve a pintar las tarjetas de productos en el panel central a partir de una lista.
     * @param productos Lista de objetos Producto a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        panelTarjetas.removeAll();

        for (Producto p : productos) {
            panelTarjetas.add(crearTarjeta(p.getNombre(), p.getTalla(), p.getStock(), p.getPrecio()));
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    /**
     * Crea un componente visual individual (tarjeta) para representar un producto.
     * @param nombre Nombre del producto.
     * @param talla Talla disponible.
     * @param stock Cantidad disponible en inventario.
     * @param precio Precio unitario.
     * @return Panel con la tarjeta formateada.
     */
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

        JLabel lblPrecio = new JLabel("$" + formatearPrecio(precio));
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

    /**
     * Convierte valores numéricos de precio en un formato con separadores de miles (ej: 45000 a 45.000).
     * @param precio Valor numérico del precio.
     * @return Cadena formateada.
     */
    private String formatearPrecio(int precio) {
        return String.format("%,d", precio).replace(",", ".");
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     * @param mensaje Mensaje a desplegar.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de éxito.
     * @param mensaje Mensaje a desplegar.
     */
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS QUE UTILIZA EL CONTROLADOR ──

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

    /**
     * Método principal (main): Permite probar y visualizar la ventana de manera independiente.
     */
    public static void main(String[] args) {
        VendedorView vista = new VendedorView("Pedro Vendedor");
        vista.setVisible(true);
    }
}