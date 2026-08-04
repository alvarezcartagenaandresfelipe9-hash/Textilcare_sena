package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores, dimensiones e interactividad
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;
import java.awt.Cursor;

// Importa la clase URL para localizar recursos multimedia dentro del proyecto
import java.net.URL;

/**
 * Vista del panel de Administración (AdministradorView) para TextilCare.
 */
public class AdministradorView extends JFrame {

    // Declaración de los componentes de la interfaz de la tabla y controles del panel
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JButton btnClientes;
    private JButton btnSupervisores;
    private JButton btnTecnicos;
    private JButton btnVendedores;
    private JButton btnRegistrar;
    private JTextField txtBuscar;
    private JLabel lblTitulo;

    // Paleta de colores morada, igual a la usada en LoginView, para que las pantallas se vean coherentes
    private Color morado = new Color(155, 89, 182);         // Color de acento para los botones inactivos del menú lateral
    private Color moradoOscuro = new Color(88, 24, 130);    // Color sólido y fuerte para la barra lateral y los encabezados de la tabla
    private Color fondo = new Color(243, 237, 250);         // Color de fondo general de toda la pantalla (un lila muy clarito)

    /**
     * Constructor principal: Inicializa la ventana del Administrador, su tamaño, colores y componentes principales.
     * @param nombreAdmin Nombre del usuario administrador que se mostrará saludando en la barra lateral.
     */
    public AdministradorView(String nombreAdmin) {
        // Título que aparece en la barra superior de la ventana de Windows
        setTitle("TextilCare - Administrador");
        
        // Dimensiones totales de la ventana: 1100 de ancho por 650 de alto
        setSize(1100, 650);
        
        // Esto hace que la ventana aparezca justo en el centro de la pantalla del monitor
        setLocationRelativeTo(null);
        
        // Acción al cerrar la ventana: Solo se cierra esta ventana (DISPOSE) sin apagar el sistema completo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Organiza el diseño general de la ventana dividiendo el espacio en zonas (BorderLayout)
        setLayout(new BorderLayout());
        
        // Este es el color de fondo general de la pantalla: un lila muy claro (RGB: 243, 237, 250)
        getContentPane().setBackground(fondo);

        // Agregamos la barra lateral izquierda pasándole el nombre del administrador
        add(crearBarraLateral(nombreAdmin), BorderLayout.WEST);
        
        // Agregamos el contenido principal (la tabla y el buscador) en el centro de la pantalla
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Crea y configura el panel de la barra lateral izquierda con el logo, la información del usuario y los botones de roles.
     * @param nombreAdmin Nombre que aparecerá en el saludo.
     * @return Panel lateral izquierdo montado.
     */
    private JPanel crearBarraLateral(String nombreAdmin) {
        JPanel panel = new JPanel();
        
        // Organiza los elementos internos de arriba hacia abajo en forma de columna vertical (BoxLayout)
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Este es el color de fondo de toda la barra lateral: un morado oscuro / morado uva (RGB: 88, 24, 130)
        panel.setBackground(moradoOscuro);
        
        // Fija un ancho exacto de 220 píxeles para esta barra lateral
        panel.setPreferredSize(new Dimension(220, 0));
        
        // Margen interno o espacio alrededor del contenido de la barra lateral (arriba, izquierda, abajo, derecha)
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        // Etiqueta contenedora para el logotipo institucional
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        // Busca de forma segura la imagen del logo en los recursos del proyecto
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            // Redimensiona la imagen del logo a 110x110 píxeles con alta calidad visual
            Image imagenEscalada = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }
        panel.add(lblLogo);

        // Espacio vacío rígido para separar visualmente el logo del texto de bienvenida
        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Etiqueta con el nombre del administrador que inició sesión
        JLabel lblSaludo = new JLabel(nombreAdmin, SwingConstants.CENTER);
        
        // Tipografía Georgia, estilo Negrita (BOLD), tamaño 17 puntos
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        
        // Color del texto del nombre: BLANCO puro
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        // Etiqueta fija que indica el cargo del usuario
        JLabel lblRol = new JLabel("Administrador", SwingConstants.CENTER);
        
        // Tipografía Arial normal, tamaño pequeño de 12 puntos
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Color del texto del rol: un morado clarito/pastel para que contraste suavemente (RGB: 225, 205, 240)
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        // Espacio vacío para separar el bloque de usuario de los botones del menú
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Creación de los botones de navegación del menú lateral (por defecto, "Clientes" inicia activo)
        btnClientes = crearBotonMenu("Clientes", true);
        btnSupervisores = crearBotonMenu("Supervisores", false);
        btnTecnicos = crearBotonMenu("Tecnicos", false);
        btnVendedores = crearBotonMenu("Vendedores", false);

        // Añadimos los botones al menú con un pequeño espacio vertical de separación entre cada uno
        panel.add(btnClientes);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnSupervisores);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnTecnicos);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnVendedores);

        // Empuja todo lo anterior hacia arriba, dejando un espacio libre flexible en la parte de abajo de la barra
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Método auxiliar para crear y estilizar los botones del menú lateral según si están seleccionados o inactivos.
     * @param texto Texto que mostrará el botón (ej. "Clientes", "Técnicos").
     * @param activo Booleano que indica si el botón está seleccionado actualmente (true = blanco, false = morado).
     * @return El botón ya configurado.
     */
    private JButton crearBotonMenu(String texto, boolean activo) {
        JButton boton = new JButton(texto);
        
        // Tipografía Arial en negrita, tamaño 13 puntos
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Quita el borde de selección feo que pone Java por defecto alrededor del texto al hacer clic
        boton.setFocusPainted(false);
        
        // Hace que el cursor del mouse cambie a la manita de enlace al pasar por encima
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        // Tamaño fijo máximo para los botones del menú (190 de ancho por 38 de alto)
        boton.setMaximumSize(new Dimension(190, 38));

        // Condicional visual de colores: Si el botón está activo se pinta de blanco con letras moradas; si no, de morado con letras blancas
        if (activo) {
            // Este es el color de fondo del botón seleccionado: BLANCO puro
            boton.setBackground(Color.WHITE);
            // Este es el color de las letras del botón seleccionado: MORADO OSCURO
            boton.setForeground(moradoOscuro);
        } else {
            // Este es el color de fondo de los botones inactivos: un tono morado/lila medio (RGB: 155, 89, 182)
            boton.setBackground(morado);
            // Este es el color de las letras de los botones inactivos: BLANCO
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

    /**
     * Agrupa y organiza el panel de contenido principal de la derecha que incluye el encabezado superior y la tabla central.
     * @return Panel de contenido montado.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Fondo de este panel: el lila muy claro general de la ventana
        panel.setBackground(fondo);
        
        // Margen interno alrededor de todo el contenido de la derecha (arriba, izquierda, abajo, derecha)
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));

        // Añade arriba el encabezado (título, buscador y botón de registrar)
        panel.add(crearEncabezado(), BorderLayout.NORTH);
        
        // Añade en el centro la tabla donde se muestran los registros de usuarios
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel superior que contiene el título dinámico, la barra de búsqueda y el botón de registro de nuevos usuarios.
     * @return Panel de encabezado configurado.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondo);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Título principal que cambia dinámicamente según la sección seleccionada (ej. "Clientes Registrados")
        lblTitulo = new JLabel("Clientes Registrados");
        
        // Tipografía Georgia, estilo Negrita (BOLD), tamaño grande de 24 puntos
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        
        // Color del texto del título: MORADO OSCURO
        lblTitulo.setForeground(moradoOscuro);

        // Sub-panel alineado a la derecha para agrupar el buscador y el botón de agregar
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setBackground(fondo);

        // Botón para registrar un nuevo usuario en el sistema
        btnRegistrar = new JButton("+ Registrar");
        
        // Tipografía Arial en negrita, tamaño 13 puntos
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Este es el color de fondo del botón registrar: MORADO medio
        btnRegistrar.setBackground(morado);
        
        // Color del texto de la palabra "+ Registrar": BLANCO
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        
        // Margen interno del botón para darle un aspecto más ancho y cómodo
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Texto guía "Buscar:" al lado de la caja de texto
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 13));

        // Caja de texto interactiva para filtrar registros buscando por nombre o documento
        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(220, 32));

        // Agregamos los componentes al panel de acciones derecho
        panelAcciones.add(lblBuscar);
        panelAcciones.add(txtBuscar);
        panelAcciones.add(btnRegistrar);

        // Ubicamos el título a la izquierda y el panel de acciones a la derecha del encabezado
        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelAcciones, BorderLayout.EAST);

        return panel;
    }

    /**
     * Configura la tabla de datos, define las columnas como no editables, aplica estilos visuales y la envuelve en un scroll.
     * @return JScrollPane con la tabla lista para usarse.
     */
    private JScrollPane crearTabla() {
        // Creamos el modelo de la tabla sobrescribiendo el método para bloquear la edición directa de celdas con doble clic
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Ninguna celda se puede modificar directamente escribiendo en ella
            }
        };
        
        // Definición de las columnas que contendrá la tabla
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Estado");

        // Construcción de la tabla visual con el modelo anterior
        tabla = new JTable(modeloTabla);
        
        // Altura de cada fila de la tabla (36 píxeles para que se vea amplia y moderna)
        tabla.setRowHeight(36);
        
        // Tipografía interna de las celdas de la tabla: Arial normal de 14 puntos
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Color de fondo que toma una fila al hacerle clic o seleccionarla (un lila muy suave)
        tabla.setSelectionBackground(new Color(228, 205, 245));
        
        // Color de las líneas divisorias de la cuadrícula de la tabla
        tabla.setGridColor(new Color(228, 218, 238));

        // Estilos de la barra de encabezados de la tabla
        tabla.getTableHeader().setBackground(moradoOscuro); // Fondo de los títulos de columna: MORADO OSCURO
        tabla.getTableHeader().setForeground(Color.WHITE);  // Texto de los títulos de columna: BLANCO
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));

        // Asignamos un renderizador personalizado a la columna de "Estado" (columna 4) para darle colores de etiqueta
        tabla.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRendererAdmin());

        // Envolvemos la tabla en un JScrollPane para que aparezca la barra de desplazamiento si hay muchos usuarios
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));

        return scroll;
    }

    /**
     * Cambia el título grande del encabezado y limpia por completo la tabla para mostrar los registros de otro rol.
     * @param titulo Nuevo título que se mostrará (ej. "Técnicos Registrados").
     */
    public void configurarTabla(String titulo) {
        lblTitulo.setText(titulo);
        modeloTabla.setRowCount(0); // Borra todos los datos actuales de la tabla
    }

    /**
     * Agrega una nueva fila con la información detallada de un usuario a la tabla visual.
     * @param nombre Nombre del usuario.
     * @param documento Número de identificación o documento.
     * @param correo Correo electrónico.
     * @param telefono Número de teléfono o contacto.
     * @param estado Estado actual ("Activo" o "Inactivo").
     */
    public void agregarFila(String nombre, String documento, String correo, String telefono, String estado) {
        modeloTabla.addRow(new Object[]{nombre, documento, correo, telefono, estado});
    }

    // ── GETTERS PARA CONTROLADORES Y EVENTOS ──

    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnSupervisores() {
        return btnSupervisores;
    }

    public JButton getBtnTecnicos() {
        return btnTecnicos;
    }

    public JButton getBtnVendedores() {
        return btnVendedores;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Método principal (main): Permite probar y ejecutar esta ventana de manera independiente.
     */
    public static void main(String[] args) {
        AdministradorView vista = new AdministradorView("Admin");
        vista.setVisible(true);
    }
}

/**
 * Clase personalizada auxiliar para darle formato visual de etiqueta con colores (verde si está Activo, rojo si está Inactivo)
 * a la columna de Estado dentro de la tabla del administrador.
 */
class EstadoCellRendererAdmin extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean foco, int fila, int columna) {

        JLabel etiqueta = new JLabel(valor.toString(), SwingConstants.CENTER);
        etiqueta.setOpaque(true); // Permite que se pinte el color de fondo de la celda
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));

        // Condicional de colores según el estado del usuario
        if (valor.toString().equals("Activo")) {
            // Fondo verde claro muy suave y texto verde oscuro para indicar estado activo
            etiqueta.setBackground(new Color(200, 240, 220));
            etiqueta.setForeground(new Color(15, 110, 86));
        } else {
            // Fondo rojo claro muy suave y texto rojo oscuro para indicar estado inactivo
            etiqueta.setBackground(new Color(255, 220, 220));
            etiqueta.setForeground(new Color(150, 30, 30));
        }

        return etiqueta;
    }
}