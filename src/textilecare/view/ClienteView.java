package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores, dimensiones e interactividad
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;

// Importa la clase URL para localizar recursos multimedia dentro del proyecto
import java.net.URL;

// Importa colecciones de Java para el almacenamiento y control de identificadores
import java.util.ArrayList;
import java.util.List;

public class ClienteView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnDetalle;
    private JButton btnSalir;

    // Lista paralela que almacena el identificador único de cada prenda registrada en la tabla
    private List<Integer> idsPrendas = new ArrayList<>();

    // Paleta de colores institucional para la interfaz del cliente
    private final Color marron = new Color(180, 130, 80);
    private final Color marronOscuro = new Color(90, 58, 35);
    private final Color fondoBeige = new Color(245, 240, 233);
    private final Color grisTexto = new Color(110, 110, 110);

    // Constructor: Inicializa la ventana principal del Cliente, sus dimensiones, restricciones y componentes de diseño
    public ClienteView(String nombreCliente) {
        setTitle("TextilCare - Cliente");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreCliente), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

  
    // Crea y configura el panel lateral izquierdo con la identidad del cliente y las opciones de navegación
    private JPanel crearBarraLateral(String nombreCliente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(marronOscuro);
        panel.setPreferredSize(new Dimension(230, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        // 1) Logo arriba de todo, simulando una tarjeta de perfil
        JLabel lblLogo = crearLogo();
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblLogo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // 2) Nombre y rol del cliente, ubicados justo debajo del logo
        JLabel lblSaludo = new JLabel(nombreCliente, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Cliente", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(230, 210, 195));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // 3) Separador visual para marcar el cambio de sección
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(140, 110, 85));
        separador.setMaximumSize(new Dimension(190, 1));
        panel.add(separador);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 4) Opción de menú activa que indica la sección actual
        JLabel lblSeccion = new JLabel("  Mis Prendas", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        lblSeccion.setBackground(Color.WHITE);
        lblSeccion.setForeground(marronOscuro);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));

        panel.add(lblSeccion);

        // Espaciador flexible para empujar el pie de página hacia la parte inferior
        panel.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("TextilCare", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(200, 180, 165));
        lblFooter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblFooter);

        return panel;
    }

    // Carga la imagen del logo institucional, ajustando sus proporciones de manera uniforme para su visualización
    private JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");

        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }

        return lblLogo;
    }

    
    // Agrupa y organiza el panel principal derecho que contiene el encabezado, la tabla y los botones de acción
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        panel.add(crearEncabezadoContenido(), BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    // Crea el panel del encabezado que incluye el título principal y un subtítulo descriptivo
    private JPanel crearEncabezadoContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel lblTitulo = new JLabel("Mis Prendas");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 26));
        lblTitulo.setForeground(marronOscuro);
        lblTitulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Consulta el estado de tus prendas en reparacion");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setForeground(grisTexto);
        lblSubtitulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        panel.add(lblTitulo);
        panel.add(lblSubtitulo);

        return panel;
    }

    // Configura la tabla interactiva de prendas, sus columnas no editables, estilos visuales y la envuelve en un JScrollPane
    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Descripcion", "Fecha", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(34);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(new Color(230, 210, 190));
        tabla.setSelectionForeground(marronOscuro);
        tabla.setGridColor(new Color(225, 218, 210));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(0, 1));

        tabla.getTableHeader().setBackground(marronOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 218, 210), 1));

        return scroll;
    }

    // Crea el panel inferior que contiene los botones de interacción ("Ver Detalle" y "Salir")
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        btnDetalle = new JButton("Ver Detalle");
        btnDetalle.setFont(new Font("Arial", Font.BOLD, 13));
        btnDetalle.setBackground(marron);
        btnDetalle.setForeground(Color.WHITE);
        btnDetalle.setFocusPainted(false);
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        btnDetalle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setBackground(marronOscuro);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnDetalle);
        panel.add(btnSalir);

        return panel;
    }

    // Agrega una nueva fila con los datos de una prenda a la tabla y registra su identificador asociado
    public void agregarFila(int idPrenda, String tipo, String descripcion, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, descripcion, fecha, estado});
        idsPrendas.add(idPrenda);
    }

    // Retorna el índice de la fila seleccionada actualmente por el usuario en la tabla
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    // Retorna el identificador único de la prenda correspondiente al índice de la fila especificada
    public int getIdPrenda(int indice) {
        return idsPrendas.get(indice);
    }

    // ── GETTERS que usa el Controlador ──
    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    // Método principal para visualizar rápidamente la interfaz de la vista de cliente de forma independiente
    public static void main(String[] args) {
        ClienteView vista = new ClienteView("Nombre Cliente");
        vista.setVisible(true);
    }
}
