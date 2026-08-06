package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;

import java.net.URL;

/**
 * Vista de registro de usuarios (RegistroUsuarioView) para TextilCare.
 * Proporciona un formulario emergente reutilizable para registrar distintos roles 
 * (como clientes o técnicos), capturando nombre, documento, correo, teléfono y contraseña inicial.
 */
public class RegistroUsuarioView extends JFrame {

    // Componentes de entrada de texto e información sensible
    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    
    // Botones de acción del formulario
    private JButton btnGuardar;
    private JButton btnCancelar;

    // Paleta de colores institucional compartida en el sistema
    private final Color morado = new Color(215, 155, 175); // Color de acento institucional

    /**
     * Constructor principal: Inicializa la ventana de registro de usuarios configurando
     * los componentes visuales según el rol especificado.
     * @param rol Rol del usuario a registrar (ej. "Cliente", "Técnico").
     */
    public RegistroUsuarioView(String rol) {
        setTitle("Registrar " + rol);
        setSize(500, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(248, 240, 243));

        // ── TÍTULO DE LA VENTANA ──
        JLabel titulo = new JLabel("Registrar " + rol, SwingConstants.CENTER);
        titulo.setBounds(0, 0, 500, 50);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBackground(morado);
        titulo.setOpaque(true);
        add(titulo);

        // ── LOGOTIPO INSTITUCIONAL ──
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setBounds(205, 55, 90, 90);
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }
        add(lblLogo);

        // ── NOMBRE COMPLETO ──
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setBounds(40, 155, 200, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(40, 180, 400, 32);
        add(txtNombre);

        // ── DOCUMENTO ──
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(40, 220, 200, 25);
        add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(40, 245, 400, 32);
        add(txtDocumento);

        // ── CORREO ELECTRÓNICO ──
        JLabel lblCorreo = new JLabel("Correo electronico:");
        lblCorreo.setBounds(40, 285, 200, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(40, 310, 400, 32);
        add(txtCorreo);

        // ── TELÉFONO ──
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(40, 350, 200, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(40, 375, 400, 32);
        add(txtTelefono);

        // ── CONTRASEÑA INICIAL ──
        JLabel lblContrasena = new JLabel("Contrasena inicial:");
        lblContrasena.setBounds(40, 415, 200, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(40, 440, 400, 32);
        add(txtContrasena);

        // ── BOTÓN GUARDAR ──
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 500, 130, 40);
        btnGuardar.setBackground(morado);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnGuardar);

        // ── BOTÓN CANCELAR ──
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 500, 130, 40);
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(morado);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCancelar);
    }

    /**
     * Restablece todos los campos del formulario dejándolos en blanco después de realizar el registro.
     */
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
    }

    /**
     * Muestra un cuadro de diálogo emergente con un mensaje de error.
     * @param mensaje Descripción del error.
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

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtDocumento() {
        return txtDocumento;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
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
        RegistroUsuarioView vista = new RegistroUsuarioView("Cliente");
        vista.setVisible(true);
    }
}