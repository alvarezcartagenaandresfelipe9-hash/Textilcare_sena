package textilecare.view;

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
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;
import java.awt.Cursor;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * Vista del supervisor (SupervisorView) para TextilCare.
 * Proporciona el panel de control principal de la supervisión, incluyendo barra lateral de navegación,
 * tabla de gestión de prendas registradas, barra de búsqueda y opciones para exportar a PDF o registrar nuevas prendas.
 */
public class SupervisorView extends JFrame {

    // Componentes principales de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnRegistrarPrenda;
    private JButton btnExportarPDF;
    private JButton btnPrendas, btnInventario, btnProveedores;

    // Listas auxiliares para asociar filas de la tabla con identificadores lógicos
    private List<Integer> idsPrendas = new ArrayList<>();
    private List<String> tiposPrendas = new ArrayList<>();

    // Paleta de colores institucional compartida en el sistema
    private final Color morado = new Color(155, 89, 182);         // Color de acento
    private final Color moradoOscuro = new Color(88, 24, 130);     // Color principal (barra lateral y encabezados)
    private final Color fondoLila = new Color(243, 237, 250);      // Color de fondo para paneles de contenido

    /**
     * Constructor principal: Inicializa la ventana de la supervisoría configurando
     * el diseño general, la barra lateral de navegación y el panel central de contenido.
     * @param nombreSupervisor Nombre del supervisor autenticado que se mostrará en el saludo.
     */
    public SupervisorView(String nombreSupervisor) {
        setTitle("TextilCare - Supervisor");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreSupervisor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Construye la barra lateral de navegación con el logotipo institucional, el saludo, el rol
     * y los botones de acceso a las secciones del sistema.
     * @param nombreSupervisor Nombre del supervisor.
     * @return Panel lateral configurado.
     */
    private JPanel crearBarraLateral(String nombreSupervisor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(moradoOscuro);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        JLabel lblLogo = crearLogo();
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblLogo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        JLabel lblSaludo = new JLabel(nombreSupervisor, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Supervisor", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        btnPrendas = crearBotonMenu("Prendas", true);
        btnInventario = crearBotonMenu("Inventario", false);
        btnProveedores = crearBotonMenu("Proveedores", false);

        panel.add(btnPrendas);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnInventario);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnProveedores);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Crea un botón estandarizado para el menú lateral.
     * @param texto Texto del botón.
     * @param activo Indica si la sección se encuentra activa actualmente.
     * @return Botón configurado.
     */
    private JButton crearBotonMenu(String texto, boolean activo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(190, 38));

        if (activo) {
            boton.setBackground(Color.WHITE);
            boton.setForeground(moradoOscuro);
        } else {
            boton.setBackground(morado);
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

    /**
     * Carga y escala el logotipo institucional para la barra lateral.
     * @return Etiqueta con el logotipo cargado.
     */
    private JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");

        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }

        return lblLogo;
    }

    /**
     * Construye el panel central que contiene el encabezado y la tabla de datos.
     * @return Panel de contenido configurado.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        panel.add(crearEncabezado(), BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Construye el encabezado del panel central con el título y las acciones (búsqueda, PDF y registro).
     * @return Panel de encabezado configurado.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel lblTitulo = new JLabel("Prendas Registradas");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(moradoOscuro);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setBackground(fondoLila);

        btnExportarPDF = new JButton("Exportar PDF");
        btnExportarPDF.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportarPDF.setBackground(moradoOscuro);
        btnExportarPDF.setForeground(Color.WHITE);
        btnExportarPDF.setFocusPainted(false);
        btnExportarPDF.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnExportarPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnRegistrarPrenda = new JButton("+ Registrar Prenda");
        btnRegistrarPrenda.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrarPrenda.setBackground(morado);
        btnRegistrarPrenda.setForeground(Color.WHITE);
        btnRegistrarPrenda.setFocusPainted(false);
        btnRegistrarPrenda.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnRegistrarPrenda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 13));

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(220, 32));

        panelAcciones.add(lblBuscar);
        panelAcciones.add(txtBuscar);
        panelAcciones.add(btnExportarPDF);
        panelAcciones.add(btnRegistrarPrenda);

        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelAcciones, BorderLayout.EAST);

        return panel;
    }

    /**
     * Construye la tabla principal de prendas con su respectivo renderizador de estados.
     * @return Panel deslizante (JScrollPane) que contiene la tabla.
     */
    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Tecnico", "Cliente", "Fecha", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Evita la edición directa en las celdas
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(new Color(228, 205, 245));
        tabla.setGridColor(new Color(228, 218, 238));
        tabla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tabla.getTableHeader().setBackground(moradoOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        tabla.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRendererSupervisor());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));

        return scroll;
    }

    /**
     * Agrega una nueva fila de datos a la tabla y registra sus identificadores asociados.
     * @param idPrenda Identificador único de la prenda.
     * @param tipo Tipo de prenda.
     * @param tecnico Nombre del técnico asignado.
     * @param cliente Nombre del cliente.
     * @param fecha Fecha de registro.
     * @param estado Estado actual del proceso.
     */
    public void agregarFila(int idPrenda, String tipo, String tecnico, String cliente, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, tecnico, cliente, fecha, estado});
        idsPrendas.add(idPrenda);
        tiposPrendas.add(tipo);
    }

    /**
     * Limpia todos los registros de la tabla y restablece las listas asociadas.
     */
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
        idsPrendas.clear();
        tiposPrendas.clear();
    }

    /**
     * Obtiene el índice de la fila seleccionada en la tabla.
     * @return Índice de la fila seleccionada, o -1 si no hay ninguna selección.
     */
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    /**
     * Obtiene el ID de la prenda asociado a una posición específica.
     * @param indice Índice de la fila.
     * @return ID único de la prenda.
     */
    public int getIdPrenda(int indice) {
        return idsPrendas.get(indice);
    }

    /**
     * Obtiene el tipo de prenda asociado a una posición específica.
     * @param indice Índice de la fila.
     * @return Descripción del tipo de prenda.
     */
    public String getTipoPrenda(int indice) {
        return tiposPrendas.get(indice);
    }

    // ── GETTERS QUE UTILIZA EL CONTROLADOR ──

    public JTable getTabla() {
        return tabla;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnRegistrarPrenda() {
        return btnRegistrarPrenda;
    }

    public JButton getBtnExportarPDF() {
        return btnExportarPDF;
    }

    public JButton getBtnInventario() {
        return btnInventario;
    }

    public JButton getBtnProveedores() {
        return btnProveedores;
    }

    /**
     * Método principal (main): Permite probar y visualizar la ventana de manera independiente.
     */
    public static void main(String[] args) {
        SupervisorView vista = new SupervisorView("Alejandro");
        vista.agregarFila(1, "Camisa", "Daniel Ramirez", "Carlos Perez", "2026-07-01", "En proceso");
        vista.setVisible(true);
    }
}

/**
 * Renderizador personalizado para la columna de estados en la tabla de supervisiones,
 * aplicando colores distintivos según el avance de cada prenda.
 */
class EstadoCellRendererSupervisor extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean foco, int fila, int columna) {

        JLabel etiqueta = new JLabel(valor.toString(), SwingConstants.CENTER);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));

        switch (valor.toString()) {
            case "Reparada":
                etiqueta.setBackground(new Color(200, 240, 220));
                etiqueta.setForeground(new Color(15, 110, 86));
                break;
            case "En proceso":
                etiqueta.setBackground(new Color(255, 235, 190));
                etiqueta.setForeground(new Color(133, 79, 11));
                break;
            default:
                etiqueta.setBackground(new Color(230, 225, 238));
                etiqueta.setForeground(new Color(90, 78, 100));
        }

        return etiqueta;
    }
}