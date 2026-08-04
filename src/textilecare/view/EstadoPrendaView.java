package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores e imágenes
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

// Importaciones para el manejo y conversión de arreglos de bytes a imágenes (BufferedImages)
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Vista detallada del flujo y estado de una prenda (EstadoPrendaView) para TextilCare.
 * Muestra el historial progresivo de la prenda mediante tarjetas visuales según sus fases de reparación.
 */
public class EstadoPrendaView extends JFrame {

    // Componentes principales de la interfaz
    private JLabel lblNombrePrenda;
    private JLabel lblEstadoActual;
    private JPanel panelTarjetas;
    private JButton btnVolver;

    // Paleta de colores institucional para mantener coherencia visual con el resto del sistema
    private final Color morado = new Color(155, 89, 182);         // Color de acento institucional
    private final Color fondoLila = new Color(243, 237, 250);     // Color de fondo general de la ventana (lila claro)

    /**
     * Constructor principal: Inicializa la ventana, dimensiones, colores y distribución absoluta de los componentes.
     */
    public EstadoPrendaView() {
        // Título que aparece en la barra superior de la ventana de Windows
        setTitle("Estado de prenda");
        
        // Dimensiones totales de la ventana: 1100 de ancho por 650 de alto
        setSize(1100, 650);
        
        // Centra la ventana en el monitor
        setLocationRelativeTo(null);
        
        // Cierra únicamente esta ventana sin afectar al resto de la aplicación
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Diseño por coordenadas absolutas (null layout) para posicionar con precisión quirúrgica
        setLayout(null);
        
        // Color de fondo general de la ventana
        getContentPane().setBackground(fondoLila);

        // ── TÍTULO DE LA VISTA ──
        JLabel titulo = new JLabel("Estado de prenda");
        titulo.setBounds(40, 20, 300, 40);
        titulo.setOpaque(true);
        titulo.setBackground(morado);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // ── NOMBRE DE LA PRENDA (Dinámico) ──
        lblNombrePrenda = new JLabel();
        lblNombrePrenda.setBounds(40, 75, 300, 25);
        lblNombrePrenda.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblNombrePrenda);

        // ── ESTADO ACTUAL (Dinámico) ──
        lblEstadoActual = new JLabel();
        lblEstadoActual.setBounds(40, 105, 300, 20);
        lblEstadoActual.setFont(new Font("Arial", Font.PLAIN, 13));
        lblEstadoActual.setForeground(Color.GRAY);
        add(lblEstadoActual);

        // ── CONTENEDOR DE TARJETAS ──
        // Panel donde se dibujarán de 1 a 3 tarjetas dependiendo de la fase en la que se encuentre la prenda
        panelTarjetas = new JPanel();
        panelTarjetas.setBounds(40, 135, 1000, 430);
        panelTarjetas.setLayout(null);
        panelTarjetas.setBackground(fondoLila);
        add(panelTarjetas);

        // ── BOTÓN VOLVER ──
        btnVolver = new JButton("← Volver");
        btnVolver.setBounds(40, 580, 120, 35);
        btnVolver.setBackground(morado);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        add(btnVolver);
    }

    /**
     * Actualiza la información principal de la prenda y genera de manera dinámica las tarjetas de progreso correspondientes.
     * @param tipo Tipo de prenda (ej. "Camisa", "Pantalón").
     * @param fecha Fecha de registro inicial.
     * @param descripcion Descripción detallada del daño o requerimiento.
     * @param estado Estado actual del proceso ("Pendiente", "En proceso" o "Reparada").
     * @param cliente Nombre del cliente propietario.
     * @param tecnico Nombre del técnico asignado.
     * @param fotoPendiente Fotografía correspondiente a la fase inicial (puede ser null).
     * @param fotoEnProceso Fotografía correspondiente a la fase de trabajo (puede ser null).
     * @param fotoReparada Fotografía correspondiente al resultado final (puede ser null).
     */
    public void mostrarPrenda(String tipo, String fecha, String descripcion, String estado, String cliente, String tecnico,
                              byte[] fotoPendiente, byte[] fotoEnProceso, byte[] fotoReparada) {

        lblNombrePrenda.setText(tipo);
        lblEstadoActual.setText("Estado actual: " + estado);

        // Limpia cualquier tarjeta generada previamente antes de redibujar
        panelTarjetas.removeAll();

        // Renderiza el historial de tarjetas acumulativo según el estado de avance
        if (estado.equals("Pendiente")) {
            panelTarjetas.add(crearTarjeta(0, fecha, cliente, tecnico, descripcion, "Pendiente", fotoPendiente));

        } else if (estado.equals("En proceso")) {
            panelTarjetas.add(crearTarjeta(0, fecha, cliente, tecnico, descripcion, "Pendiente", fotoPendiente));
            panelTarjetas.add(crearTarjeta(1, fecha, cliente, tecnico, descripcion, "En proceso", fotoEnProceso));

        } else if (estado.equals("Reparada")) {
            panelTarjetas.add(crearTarjeta(0, fecha, cliente, tecnico, descripcion, "Pendiente", fotoPendiente));
            panelTarjetas.add(crearTarjeta(1, fecha, cliente, tecnico, descripcion, "En proceso", fotoEnProceso));
            panelTarjetas.add(crearTarjeta(2, fecha, cliente, tecnico, descripcion, "Reparada", fotoReparada));
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    /**
     * Método auxiliar privado para construir una tarjeta visual individual que representa una etapa del proceso.
     * @param posicion Posición horizontal de la tarjeta (0 para la primera, 1 para la segunda, 2 para la tercera).
     * @param fecha Fecha asociada.
     * @param cliente Nombre del cliente.
     * @param tecnico Nombre del técnico.
     * @param descripcion Descripción de la novedad.
     * @param estado Nombre de la fase ("Pendiente", "En proceso", "Reparada").
     * @param fotoBytes Arreglo de bytes con la imagen asociada (si existe).
     * @return JPanel configurado con la estructura de la tarjeta.
     */
    private JPanel crearTarjeta(int posicion, String fecha, String cliente, String tecnico, String descripcion, String estado, byte[] fotoBytes) {
        // Calcula la coordenada X desplazando 240 píxeles por cada tarjeta adicional
        int x = posicion * 240;

        JPanel tarjeta = new JPanel();
        tarjeta.setBounds(x, 0, 220, 430);
        tarjeta.setLayout(null);
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(new LineBorder(new Color(220, 210, 235)));

        // ── BANNER SUPERIOR DE LA TARJETA ──
        JPanel panelColor = new JPanel();
        panelColor.setBounds(0, 0, 220, 45);
        panelColor.setLayout(null);

        JLabel lblEstadoTop = new JLabel(estado, SwingConstants.CENTER);
        lblEstadoTop.setBounds(0, 10, 220, 25);
        lblEstadoTop.setFont(new Font("Arial", Font.BOLD, 14));

        // Asignación de colores distintivos según la fase de la tarjeta
        if (estado.equals("Reparada")) {
            panelColor.setBackground(new Color(200, 240, 220)); // Verde suave
            lblEstadoTop.setForeground(new Color(15, 110, 86));
        } else if (estado.equals("En proceso")) {
            panelColor.setBackground(new Color(255, 235, 190)); // Amarillo / Naranja pastel
            lblEstadoTop.setForeground(new Color(133, 79, 11));
        } else {
            panelColor.setBackground(new Color(230, 225, 238)); // Gris lila suave
            lblEstadoTop.setForeground(new Color(90, 78, 100));
        }

        panelColor.add(lblEstadoTop);
        tarjeta.add(panelColor);

        // ── FOTOGRAFÍA DE LA ETAPA ──
        JLabel lblFoto = crearLabelFoto(fotoBytes);
        lblFoto.setBounds(10, 55, 200, 110);
        tarjeta.add(lblFoto);

        // ── CAMPOS DE TEXTO E INFORMACIÓN ──
        JLabel lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setBounds(10, 175, 200, 20);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        tarjeta.add(lblFecha);

        JLabel lblCliente = new JLabel("Cliente: " + cliente);
        lblCliente.setBounds(10, 200, 200, 20);
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 12));
        tarjeta.add(lblCliente);

        JLabel lblTecnico = new JLabel("Tecnico: " + tecnico);
        lblTecnico.setBounds(10, 225, 200, 20);
        lblTecnico.setFont(new Font("Arial", Font.PLAIN, 12));
        tarjeta.add(lblTecnico);

        JLabel lblDesc = new JLabel("<html>" + descripcion + "</html>");
        lblDesc.setBounds(10, 255, 200, 80);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);
        tarjeta.add(lblDesc);

        return tarjeta;
    }

    /**
     * Convierte los bytes de una imagen almacenada en un icono visual adaptado al tamaño de la tarjeta.
     * Si no hay foto disponible, muestra una etiqueta descriptiva indicando "Sin foto".
     * @param fotoBytes Arreglo de bytes de la imagen.
     * @return JLabel con la imagen cargada o texto alternativo.
     */
    private JLabel crearLabelFoto(byte[] fotoBytes) {
        JLabel lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(new LineBorder(new Color(225, 218, 238)));
        lblFoto.setOpaque(true);
        lblFoto.setBackground(new Color(247, 245, 250));

        if (fotoBytes == null) {
            lblFoto.setText("Sin foto");
            lblFoto.setForeground(Color.GRAY);
            lblFoto.setFont(new Font("Arial", Font.PLAIN, 12));
            return lblFoto;
        }

        try {
            BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(fotoBytes));
            Image imagenEscalada = imagen.getScaledInstance(200, 110, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception ex) {
            lblFoto.setText("Error al cargar foto");
            lblFoto.setForeground(Color.RED);
            lblFoto.setFont(new Font("Arial", Font.PLAIN, 12));
        }

        return lblFoto;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    /**
     * Método principal (main): Permite probar y ejecutar esta ventana de manera independiente.
     */
    public static void main(String[] args) {
        EstadoPrendaView vista = new EstadoPrendaView();
        vista.mostrarPrenda("Camisa", "2026-07-01", "Mancha de cafe en el cuello", "En proceso", "Carlos Perez", "Daniel Ramirez", null, null, null);
        vista.setVisible(true);
    }
}