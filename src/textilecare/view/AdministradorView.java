package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores, dimensiones e interactividad
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;
import java.awt.Cursor;

// Importa la clase URL para localizar recursos multimedia dentro del proyecto
import java.net.URL;

public class AdministradorView extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JButton btnClientes;
    private JButton btnSupervisores;
    private JButton btnTecnicos;
    private JButton btnVendedores;
    private JButton btnRegistrar;
    private JTextField txtBuscar;
    private JLabel lblTitulo;

    private Color cafe = new Color(181, 137, 103);
    private Color marronOscuro = new Color(90, 58, 35);
    private Color fondo = new Color(238, 232, 224);

    // Constructor: Inicializa la ventana del Administrador, su tamaño, colores y componentes principales
    public AdministradorView(String nombreAdmin) {
        setTitle("TextilCare - Administrador");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);

        add(crearBarraLateral(nombreAdmin), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    // Crea y configura el panel de la barra lateral con el logo, información del usuario y botones de navegación por roles
    private JPanel crearBarraLateral(String nombreAdmin) {
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

        JLabel lblSaludo = new JLabel(nombreAdmin, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Administrador", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(230, 210, 195));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        btnClientes = crearBotonMenu("Clientes", true);
        btnSupervisores = crearBotonMenu("Supervisores", false);
        btnTecnicos = crearBotonMenu("Tecnicos", false);
        btnVendedores = crearBotonMenu("Vendedores", false);

        panel.add(btnClientes);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnSupervisores);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnTecnicos);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnVendedores);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // Método auxiliar para crear y estilizar los botones del menú lateral según su estado activo
    private JButton crearBotonMenu(String texto, boolean activo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(190, 38));

        if (activo) {
            boton.setBackground(Color.WHITE);
            boton.setForeground(marronOscuro);
        } else {
            boton.setBackground(cafe);
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

    // Agrupa y organiza el panel de contenido principal que incluye el encabezado y la tabla de usuarios
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        panel.add(crearEncabezado(), BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    // Crea el panel superior que contiene el título dinámico, el campo de búsqueda y el botón de registro
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        lblTitulo = new JLabel("Clientes Registrados");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(marronOscuro);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setBackground(fondo);

        btnRegistrar = new JButton("+ Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrar.setBackground(cafe);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 13));

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(220, 32));

        panelAcciones.add(lblBuscar);
        panelAcciones.add(txtBuscar);
        panelAcciones.add(btnRegistrar);

        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelAcciones, BorderLayout.EAST);

        return panel;
    }

    // Configura la tabla de datos, sus columnas no editables, estilos visuales y la envuelve en un JScrollPane
    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Estado");

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setSelectionBackground(new Color(230, 210, 190));
        tabla.setGridColor(new Color(225, 218, 210));

        tabla.getTableHeader().setBackground(marronOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        tabla.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRendererAdmin());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 218, 210), 1));

        return scroll;
    }

    // Cambia el titulo y limpia la tabla, para mostrar los usuarios de otro rol
    public void configurarTabla(String titulo) {
        lblTitulo.setText(titulo);
        modeloTabla.setRowCount(0);
    }

    // Agrega una nueva fila con la información del usuario a la tabla
    public void agregarFila(String nombre, String documento, String correo, String telefono, String estado) {
        modeloTabla.addRow(new Object[]{nombre, documento, correo, telefono, estado});
    }

    // ── GETTERS ──
    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnSupervisores() {
        return btnSupervisores;
    }

    public JButton getBtnTecnicos() {
        return btnTecnicos;
    }

    public JButton getBtnVendedores() {
        return btnVendedores;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    // Método principal para visualizar rápidamente la interfaz de la vista de administrador de forma independiente
    public static void main(String[] args) {
        AdministradorView vista = new AdministradorView("Admin");
        vista.setVisible(true);
    }
}

// Clase personalizada para darle formato visual y color a la celda de Estado (Activo / Inactivo) en la tabla
class EstadoCellRendererAdmin extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean foco, int fila, int columna) {

        JLabel etiqueta = new JLabel(valor.toString(), SwingConstants.CENTER);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));

        if (valor.toString().equals("Activo")) {
            etiqueta.setBackground(new Color(200, 240, 220));
            etiqueta.setForeground(new Color(15, 110, 86));
        } else {
            etiqueta.setBackground(new Color(255, 220, 220));
            etiqueta.setForeground(new Color(150, 30, 30));
        }

        return etiqueta;
    }
}