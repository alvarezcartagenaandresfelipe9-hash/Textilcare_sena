package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;

import java.net.URL;
import java.util.List;

/**
 * Vista de registro de prendas (RegistrarPrendaView) para TextilCare.
 * Proporciona un formulario emergente con campos para seleccionar el tipo de prenda,
 * cliente asociado, técnico encargado, fecha de ingreso y descripción detallada.
 */
public class RegistrarPrendaView extends JFrame {

    // Componentes de selección y entrada de datos
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbTecnico;
    private JComboBox<String> cmbDia, cmbMes, cmbAnio;
    private JTextArea txtDescripcion;
    
    // Botones de acción del formulario
    private JButton btnGuardar;
    private JButton btnCancelar;

    // Paleta de colores institucional para mantener la coherencia visual
    private final Color morado = new Color(215, 155, 175);
    private final Color moradoOscuro = new Color(145, 75, 95); 

    /**
     * Constructor principal: Inicializa la ventana de registro de prendas con un diseño de posicionamiento absoluto.
     */
    public RegistrarPrendaView() {
        setTitle("Registrar Prenda - TextilCare");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // ── TÍTULO DE LA VENTANA ──
        JLabel titulo = new JLabel("Registrar Prenda", SwingConstants.CENTER);
        titulo.setBounds(150, 20, 200, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(moradoOscuro);
        add(titulo);

        // ── LOGOTIPO INSTITUCIONAL ──
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(190, 60, 100, 100);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }
        add(lblLogo);

        // ── TIPO DE PRENDA ──
        JLabel lblTipo = new JLabel("Tipo de prenda:");
        lblTipo.setBounds(40, 180, 150, 25);
        add(lblTipo);

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("-- Seleccione --");
        cmbTipo.addItem("Camisa");
        cmbTipo.addItem("Pantalon");
        cmbTipo.addItem("Vestido");
        cmbTipo.addItem("Chaqueta");
        cmbTipo.addItem("Abrigo");
        cmbTipo.addItem("Falda");
        cmbTipo.addItem("Blusa");
        cmbTipo.addItem("Traje");
        cmbTipo.addItem("Otro");
        cmbTipo.setBounds(40, 210, 400, 30);
        add(cmbTipo);

        // ── CLIENTE ──
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(40, 255, 150, 25);
        add(lblCliente);

        cmbCliente = new JComboBox<>();
        cmbCliente.addItem("-- Seleccione --");
        cmbCliente.setBounds(40, 285, 400, 30);
        add(cmbCliente);

        // ── TÉCNICO ──
        JLabel lblTecnico = new JLabel("Tecnico:");
        lblTecnico.setBounds(40, 330, 150, 25);
        add(lblTecnico);

        cmbTecnico = new JComboBox<>();
        cmbTecnico.addItem("-- Seleccione --");
        cmbTecnico.setBounds(40, 360, 400, 30);
        add(cmbTecnico);

        // ── FECHA DE INGRESO ──
        JLabel lblFecha = new JLabel("Fecha de ingreso:");
        lblFecha.setBounds(40, 405, 150, 25);
        add(lblFecha);

        cmbDia = new JComboBox<>();
        cmbDia.addItem("Dia");
        for (int i = 1; i <= 31; i++) {
            cmbDia.addItem(String.valueOf(i));
        }
        cmbDia.setBounds(40, 435, 80, 30);
        add(cmbDia);

        cmbMes = new JComboBox<>();
        cmbMes.addItem("Mes");
        cmbMes.addItem("Enero");
        cmbMes.addItem("Febrero");
        cmbMes.addItem("Marzo");
        cmbMes.addItem("Abril");
        cmbMes.addItem("Mayo");
        cmbMes.addItem("Junio");
        cmbMes.addItem("Julio");
        cmbMes.addItem("Agosto");
        cmbMes.addItem("Septiembre");
        cmbMes.addItem("Octubre");
        cmbMes.addItem("Noviembre");
        cmbMes.addItem("Diciembre");
        cmbMes.setBounds(130, 435, 130, 30);
        add(cmbMes);

        cmbAnio = new JComboBox<>();
        cmbAnio.addItem("Año");
        for (int i = 2026; i <= 2030; i++) {
            cmbAnio.addItem(String.valueOf(i));
        }
        cmbAnio.setBounds(270, 435, 170, 30);
        add(cmbAnio);

        // ── DESCRIPCIÓN ──
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setBounds(40, 480, 150, 25);
        add(lblDescripcion);

        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBounds(40, 510, 400, 80);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(215, 205, 235)));
        add(scroll);

        // ── BOTÓN GUARDAR ──
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 615, 120, 35);
        btnGuardar.setBackground(morado);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnGuardar);

        // ── BOTÓN CANCELAR ──
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 615, 120, 35);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCancelar);
    }

    /**
     * Llena el ComboBox de clientes con los nombres reales obtenidos de la base de datos.
     * @param nombres Lista con los nombres de los clientes.
     */
    public void cargarClientes(List<String> nombres) {
        for (String nombre : nombres) {
            cmbCliente.addItem(nombre);
        }
    }

    /**
     * Llena el ComboBox de técnicos con los nombres reales obtenidos de la base de datos.
     * @param nombres Lista con los nombres de los técnicos.
     */
    public void cargarTecnicos(List<String> nombres) {
        for (String nombre : nombres) {
            cmbTecnico.addItem(nombre);
        }
    }

    /**
     * Restablece todos los campos del formulario a su estado predeterminado después de guardar.
     */
    public void limpiarFormulario() {
        cmbTipo.setSelectedIndex(0);
        cmbCliente.setSelectedIndex(0);
        cmbTecnico.setSelectedIndex(0);
        cmbDia.setSelectedIndex(0);
        cmbMes.setSelectedIndex(0);
        cmbAnio.setSelectedIndex(0);
        txtDescripcion.setText("");
    }

    /**
     * Muestra un cuadro de diálogo emergente con un mensaje de error.
     * @param mensaje Mensaje descriptivo del error.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo emergente indicando que la operación fue exitosa.
     * @param mensaje Mensaje de éxito.
     */
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS QUE UTILIZA EL CONTROLADOR ──

    public JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public JComboBox<String> getCmbCliente() {
        return cmbCliente;
    }

    public JComboBox<String> getCmbTecnico() {
        return cmbTecnico;
    }

    public JComboBox<String> getCmbDia() {
        return cmbDia;
    }

    public JComboBox<String> getCmbMes() {
        return cmbMes;
    }

    public JComboBox<String> getCmbAnio() {
        return cmbAnio;
    }

    public JTextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Método principal (main): Permite probar y visualizar la ventana de manera independiente.
     */
    public static void main(String[] args) {
        RegistrarPrendaView vista = new RegistrarPrendaView();
        vista.setVisible(true);
    }
}