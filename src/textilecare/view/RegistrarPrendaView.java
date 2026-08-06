package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.BorderLayout;

import java.net.URL;
import java.util.List;

public class RegistrarPrendaView extends JFrame {

    // ── Colores ───────────────────────────────────────────────────────────────
    private final Color cafe        = new Color(181, 137, 103);
    private final Color marronOscuro = new Color(90,  58,  35);
    private final Color verdeStock  = new Color(34,  139,  34);
    private final Color rojoSinStock = new Color(180,  30,  30);

    // ── Componentes del formulario de registro ────────────────────────────────
    private JComboBox<String>    cmbTipo;
    private JComboBox<String>    cmbCliente;
    private JComboBox<String>    cmbTecnico;
    private JComboBox<String>    cmbDia;
    private JComboBox<String>    cmbMes;
    private JComboBox<String>    cmbAnio;
    private JTextArea            txtDescripcion;
    private JButton              btnGuardar;
    private JButton              btnCancelar;

    // ── Componentes de la tabla de prendas ────────────────────────────────────
    private JTable               tablaPrendas;
    private DefaultTableModel    modeloTabla;
    private JButton              btnRecargar;

    public RegistrarPrendaView() {
        setTitle("Registrar Prenda - TextilCare");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Pestañas: una para registrar, otra para ver todas las prendas
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Arial", Font.BOLD, 13));

        pestanas.addTab("📋 Registrar Prenda", crearPanelRegistro());
        pestanas.addTab("📦 Estado de Prendas",  crearPanelTabla());

        add(pestanas, BorderLayout.CENTER);
    }

    // ── Pestaña 1: Formulario de registro ─────────────────────────────────────
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Registrar Prenda", SwingConstants.CENTER);
        titulo.setBounds(150, 15, 350, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(marronOscuro);
        panel.add(titulo);

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(280, 50, 100, 100);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imgE = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imgE));
        }
        panel.add(lblLogo);

        // Tipo de prenda
        JLabel lblTipo = new JLabel("Tipo de prenda:");
        lblTipo.setBounds(40, 165, 150, 25);
        panel.add(lblTipo);

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("-- Seleccione --");
        for (String tipo : new String[]{"Camisa","Pantalon","Vestido","Chaqueta","Abrigo","Falda","Blusa","Traje","Otro"}) {
            cmbTipo.addItem(tipo);
        }
        cmbTipo.setBounds(40, 193, 580, 30);
        panel.add(cmbTipo);

        // Cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(40, 235, 150, 25);
        panel.add(lblCliente);

        cmbCliente = new JComboBox<>();
        cmbCliente.addItem("-- Seleccione --");
        cmbCliente.setBounds(40, 263, 580, 30);
        panel.add(cmbCliente);

        // Técnico
        JLabel lblTecnico = new JLabel("Tecnico:");
        lblTecnico.setBounds(40, 305, 150, 25);
        panel.add(lblTecnico);

        cmbTecnico = new JComboBox<>();
        cmbTecnico.addItem("-- Seleccione --");
        cmbTecnico.setBounds(40, 333, 580, 30);
        panel.add(cmbTecnico);

        // Fecha
        JLabel lblFecha = new JLabel("Fecha de ingreso:");
        lblFecha.setBounds(40, 375, 150, 25);
        panel.add(lblFecha);

        cmbDia = new JComboBox<>();
        cmbDia.addItem("Dia");
        for (int i = 1; i <= 31; i++) cmbDia.addItem(String.valueOf(i));
        cmbDia.setBounds(40, 403, 100, 30);
        panel.add(cmbDia);

        cmbMes = new JComboBox<>();
        cmbMes.addItem("Mes");
        for (String m : new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                                     "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"}) {
            cmbMes.addItem(m);
        }
        cmbMes.setBounds(150, 403, 160, 30);
        panel.add(cmbMes);

        cmbAnio = new JComboBox<>();
        cmbAnio.addItem("Año");
        for (int i = 2026; i <= 2030; i++) cmbAnio.addItem(String.valueOf(i));
        cmbAnio.setBounds(320, 403, 300, 30);
        panel.add(cmbAnio);

        // Descripción
        JLabel lblDesc = new JLabel("Descripcion:");
        lblDesc.setBounds(40, 445, 150, 25);
        panel.add(lblDesc);

        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBounds(40, 473, 580, 80);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 190, 180)));
        panel.add(scroll);

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(120, 575, 150, 38);
        btnGuardar.setBackground(cafe);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnGuardar);

        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(390, 575, 150, 38);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnCancelar);

        return panel;
    }

    // ── Pestaña 2: Tabla de todas las prendas con su estado ───────────────────
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);

        // Título de la pestaña
        JLabel titulo = new JLabel("Estado de todas las prendas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(marronOscuro);
        panel.add(titulo, BorderLayout.NORTH);

        // Columnas de la tabla
        String[] columnas = {"ID", "Tipo", "Cliente", "Tecnico", "Fecha", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Hace que las celdas no se puedan editar directamente
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaPrendas = new JTable(modeloTabla);
        tablaPrendas.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPrendas.setRowHeight(28);
        tablaPrendas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaPrendas.getTableHeader().setBackground(cafe);
        tablaPrendas.getTableHeader().setForeground(Color.WHITE);

        // Colores en la columna Estado según el valor
        tablaPrendas.getColumnModel().getColumn(5).setCellRenderer(
            (table, value, isSelected, hasFocus, row, column) -> {
                JLabel celda = new JLabel(value == null ? "" : value.toString(), SwingConstants.CENTER);
                celda.setOpaque(true);
                String estado = value == null ? "" : value.toString().toLowerCase();

                if (estado.equals("pendiente")) {
                    // Pendiente → amarillo
                    celda.setBackground(new Color(255, 220, 100));
                    celda.setForeground(new Color(100, 70, 0));
                } else if (estado.equals("en proceso")) {
                    // En proceso → azul claro
                    celda.setBackground(new Color(100, 160, 220));
                    celda.setForeground(Color.WHITE);
                } else if (estado.equals("reparada")) {
                    // Reparada → verde (en stock / lista)
                    celda.setBackground(verdeStock);
                    celda.setForeground(Color.WHITE);
                } else {
                    // Cualquier otro estado → gris
                    celda.setBackground(new Color(200, 200, 200));
                    celda.setForeground(Color.BLACK);
                }

                return celda;
            }
        );

        // Ancho de las columnas
        tablaPrendas.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaPrendas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaPrendas.getColumnModel().getColumn(2).setPreferredWidth(140);
        tablaPrendas.getColumnModel().getColumn(3).setPreferredWidth(140);
        tablaPrendas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaPrendas.getColumnModel().getColumn(5).setPreferredWidth(110);

        JScrollPane scrollTabla = new JScrollPane(tablaPrendas);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Panel inferior con leyenda y botón recargar
        JPanel panelInferior = new JPanel(null);
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setPreferredSize(new java.awt.Dimension(0, 60));

        // Leyenda de colores
        JLabel leyenda1 = new JLabel("🟡 Pendiente   🔵 En proceso   🟢 Reparada");
        leyenda1.setFont(new Font("Arial", Font.PLAIN, 12));
        leyenda1.setBounds(20, 15, 350, 25);
        panelInferior.add(leyenda1);

        // Botón para recargar la tabla manualmente
        btnRecargar = new JButton("🔄 Recargar");
        btnRecargar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRecargar.setBackground(marronOscuro);
        btnRecargar.setForeground(Color.WHITE);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setBorderPainted(false);
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecargar.setBounds(530, 12, 130, 32);
        panelInferior.add(btnRecargar);

        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }

    // ── Métodos para llenar los combos desde el Controlador ──────────────────

    // Llena el combo de clientes con nombres reales de la BD
    public void cargarClientes(List<String> nombres) {
        cmbCliente.removeAllItems();
        cmbCliente.addItem("-- Seleccione --");
        for (String nombre : nombres) {
            cmbCliente.addItem(nombre);
        }
    }

    // Llena el combo de técnicos con nombres reales de la BD
    public void cargarTecnicos(List<String> nombres) {
        cmbTecnico.removeAllItems();
        cmbTecnico.addItem("-- Seleccione --");
        for (String nombre : nombres) {
            cmbTecnico.addItem(nombre);
        }
    }

    // Llena la tabla con la lista de prendas que trae el controlador
    public void cargarPrendasEnTabla(List<Object[]> filas) {
        // Limpia las filas anteriores
        modeloTabla.setRowCount(0);

        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
    }

    // Limpia el formulario después de guardar
    public void limpiarFormulario() {
        cmbTipo.setSelectedIndex(0);
        cmbCliente.setSelectedIndex(0);
        cmbTecnico.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
        cmbMes.setSelectedIndex(0);
        cmbAnio.setSelectedIndex(0);
        txtDescripcion.setText("");
    }

    // Muestra un mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Muestra un mensaje de éxito
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS para el Controlador ───────────────────────────────────────────
    public JComboBox<String> getCmbTipo()        { return cmbTipo;        }
    public JComboBox<String> getCmbCliente()     { return cmbCliente;     }
    public JComboBox<String> getCmbTecnico()     { return cmbTecnico;     }
    public JComboBox<String> getCmbDia()         { return cmbDia;         }
    public JComboBox<String> getCmbMes()         { return cmbMes;         }
    public JComboBox<String> getCmbAnio()        { return cmbAnio;        }
    public JTextArea         getTxtDescripcion() { return txtDescripcion; }
    public JButton           getBtnGuardar()     { return btnGuardar;     }
    public JButton           getBtnCancelar()    { return btnCancelar;    }
    public JButton           getBtnRecargar()    { return btnRecargar;    }

    public static void main(String[] args) {
        RegistrarPrendaView vista = new RegistrarPrendaView();
        vista.setVisible(true);
    }
}