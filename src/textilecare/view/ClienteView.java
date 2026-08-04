package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores, dimensiones e interactividad
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;

// Importa la clase URL para localizar recursos multimedia dentro del proyecto
import java.net.URL;

// Importa colecciones de Java para el almacenamiento y control de identificadores
import java.util.ArrayList;
import java.util.List;

/**
 * Vista de la interfaz del Cliente (ClienteView) para TextilCare.
 */
public class ClienteView extends JFrame {

    // Componentes de la interfaz de la tabla y controles del panel
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnDetalle;
    private JButton btnSalir;

    // Lista paralela que almacena el identificador único de cada prenda registrada en la tabla
    private List<Integer> idsPrendas = new ArrayList<>();

    // Paleta de colores institucional para la interfaz del cliente (morada, igual que Login y Administrador)
 private final Color morado = new Color(215, 155, 175);         
private final Color moradoOscuro = new Color(145, 75, 95);      
private final Color fondoLila = new Color(248, 240, 243);       
private final Color grisTexto = new Color(110, 110, 110);    

    /**
     * Constructor principal: Inicializa la ventana principal del Cliente, sus dimensiones, restricciones y componentes de diseño.
     * @param nombreCliente Nombre del cliente que se mostrará saludando en la barra lateral.
     */
    public ClienteView(String nombreCliente) {
        // Título que aparece en la barra superior de la ventana de Windows
        setTitle("TextilCare - Cliente");
        
        // Dimensiones totales de la ventana: 950 de ancho por 600 de alto
        setSize(950, 600);
        
        // Esto hace que la ventana aparezca justo en el centro de la pantalla del monitor
        setLocationRelativeTo(null);
        
        // Acción al cerrar la ventana: Solo se cierra esta ventana sin apagar el sistema completo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Evita que el usuario estire o cambie el tamaño de la ventana con el mouse
        setResizable(false);
        
        // Organiza el diseño general de la ventana dividiendo el espacio en zonas (BorderLayout)
        setLayout(new BorderLayout());

        // Agregamos la barra lateral izquierda pasándole el nombre del cliente
        add(crearBarraLateral(nombreCliente), BorderLayout.WEST);
        
        // Agregamos el contenido principal (la tabla de prendas y los botones) en el centro de la pantalla
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Crea y configura el panel lateral izquierdo con la identidad del cliente y las opciones de navegación.
     * @param nombreCliente Nombre que aparecerá en el saludo.
     * @return Panel lateral izquierdo montado.
     */
    private JPanel crearBarraLateral(String nombreCliente) {
        JPanel panel = new JPanel();
        
        // Organiza los elementos internos de arriba hacia abajo en forma de columna vertical (BoxLayout)
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Este es el color de fondo de toda la barra lateral: un morado oscuro / morado uva (RGB: 88, 24, 130)
        panel.setBackground(moradoOscuro);
        
        // Fija un ancho exacto de 230 píxeles para esta barra lateral
        panel.setPreferredSize(new Dimension(230, 0));
        
        // Margen interno o espacio alrededor del contenido de la barra lateral (arriba, izquierda, abajo, derecha)
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        // 1) Logo arriba de todo, simulando una tarjeta de perfil
        JLabel lblLogo = crearLogo();
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblLogo);

        // Espacio vacío rígido para separar visualmente el logo del nombre
        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // 2) Nombre y rol del cliente, ubicados justo debajo del logo
        JLabel lblSaludo = new JLabel(nombreCliente, SwingConstants.CENTER);
        
        // Tipografía Georgia, estilo Negrita (BOLD), tamaño 17 puntos
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        
        // Color del texto del nombre: BLANCO puro
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        // Etiqueta fija que indica el rol del usuario
        JLabel lblRol = new JLabel("Cliente", SwingConstants.CENTER);
        
        // Tipografía Arial normal, tamaño pequeño de 12 puntos
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Color del texto del rol: un morado clarito/pastel para que contraste suavemente (RGB: 225, 205, 240)
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        // Espacio vacío para separar el bloque de usuario del separador
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // 3) Separador visual para marcar el cambio de sección
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(140, 100, 165));
        separador.setMaximumSize(new Dimension(190, 1));
        panel.add(separador);

        // Espacio vacío después de la línea divisoria
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 4) Opción de menú activa que indica la sección actual ("Mis Prendas")
        JLabel lblSeccion = new JLabel("  Mis Prendas", SwingConstants.LEFT);
        lblSeccion.setOpaque(true);
        
        // Este es el fondo de la opción seleccionada: BLANCO puro
        lblSeccion.setBackground(Color.WHITE);
        
        // Este es el color del texto de la opción seleccionada: MORADO OSCURO
        lblSeccion.setForeground(moradoOscuro);
        
        // Tipografía Arial en negrita, tamaño 13 puntos
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblSeccion.setMaximumSize(new Dimension(190, 38));
        lblSeccion.setPreferredSize(new Dimension(190, 38));

        panel.add(lblSeccion);

        // Espaciador flexible para empujar el pie de página hacia la parte inferior de la barra
        panel.add(Box.createVerticalGlue());

        // Pie de página institucional en la parte baja de la barra lateral
        JLabel lblFooter = new JLabel("TextilCare", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(200, 175, 220));
        lblFooter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblFooter);

        return panel;
    }

    /**
     * Carga la imagen del logo institucional, ajustando sus proporciones de manera uniforme para su visualización.
     * @return JLabel con la imagen redimensionada o vacío si no se encuentra.
     */
    private JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Ruta interna en el proyecto donde está guardada la imagen del logo
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");

        // Si la imagen existe, la cargamos y la ajustamos a 120x120 píxeles con buena calidad
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }

        return lblLogo;
    }

    /**
     * Agrupa y organiza el panel principal derecho que contiene el encabezado, la tabla y los botones de acción.
     * @return Panel de contenido montado.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Fondo de este panel: el lila muy claro general de la ventana
        panel.setBackground(fondoLila);
        
        // Margen interno alrededor de todo el contenido de la derecha (arriba, izquierda, abajo, derecha)
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        // Añade arriba el encabezado descriptivo
        panel.add(crearEncabezadoContenido(), BorderLayout.NORTH);
        
        // Añade en el centro la tabla con el listado de prendas del cliente
        panel.add(crearTabla(), BorderLayout.CENTER);
        
        // Añade abajo el panel con los botones de acción ("Ver Detalle" y "Salir")
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel del encabezado que incluye el título principal y un subtítulo descriptivo.
     * @return Panel de encabezado configurado.
     */
    private JPanel crearEncabezadoContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Título principal de la sección
        JLabel lblTitulo = new JLabel("Mis Prendas");
        
        // Tipografía Georgia, estilo Negrita (BOLD), tamaño grande de 26 puntos
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 26));
        
        // Color del texto del título: MORADO OSCURO
        lblTitulo.setForeground(moradoOscuro);
        lblTitulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        // Subtítulo explicativo para guiar al usuario
        JLabel lblSubtitulo = new JLabel("Consulta el estado de tus prendas en reparacion");
        
        // Tipografía Arial normal, tamaño 13 puntos
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        
        // Color del subtítulo: Gris suave
        lblSubtitulo.setForeground(grisTexto);
        lblSubtitulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        panel.add(lblTitulo);
        panel.add(lblSubtitulo);

        return panel;
    }

    /**
     * Configura la tabla interactiva de prendas, define las columnas como no editables, aplica estilos visuales y la envuelve en un JScrollPane.
     * @return JScrollPane con la tabla lista para usarse.
     */
    private JScrollPane crearTabla() {
        String[] columnas = {"Prenda", "Descripcion", "Fecha", "Estado"};

        // Creamos el modelo de la tabla sobrescribiendo el método para bloquear la edición directa de celdas
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Ninguna celda se puede modificar directamente
            }
        };

        // Construcción de la tabla visual con el modelo anterior
        tabla = new JTable(modeloTabla);
        
        // Altura de cada fila de la tabla (34 píxeles)
        tabla.setRowHeight(34);
        
        // Tipografía interna de las celdas de la tabla: Arial normal de 13 puntos
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        
        // Restricción de selección: Solo permite seleccionar una fila a la vez
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Color de fondo que toma la fila seleccionada (un lila muy suave)
        tabla.setSelectionBackground(new Color(228, 205, 245));
        
        // Color del texto al seleccionar una fila: MORADO OSCURO
        tabla.setSelectionForeground(moradoOscuro);
        
        // Color de las líneas divisorias de la cuadrícula de la tabla
        tabla.setGridColor(new Color(228, 218, 238));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(0, 1));

        // Estilos de la barra de encabezados de la tabla
        tabla.getTableHeader().setBackground(moradoOscuro); // Fondo de los títulos de columna: MORADO OSCURO
        tabla.getTableHeader().setForeground(Color.WHITE);  // Texto de los títulos de columna: BLANCO
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        // Envolvemos la tabla en un JScrollPane para que aparezca la barra de desplazamiento si hay muchos registros
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));

        return scroll;
    }

    /**
     * Crea el panel inferior que contiene los botones de interacción ("Ver Detalle" y "Salir").
     * @return Panel inferior de botones montado.
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Botón para ver la información detallada de la prenda seleccionada
        btnDetalle = new JButton("Ver Detalle");
        btnDetalle.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Este es el color de fondo del botón Ver Detalle: un morado/lila medio (RGB: 155, 89, 182)
        btnDetalle.setBackground(morado);
        
        // Color del texto: BLANCO
        btnDetalle.setForeground(Color.WHITE);
        btnDetalle.setFocusPainted(false);
        btnDetalle.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        btnDetalle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Botón para salir o cerrar sesión
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Este es el color de fondo del botón Salir: MORADO OSCURO
        btnSalir.setBackground(moradoOscuro);
        
        // Color del texto: BLANCO
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnDetalle);
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Agrega una nueva fila con los datos de una prenda a la tabla y registra su identificador asociado en la lista paralela.
     * @param idPrenda Identificador único de la base de datos.
     * @param tipo Tipo de prenda.
     * @param descripcion Descripción de la prenda o daño.
     * @param fecha Fecha de registro.
     * @param estado Estado actual del proceso.
     */
    public void agregarFila(int idPrenda, String tipo, String descripcion, String fecha, String estado) {
        modeloTabla.addRow(new Object[]{tipo, descripcion, fecha, estado});
        idsPrendas.add(idPrenda);
    }

    /**
     * Retorna el índice de la fila seleccionada actualmente por el usuario en la tabla.
     * @return Número de la fila seleccionada, o -1 si no hay ninguna seleccionada.
     */
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    /**
     * Retorna el identificador único de la prenda correspondiente al índice de la fila especificada.
     * @param indice Índice de la fila en la tabla.
     * @return ID numérico único de la prenda.
     */
    public int getIdPrenda(int indice) {
        return idsPrendas.get(indice);
    }

    // ── GETTERS PARA CONTROLADORES Y EVENTOS ──

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    /**
     * Método principal (main): Permite probar y ejecutar esta ventana de manera independiente.
     */
    public static void main(String[] args) {
        ClienteView vista = new ClienteView("Nombre Cliente");
        vista.setVisible(true);
    }
}