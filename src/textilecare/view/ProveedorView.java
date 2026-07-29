package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;

import java.net.URL;
import java.util.List;

public class ProveedorView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JLabel lblTotal, lblActivos, lblNuevos;
    private JButton btnNuevoProveedor;
    private JButton btnCambiarEstado;
    private JButton btnPrendas, btnInventario, btnTecnicos;

    private final Color cafe = new Color(181, 137, 103);
    private final Color marronOscuro = new Color(90, 58, 35);
    private final Color fondo = new Color(238, 232, 224);

    public ProveedorView(String nombreSupervisor) {
        setTitle("TextilCare - Proveedores");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);

        add(crearBarraLateral(nombreSupervisor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearBarraLateral(String nombreSupervisor) {
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

        btnPrendas = crearBotonMenu("Prendas", false);
        btnInventario = crearBotonMenu("Inventario", false);
        btnTecnicos = crearBotonMenu("Tecnicos", false);
        JButton btnProveedores = crearBotonMenu("Proveedores", true);

        panel.add(btnPrendas);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnInventario);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnTecnicos);
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
            boton.setBackground(cafe);
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel titulo = new JLabel("Proveedores");
        titulo.setFont(new Font("Georgia", Font.BOLD, 24));
        titulo.setForeground(marronOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);
        panelSuperior.add(titulo, BorderLayout.NORTH);
        panelSuperior.add(crearTarjetas(), BorderLayout.CENTER);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearTarjetas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(fondo);

        lblTotal = new JLabel("0");
        lblActivos = new JLabel("0");
        lblNuevos = new JLabel("0");

        panel.add(crearTarjeta("Registrados", lblTotal));
        panel.add(crearTarjeta("Activos", lblActivos));
        panel.add(crearTarjeta("Nuevos hoy", lblNuevos));

        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel lblNumero) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 210, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        tarjeta.setPreferredSize(new Dimension(160, 70));

        lblNumero.setFont(new Font("Arial", Font.BOLD, 24));
        lblNumero.setForeground(marronOscuro);
        lblNumero.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        tarjeta.add(lblNumero);
        tarjeta.add(lblTitulo);

        return tarjeta;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Nombre empresa", "NIT", "Telefono", "Correo", "Productos", "Estado", "Supervisor"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setGridColor(new Color(225, 218, 210));

        tabla.getTableHeader().setBackground(marronOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 36));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 218, 210), 1));

        return scroll;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        panel.setBackground(fondo);

        btnNuevoProveedor = new JButton("+ Nuevo Proveedor");
        btnNuevoProveedor.setBackground(cafe);
        btnNuevoProveedor.setForeground(Color.WHITE);
        btnNuevoProveedor.setFocusPainted(false);
        btnNuevoProveedor.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnNuevoProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.setBackground(marronOscuro);
        btnCambiarEstado.setForeground(Color.WHITE);
        btnCambiarEstado.setFocusPainted(false);
        btnCambiarEstado.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnCambiarEstado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnCambiarEstado);
        panel.add(btnNuevoProveedor);

        return panel;
    }

    // Llena la tabla con la lista de proveedores
    public void agregarFila(String nombreEmpresa, String nit, String telefono, String correo, String productos, String estado, String supervisor) {
        modeloTabla.addRow(new Object[]{nombreEmpresa, nit, telefono, correo, productos, estado, supervisor});
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void actualizarTarjetas(int total, int activos, int nuevos) {
        lblTotal.setText(String.valueOf(total));
        lblActivos.setText(String.valueOf(activos));
        lblNuevos.setText(String.valueOf(nuevos));
    }

    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    public String getNombreEmpresaEnFila(int fila) {
        return (String) modeloTabla.getValueAt(fila, 0);
    }

    public String getEstadoEnFila(int fila) {
        return (String) modeloTabla.getValueAt(fila, 5);
    }

    public void mostrarAviso(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    public boolean confirmar(String mensaje) {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        return opcion == javax.swing.JOptionPane.YES_OPTION;
    }

    // ── GETTERS que usa el Controlador ──
    public JTable getTabla() {
        return tabla;
    }

    public JButton getBtnNuevoProveedor() {
        return btnNuevoProveedor;
    }

    public JButton getBtnCambiarEstado() {
        return btnCambiarEstado;
    }

    public JButton getBtnPrendas() {
        return btnPrendas;
    }

    public JButton getBtnInventario() {
        return btnInventario;
    }

    public JButton getBtnTecnicos() {
        return btnTecnicos;
    }

    // Solo para ver rapido como se ve la ventana
    public static void main(String[] args) {
        ProveedorView vista = new ProveedorView("Alejandro");
        vista.agregarFila("Textiles del Valle", "900123456", "3001234567", "contacto@tdv.com", "Hilos, telas", "Activo", "Alejandro Diaz");
        vista.actualizarTarjetas(1, 1, 0);
        vista.setVisible(true);
    }
}