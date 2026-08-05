package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

/**
 * Vista de registro de proveedores (RegistrarProveedorView) para TextilCare.
 * Proporciona un formulario emergente con campos para ingresar la información 
 * de la empresa proveedora (nombre, NIT, correo, teléfono y productos).
 */
public class RegistrarProveedorView extends JFrame {

    // Componentes de entrada de texto
    private JTextField txtEmpresa;
    private JTextField txtNit;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtProductos;
    
    // Botones de acción del formulario
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final Color morado = new Color(215, 155, 175);
    private final Color moradoOscuro = new Color(145, 75, 95);
    /**
     * Constructor principal: Inicializa la ventana de registro de proveedores con un diseño de posicionamiento absoluto.
     */
    public RegistrarProveedorView() {
        setTitle("Registrar Proveedor - TextilCare");
        setSize(500, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // ── TÍTULO DE LA VENTANA ──
        JLabel titulo = new JLabel("Registrar Proveedor");
        titulo.setBounds(140, 20, 220, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(moradoOscuro);
        add(titulo);

        // ── NOMBRE DE LA EMPRESA ──
        JLabel lblEmpresa = new JLabel("Nombre de la empresa:");
        lblEmpresa.setBounds(40, 70, 200, 25);
        add(lblEmpresa);

        txtEmpresa = new JTextField();
        txtEmpresa.setBounds(40, 100, 400, 30);
        add(txtEmpresa);

        // ── NIT ──
        JLabel lblNit = new JLabel("NIT:");
        lblNit.setBounds(40, 140, 200, 25);
        add(lblNit);

        txtNit = new JTextField();
        txtNit.setBounds(40, 170, 400, 30);
        add(txtNit);

        // ── CORREO ──
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(40, 210, 200, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(40, 240, 400, 30);
        add(txtCorreo);

        // ── TELÉFONO ──
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(40, 280, 200, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(40, 310, 400, 30);
        add(txtTelefono);

        // ── PRODUCTOS ──
        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setBounds(40, 350, 200, 25);
        add(lblProductos);

        txtProductos = new JTextField();
        txtProductos.setBounds(40, 380, 400, 30);
        add(txtProductos);

        // ── BOTÓN GUARDAR ──
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 430, 120, 35);
        btnGuardar.setBackground(morado);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnGuardar);

        // ── BOTÓN CANCELAR ──
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 430, 120, 35);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCancelar);
    }

    /**
     * Restablece todos los campos del formulario dejándolos en blanco después de guardar.
     */
    public void limpiarFormulario() {
        txtEmpresa.setText("");
        txtNit.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtProductos.setText("");
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

    public JTextField getTxtEmpresa() {
        return txtEmpresa;
    }

    public JTextField getTxtNit() {
        return txtNit;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtProductos() {
        return txtProductos;
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
        RegistrarProveedorView vista = new RegistrarProveedorView();
        vista.setVisible(true);
    }
}