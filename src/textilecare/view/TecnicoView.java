package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
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
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * Vista del técnico (TecnicoView) para TextilCare.
 * Proporciona el panel de control exclusivo para el rol de técnico, mostrando una barra lateral
 * institucional y una tabla central con el listado de prendas asignadas para su reparación o revisión.
 */
public class TecnicoView extends JFrame {

    // Componentes principales de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Listas auxiliares para asociar filas de la tabla con identificadores lógicos
    private List<Integer> idsPrendas = new ArrayList<>();
    private List<String> tiposPrendas = new ArrayList<>();

    // Paleta de colores institucional compartida en el sistema
    private final Color morado = new Color(155, 89, 182);         // Color de acento
    private final Color moradoOscuro = new Color(88, 24, 130);     // Color principal (barra lateral y encabezados)
    private final Color fondoLila = new Color(243, 237, 250);      // Color de fondo para paneles de contenido

    /**
     * Constructor principal: Inicializa la ventana del técnico configurando
     * el diseño general, la barra lateral y el panel central de contenido.
     * @param nombreTecnico Nombre del técnico autenticado que se mostrará en el saludo.
     */
    public TecnicoView(String nombreTecnico) {
        setTitle("TextilCare - Tecnico");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreTecnico), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Construye la barra lateral de navegación con el logotipo institucional, el saludo, el rol
     * y el indicador de sección activa.
     * @param nombreTecnico Nombre del técnico.
     * @return Panel lateral configurado.
     */
    private JPanel crearBarraLateral(String nombreTecnico) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(moradoOscuro);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        JLabel lblLogo = crearLogo();
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblLogo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        JLabel lblSaludo = new JLabel(nombreTecnico, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Tecnico", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblSeccion = new JLabel("  Mis Reparaciones", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        lblSeccion.setBackground(Color.WHITE);
        lblSeccion.setForeground(moradoOscuro);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));
        panel.add(lblSeccion);

        panel.add(Box.createVerticalGlue());

        return panel;
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
            Image imagenEscalada = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }

        return lblLogo;
    }

    /**
     * Construye el panel central que contiene el título, el aviso informativo y la tabla de prendas asignadas.
     * @return Panel de contenido configurado.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(fondoLila);
        encabezado.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitulo = new JLabel("Mis Prendas Asignadas");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(moradoOscuro);
        lblTitulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        JLabel lblAviso = new JLabel("  Solo ves las prendas asignadas a ti. Haz clic en una fila para editarla.");
        lblAviso.setOpaque(true);
        lblAviso.setBackground(new Color(222, 235, 250));
        lblAviso.setForeground(new Color(40, 90, 150));
        lblAviso.setFont(new Font("Arial", Font.PLAIN, 13));
        lblAviso.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        lblAviso.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblAviso.setMaximumSize(new Dimension(900, 30));

        encabezado.add(lblTitulo);
        encabezado.add(Box.createRigidArea(new Dimension(0, 10)));
        encabezado.add(lblAviso);

        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Construye la tabla principal de prendas asignadas con su respectivo renderizador de estados.
     * @return Panel deslizante (JScrollPane) que contiene la tabla.
     */
    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Cliente", "Fecha", "Estado"};

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

        tabla.getTableHeader().setBackground(moradoOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        tabla.getColumnModel().getColumn(3).setCellRenderer(new EstadoCellRenderer());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));

        return scroll;
    }

    /**
     * Agrega una nueva fila con los datos de una prenda asignada al técnico y registra sus identificadores.
     * @param idPrenda Identificador único de la prenda.
     * @param tipo Tipo de prenda.
     * @param nombreCliente Nombre del cliente propietario.
     * @param fecha Fecha de asignación o registro.
     * @param estado Estado actual del proceso de reparación.
     */
    public void agregarFila(int idPrenda, String tipo, String nombreCliente, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, nombreCliente, fecha, estado});
        idsPrendas.add(idPrenda);
        tiposPrendas.add(tipo);
    }

    /**
     * Vacía la tabla y las listas de apoyo para volver a cargar datos frescos.
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

    /**
     * Método principal (main): Permite probar y visualizar la ventana de manera independiente.
     */
    public static void main(String[] args) {
        TecnicoView vista = new TecnicoView("Daniel Ramirez");
        vista.agregarFila(1, "Camisa", "Carlos Perez", "2026-07-01", "En proceso");
        vista.setVisible(true);
    }
}

/**
 * Renderizador personalizado para la columna de estado en la tabla del técnico,
 * aplicando colores distintivos según el avance (Reparada, En proceso, etc.).
 */
class EstadoCellRenderer extends DefaultTableCellRenderer {
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