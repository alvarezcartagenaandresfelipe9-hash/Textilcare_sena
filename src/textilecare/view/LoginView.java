package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import java.net.URL;

public class LoginView extends JFrame {

    // Se quitó el JComboBox de rol porque ya no se necesita
    private JTextField     txtDocumento;
    private JPasswordField txtContrasena;
    private JButton        btnContinuar;
    private JLabel         lblMensaje;

    public LoginView() {
        setTitle("TextilCare - Iniciar Sesion ");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(crearPanelIzquierdo(), BorderLayout.WEST);
        add(crearPanelDerecho(),   BorderLayout.CENTER);
    }

    // Arma el panel blanco de la izquierda con logo, campos y botón
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(300, 0));

        // Logo de la aplicación
        JLabel lblLogo = crearLogo();
        lblLogo.setBounds(75, 15, 150, 150);
        panel.add(lblLogo);

        // Texto de bienvenida
        JLabel lblBienvenida = new JLabel("Bienvenido, ingresa tus datos");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 13));
        lblBienvenida.setForeground(new Color(80, 80, 80));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenida.setBounds(20, 175, 260, 20);
        panel.add(lblBienvenida);

        // Campo documento
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(30, 210, 240, 20);
        panel.add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(30, 232, 240, 32);
        txtDocumento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        panel.add(txtDocumento);

        // Campo contraseña
        JLabel lblContrasena = new JLabel("Contrasena:");
        lblContrasena.setBounds(30, 275, 240, 20);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(30, 297, 240, 32);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        panel.add(txtContrasena);

        // Botón continuar — subió porque ya no hay combo de rol
        // Color igual al original: RGB (145, 75, 95)
        btnContinuar = new JButton("Continuar");
        btnContinuar.setBackground(new Color(145, 75, 95));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 13));
        btnContinuar.setFocusPainted(false);
        btnContinuar.setBorderPainted(false);
        btnContinuar.setBounds(30, 348, 240, 42);
        panel.add(btnContinuar);

        // Mensaje de error debajo del botón
        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBounds(30, 400, 240, 20);
        panel.add(lblMensaje);

        return panel;
    }

    // Arma el panel derecho con degradado rosado y el texto "INICIA SESION"
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                // Colores iguales al original
                Color colorInicio = new Color(145, 75, 95);
                Color colorFin    = new Color(215, 155, 175);
                GradientPaint degradado = new GradientPaint(
                    0, 0, colorInicio,
                    getWidth(), getHeight(), colorFin
                );
                g2.setPaint(degradado);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lblTexto = new JLabel("INICIA SESION");
        lblTexto.setFont(new Font("Arial", Font.BOLD, 40));
        lblTexto.setForeground(Color.WHITE);
        panel.add(lblTexto);

        return panel;
    }

    // Carga el logo desde la carpeta de recursos del proyecto
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

    // ── GETTERS ───────────────────────────────────────────────────────────────

    // Devuelve el documento escrito
    public String getDocumento() {
        return txtDocumento.getText().trim();
    }

    // Devuelve la contraseña escrita como texto plano
    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    // Muestra un mensaje de error debajo del botón
    public void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    // Borra el mensaje de error
    public void limpiarMensaje() {
        lblMensaje.setText(" ");
    }

    // Devuelve el botón para que el controlador le agregue el listener
    public JButton getBtnContinuar() {
        return btnContinuar;
    }

    public static void main(String[] args) {
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}