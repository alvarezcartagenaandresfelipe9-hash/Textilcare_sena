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

public class RegistroUsuarioView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final Color cafe = new Color(181, 137, 103);

    public RegistroUsuarioView(String rol) {
        setTitle("Registrar " + rol);
        setSize(500, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(238, 232, 224));

        // TITULO
        JLabel titulo = new JLabel("Registrar " + rol, SwingConstants.CENTER);
        titulo.setBounds(0, 0, 500, 50);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBackground(cafe);
        titulo.setOpaque(true);
        add(titulo);

        // LOGO
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

        // NOMBRE
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setBounds(40, 155, 200, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(40, 180, 400, 32);
        add(txtNombre);

        // DOCUMENTO
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(40, 220, 200, 25);
        add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(40, 245, 400, 32);
        add(txtDocumento);

        // CORREO
        JLabel lblCorreo = new JLabel("Correo electronico:");
        lblCorreo.setBounds(40, 285, 200, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(40, 310, 400, 32);
        add(txtCorreo);

        // TELEFONO
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(40, 350, 200, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(40, 375, 400, 32);
        add(txtTelefono);

        // CONTRASENA
        JLabel lblContrasena = new JLabel("Contrasena inicial:");
        lblContrasena.setBounds(40, 415, 200, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(40, 440, 400, 32);
        add(txtContrasena);

        // BOTON GUARDAR
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(90, 500, 130, 40);
        btnGuardar.setBackground(cafe);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnGuardar);

        // BOTON CANCELAR
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 500, 130, 40);
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(cafe);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCancelar);
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS ──
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

    // Solo para probar rapido como se ve el formulario
    public static void main(String[] args) {
        RegistroUsuarioView vista = new RegistroUsuarioView("Cliente");
        vista.setVisible(true);
    }
}