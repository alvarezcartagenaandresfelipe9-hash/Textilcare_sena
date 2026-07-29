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

public class TecnicoView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private List<Integer> idsPrendas = new ArrayList<>();
    private List<String> tiposPrendas = new ArrayList<>();

    // Paleta de colores (misma del resto del proyecto)
    private final Color marron = new Color(180, 130, 80);
    private final Color marronOscuro = new Color(90, 58, 35);
    private final Color fondoBeige = new Color(245, 240, 233);

    public TecnicoView(String nombreTecnico) {
        setTitle("TextilCare - Tecnico");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreTecnico), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    // Barra lateral con logo, nombre y menu
    private JPanel crearBarraLateral(String nombreTecnico) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(marronOscuro);
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
        lblRol.setForeground(new Color(230, 210, 195));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblSeccion = new JLabel("  Mis Reparaciones", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        lblSeccion.setBackground(Color.WHITE);
        lblSeccion.setForeground(marronOscuro);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));
        panel.add(lblSeccion);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // Carga el logo desde el paquete de recursos (mismo que en Login y Cliente)
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

    // Panel principal con titulo, aviso y tabla
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(fondoBeige);
        encabezado.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitulo = new JLabel("Mis Prendas Asignadas");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(marronOscuro);
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

    // Crea la tabla de prendas asignadas
    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Cliente", "Fecha", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(new Color(230, 210, 190));
        tabla.setGridColor(new Color(225, 218, 210));

        tabla.getTableHeader().setBackground(marronOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        tabla.getColumnModel().getColumn(3).setCellRenderer(new EstadoCellRenderer());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 218, 210), 1));

        return scroll;
    }

    // Agrega una fila con los datos de una prenda asignada al tecnico
    public void agregarFila(int idPrenda, String tipo, String nombreCliente, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, nombreCliente, fecha, estado});
        idsPrendas.add(idPrenda);
        tiposPrendas.add(tipo);
    }

    // Vacia la tabla y las listas de apoyo, para volver a cargar datos frescos
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
        idsPrendas.clear();
        tiposPrendas.clear();
    }

    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    public int getIdPrenda(int indice) {
        return idsPrendas.get(indice);
    }

    public String getTipoPrenda(int indice) {
        return tiposPrendas.get(indice);
    }

    public JTable getTabla() {
        return tabla;
    }

   
}

// Le da color de fondo a la celda de Estado segun su valor (Pendiente/En proceso/Reparada)
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
                etiqueta.setBackground(new Color(230, 228, 220));
                etiqueta.setForeground(new Color(80, 78, 74));
        }

        return etiqueta;
    }
}