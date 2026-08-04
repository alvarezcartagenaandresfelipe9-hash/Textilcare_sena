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

/**
 * Vista de inicio de sesión (LoginView) para TextilCare.
 */
public class LoginView extends JFrame {

    // Declaración de los componentes de la interfaz
    private JTextField txtDocumento;
    private JPasswordField txtContrasena;
    private JComboBox<String> cboRol;
    private JButton btnContinuar;
    private JLabel lblMensaje;

    // Constructor principal de la ventana del Login
    public LoginView() {
        // Título que aparece arriba en la barra de la ventana
        setTitle("TextilCare - Iniciar Sesion ");

        // Tamaño total de la ventana: 900 de ancho por 560 de alto
        setSize(900, 560);

        // Esto hace que la ventana aparezca justo en el medio de la pantalla
        setLocationRelativeTo(null);

        // Cierra el programa por completo al hacer clic en la "X"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Evita que el usuario estire o cambie el tamaño de la ventana con el mouse
        setResizable(false);

        // Divide la ventana en dos partes (izquierda y derecha)
        setLayout(new BorderLayout());

        // Agregamos el panel izquierdo (el blanco con los campos y el logo) a la izquierda
        add(crearPanelIzquierdo(), BorderLayout.WEST);

        // Agregamos el panel derecho (el fondo donde dice "INICIA SESION") al centro
        add(crearPanelDerecho(), BorderLayout.CENTER);
    }

    // Método que arma todo el panel izquierdo (el blanquito donde uno escribe)
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();

        // Quitamos los diseños automáticos para poder ubicar las cosas con coordenadas exactas (x, y)
        panel.setLayout(null);

        // Este es el color de fondo de todo este lado izquierdo: BLANCO
        panel.setBackground(Color.WHITE);

        // Ancho fijo de 300 píxeles para este panel izquierdo
        panel.setPreferredSize(new Dimension(300, 0));

        // Aquí cargamos el loguito de la app
        JLabel lblLogo = crearLogo();
        lblLogo.setBounds(75, 15, 150, 150); // Ubicación y tamaño del logo
        panel.add(lblLogo);

        // Texto de bienvenida arriba del formulario
        JLabel lblBienvenida = new JLabel("Bienvenido, ingresa tus datos");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 13));
        lblBienvenida.setForeground(new Color(80, 80, 80));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenida.setBounds(20, 165, 260, 20);
        panel.add(lblBienvenida);

        // Texto que dice "Documento:"
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(30, 200, 240, 20);
        panel.add(lblDocumento);

        // Caja de texto blanca donde el usuario escribe su número de documento
        txtDocumento = new JTextField();
        txtDocumento.setBounds(30, 222, 240, 32);
        // Le ponemos un borde simple con un poco de espacio interno para que no se vea tan pegado
        txtDocumento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        panel.add(txtDocumento);

        // Texto que dice "Contrasena:"
        JLabel lblContrasena = new JLabel("Contrasena:");
        lblContrasena.setBounds(30, 262, 240, 20);
        panel.add(lblContrasena);

        // Caja de contraseña (la que oculta las letras con puntos o asteriscos)
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(30, 284, 240, 32);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        panel.add(txtContrasena);

        // Texto que dice "Rol:"
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 324, 240, 20);
        panel.add(lblRol);

        // Menú desplegable para elegir el rol (Administrador, Supervisor, etc.)
        cboRol = new JComboBox<>(new String[]{"Administrador", "Supervisor", "Tecnico", "Vendedor", "Cliente"});
        cboRol.setBounds(30, 346, 240, 32);
        panel.add(cboRol);

        // El botón para avanzar ("Continuar")
        btnContinuar = new JButton("Continuar");

        // Este es el color de fondo del botón: un tono lila/rosado claro (RGB: 200, 145, 245)
        btnContinuar.setBackground(new Color(81, 45, 168)); 


        // Este es el color del texto de adentro del botón: BLANCO
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 13));

        // Le quitamos el borde feo por defecto y dejamos el fondo pintado
        btnContinuar.setFocusPainted(false);
        btnContinuar.setBorderPainted(false);

        btnContinuar.setBounds(30, 398, 240, 42);
        panel.add(btnContinuar);

        // Etiqueta invisible o de alerta por si hay errores al iniciar sesión
        lblMensaje = new JLabel(" ");

        // Color del texto de error: ROJO encendido para que se note
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBounds(30, 448, 240, 20);
        panel.add(lblMensaje);

        return panel;
    }

    // Método que arma el panel derecho (donde dice "INICIA SESION")
    private JPanel crearPanelDerecho() {
        // Creamos el panel con un fondo degradado en vez de un solo color plano.
        // Sobreescribimos paintComponent para "pintar" el degradado antes que todo lo demás.
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                // Color de inicio del degradado (morado oscuro)
                Color colorInicio = new Color(88, 24, 130);
                // Color final del degradado (morado mas claro)
                Color colorFin = new Color(155, 89, 182);

                // GradientPaint dibuja una transicion de un color a otro
                GradientPaint degradado = new GradientPaint(0, 0, colorInicio, getWidth(), getHeight(), colorFin);
                g2.setPaint(degradado);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Usamos esto para centrar perfectamente el texto grande en medio de todo el panel
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        // El texto grandote que se ve a la derecha
        JLabel lblTexto = new JLabel("INICIA SESION");

        // Tipografía Arial, en Negrita (BOLD), con un tamaño gigante de 40 puntos
        lblTexto.setFont(new Font("Arial", Font.BOLD, 40));

        // Color de las letras: BLANCO para que resalte sobre el fondo morado
        lblTexto.setForeground(Color.WHITE);
        panel.add(lblTexto);

        return panel;
    }

    // Método encargado de buscar y cargar la imagen del logo de forma segura
    private JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Ruta interna en el proyecto donde está guardada la imagen del logo
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");

        // Si la imagen existe, la cargamos y la ajustamos a 150x150 píxeles con buena calidad
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        } else {
            // Si por alguna razón no encuentra la imagen, muestra la palabra "LOGO" en texto plano para que no falle
            lblLogo.setText("LOGO");
        }

        return lblLogo;
    }

    // ── GETTERS PARA EXTRAER LOS DATOS ──

    // Devuelve lo que el usuario escribió en el campo de documento (sin espacios vacíos al lado y lado)
    public String getDocumento() {
        return txtDocumento.getText().trim();
    }

    // Devuelve lo que el usuario escribió en la contraseña convertida a texto plano
    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    // Devuelve el rol exacto que el usuario seleccionó en la lista desplegable
    public String getRol() {
        return cboRol.getSelectedItem().toString();
    }

    // Método para mostrar un mensaje de error dinámico en la etiqueta roja inferior
    public void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    // Método para borrar el mensaje de error y dejarlo limpio
    public void limpiarMensaje() {
        lblMensaje.setText(" ");
    }

    // Devuelve el botón continuar para poder programarle acciones desde el controlador
    public JButton getBtnContinuar() {
        return btnContinuar;
    }

    // Método main para probar esta pantalla solita dándole clic en ejecutar
    public static void main(String[] args) {
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}