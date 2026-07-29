package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.net.URL;

public class LoginView extends JFrame {

    private JTextField txtDocumento;
    private JPasswordField txtContrasena;
    private JComboBox<String> cboRol;
    private JButton btnContinuar;
    private JLabel lblMensaje;

    public LoginView() {
        setTitle("TextilCare - Iniciar Sesion");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelDerecho(), BorderLayout.CENTER);
    }

    // Panel blanco con el logo, los campos y el boton
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 0));

        // Logo
        JLabel lblLogo = crearLogo();
        lblLogo.setBounds(75, 20, 150, 150);
        panel.add(lblLogo);

        // Etiqueta y campo de documento
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(30, 190, 240, 20);
        panel.add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(30, 212, 240, 30);
        panel.add(txtDocumento);

        // Etiqueta y campo de contraseña
        JLabel lblContrasena = new JLabel("Contrasena:");
        lblContrasena.setBounds(30, 250, 240, 20);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(30, 272, 240, 30);
        panel.add(txtContrasena);

        // Etiqueta y combo de rol
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 310, 240, 20);
        panel.add(lblRol);

        cboRol = new JComboBox<>(new String[]{"Administrador", "Supervisor", "Tecnico", "Vendedor", "Cliente"});
        cboRol.setBounds(30, 332, 240, 30);
        panel.add(cboRol);

        // Boton continuar
        btnContinuar = new JButton("Continuar");
        btnContinuar.setBackground(new Color(229, 153, 247));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setBounds(30, 380, 240, 40);
        panel.add(btnContinuar);

        // Mensaje de error
        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBounds(30, 430, 240, 20);
        panel.add(lblMensaje);

        return panel;
    }

    // Panel marron del lado derecho, con color solido y un texto
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(92, 0, 61));

        JLabel lblTexto = new JLabel("INICIA SESION");
        lblTexto.setFont(new Font("Arial", Font.BOLD, 40));
        lblTexto.setForeground(Color.BLACK);
        panel.add(lblTexto);

        return panel;
    }

    // Carga la imagen del logo desde el paquete textilecare.recursos
    private JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");

        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        } else {
            lblLogo.setText("LOGO");
        }

        return lblLogo;
    }

    // ── GETTERS ──
    public String getDocumento() {
        return txtDocumento.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    public String getRol() {
        return cboRol.getSelectedItem().toString();
    }

    public void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void limpiarMensaje() {
        lblMensaje.setText(" ");
    }

    public JButton getBtnContinuar() {
        return btnContinuar;
    }

    // Solo para probar rapido como se ve la ventana, sin conectar el login
    public static void main(String[] args) {
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}