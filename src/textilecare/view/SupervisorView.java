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

public class SupervisorView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnRegistrarPrenda;
    private JButton btnExportarPDF;
    private JButton btnPrendas, btnInventario,btnProveedores;

    private List<Integer> idsPrendas = new ArrayList<>();
    private List<String> tiposPrendas = new ArrayList<>();

    private final Color marron = new Color(180, 130, 80);
    private final Color marronOscuro = new Color(90, 58, 35);
    private final Color fondoBeige = new Color(245, 240, 233);

    public SupervisorView(String nombreSupervisor) {
        setTitle("TextilCare - Supervisor");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearBarraLateral(nombreSupervisor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearBarraLateral(String nombreSupervisor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(marronOscuro);
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
        lblRol.setForeground(new Color(230, 210, 195));
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
        
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnProveedores);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

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
            boton.setBackground(marron);
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

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

    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        panel.add(crearEncabezado(), BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoBeige);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel lblTitulo = new JLabel("Prendas Registradas");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(marronOscuro);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setBackground(fondoBeige);

        btnExportarPDF = new JButton("Exportar PDF");
        btnExportarPDF.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportarPDF.setBackground(marronOscuro);
        btnExportarPDF.setForeground(Color.WHITE);
        btnExportarPDF.setFocusPainted(false);
        btnExportarPDF.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnExportarPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnRegistrarPrenda = new JButton("+ Registrar Prenda");
        btnRegistrarPrenda.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrarPrenda.setBackground(marron);
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

    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Tecnico", "Cliente", "Fecha", "Estado"};

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
        tabla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tabla.getTableHeader().setBackground(marronOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        tabla.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRendererSupervisor());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 218, 210), 1));

        return scroll;
    }

    public void agregarFila(int idPrenda, String tipo, String tecnico, String cliente, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, tecnico, cliente, fecha, estado});
        idsPrendas.add(idPrenda);
        tiposPrendas.add(tipo);
    }

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

 
}

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
                etiqueta.setBackground(new Color(230, 228, 220));
                etiqueta.setForeground(new Color(80, 78, 74));
        }

        return etiqueta;
    }
}