package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Component;

import java.net.URL;

/**
 * Vista de gestión de proveedores (ProveedorView) para TextilCare.
 * Permite visualizar el total de proveedores registrados, activos y nuevos,
 * así como la tabla completa con opciones para registrar nuevos proveedores y cambiar su estado.
 */
public class ProveedorView extends JFrame {

    // Componentes principales de la tabla y modelo de datos
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Tarjetas de resumen métrico superior
    private JLabel lblTotal, lblActivos, lblNuevos;
    
    // Botones de acción principal y navegación
    private JButton btnNuevoProveedor;
    private JButton btnCambiarEstado;
    private JButton btnPrendas, btnInventario, btnTecnicos;

    // Paleta de colores institucional compartida en el sistema
  private final Color morado = new Color(215, 155, 175);          
private final Color moradoOscuro = new Color(145, 75, 95);  
private final Color fondoLila = new Color(248, 240, 243);    

    /**
     * Constructor principal: Inicializa la interfaz de la ventana de proveedores.
     * @param nombreSupervisor Nombre del supervisor autenticado que aparece en el saludo.
     */
    public ProveedorView(String nombreSupervisor) {
        setTitle("TextilCare - Proveedores");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondoLila);

        add(crearBarraLateral(nombreSupervisor), BorderLayout.WEST);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Construye y configura el panel de la barra lateral izquierda (menú de navegación y perfil).
     * @param nombreSupervisor Nombre del supervisor.
     * @return JPanel configurado con la barra lateral.
     */
    private JPanel crearBarraLateral(String nombreSupervisor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(moradoOscuro);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 20, 25, 20));

        // ── LOGOTIPO INSTITUCIONAL ──
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        URL rutaImagen = getClass().getResource("/textilecare/recursos/logo.png");
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenEscalada));
        }
        panel.add(lblLogo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        // ── ETIQUETAS DE USUARIO Y ROL ──
        JLabel lblSaludo = new JLabel(nombreSupervisor, SwingConstants.CENTER);
        lblSaludo.setFont(new Font("Georgia", Font.BOLD, 17));
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblSaludo);

        JLabel lblRol = new JLabel("Supervisor", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(new Color(225, 205, 240));
        lblRol.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(lblRol);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // ── BOTONES DEL MENÚ DE NAVEGACIÓN ──
        btnPrendas = crearBotonMenu("Prendas", false);
        btnInventario = crearBotonMenu("Inventario", false);
        btnTecnicos = crearBotonMenu("Tecnicos", false);
        JButton btnProveedores = crearBotonMenu("Proveedores", true); // Sección actual activa

        panel.add(btnPrendas);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnInventario);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnTecnicos);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(btnProveedores);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Método auxiliar para crear botones del menú lateral con estilos consistentes según su estado activo.
     * @param texto Texto descriptivo del botón.
     * @param activo Indica si corresponde a la vista actual.
     * @return JButton configurado.
     */
    private JButton crearBotonMenu(String texto, boolean activo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(190, 38));

        if (activo) {
            boton.setBackground(Color.WHITE);
            boton.setForeground(moradoOscuro);
        } else {
            boton.setBackground(morado);
            boton.setForeground(Color.WHITE);
        }

        return boton;
    }

    /**
     * Construye el contenedor principal del área central (título, tarjetas de métricas, tabla y botones de acción).
     * @return JPanel con el contenido completo de la vista.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel titulo = new JLabel("Proveedores");
        titulo.setFont(new Font("Georgia", Font.BOLD, 24));
        titulo.setForeground(moradoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondoLila);
        panelSuperior.add(titulo, BorderLayout.NORTH);
        panelSuperior.add(crearTarjetas(), BorderLayout.CENTER);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel contenedor de las tarjetas métricas superiores.
     * @return JPanel con las tarjetas de resumen.
     */
    private JPanel crearTarjetas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(fondoLila);

        lblTotal = new JLabel("0");
        lblActivos = new JLabel("0");
        lblNuevos = new JLabel("0");

        panel.add(crearTarjeta("Registrados", lblTotal));
        panel.add(crearTarjeta("Activos", lblActivos));
        panel.add(crearTarjeta("Nuevos hoy", lblNuevos));

        return panel;
    }

    /**
     * Método auxiliar para construir una tarjeta métrica individual.
     * @param titulo Título descriptivo de la métrica.
     * @param lblNumero Etiqueta numérica que muestra el valor actual.
     * @return JPanel que representa la tarjeta visual.
     */
    private JPanel crearTarjeta(String titulo, JLabel lblNumero) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 212, 238)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        tarjeta.setPreferredSize(new Dimension(160, 70));

        lblNumero.setFont(new Font("Arial", Font.BOLD, 24));
        lblNumero.setForeground(moradoOscuro);
        lblNumero.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        tarjeta.add(lblNumero);
        tarjeta.add(lblTitulo);

        return tarjeta;
    }

    /**
     * Construye la tabla principal de proveedores y su contenedor scroll con renderizado de colores para el estado.
     * @return JScrollPane que envuelve la JTable.
     */
    private JScrollPane crearTabla() {
        String[] columnas = {"Nombre empresa", "NIT", "Telefono", "Correo", "Productos", "Estado", "Supervisor"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Evita la edición directa de celdas en la tabla
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setGridColor(new Color(228, 218, 238));

        tabla.getTableHeader().setBackground(moradoOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 36));

        // Asigna el renderizador personalizado para dar color a la columna de Estado (índice 5)
        tabla.getColumnModel().getColumn(5).setCellRenderer(new EstadoProveedorCellRenderer());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));

        return scroll;
    }

    /**
     * Construye el panel inferior que contiene los botones de acción para la gestión de proveedores.
     * @return JPanel con los botones de control.
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        panel.setBackground(fondoLila);

        btnNuevoProveedor = new JButton("+ Nuevo Proveedor");
        btnNuevoProveedor.setBackground(morado);
        btnNuevoProveedor.setForeground(Color.WHITE);
        btnNuevoProveedor.setFocusPainted(false);
        btnNuevoProveedor.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnNuevoProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.setBackground(moradoOscuro);
        btnCambiarEstado.setForeground(Color.WHITE);
        btnCambiarEstado.setFocusPainted(false);
        btnCambiarEstado.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnCambiarEstado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnCambiarEstado);
        panel.add(btnNuevoProveedor);

        return panel;
    }

    /**
     * Agrega una fila con los datos de un proveedor a la tabla principal.
     * @param nombreEmpresa Nombre o razón social de la empresa proveedora.
     * @param nit Número de NIT o identificación tributaria.
     * @param telefono Teléfono de contacto.
     * @param correo Correo electrónico.
     * @param productos Productos o insumos que suministra.
     * @param estado Estado actual del proveedor ("Activo" o "Inactivo").
     * @param supervisor Supervisor encargado de la relación con el proveedor.
     */
    public void agregarFila(String nombreEmpresa, String nit, String telefono, String correo, String productos, String estado, String supervisor) {
        modeloTabla.addRow(new Object[]{nombreEmpresa, nit, telefono, correo, productos, estado, supervisor});
    }

    /**
     * Limpia todas las filas de la tabla de proveedores.
     */
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Actualiza los valores numéricos mostrados en las tarjetas superiores de resumen.
     * @param total Total de proveedores registrados.
     * @param activos Proveedores en estado activo.
     * @param nuevos Proveedores nuevos registrados hoy.
     */
    public void actualizarTarjetas(int total, int activos, int nuevos) {
        lblTotal.setText(String.valueOf(total));
        lblActivos.setText(String.valueOf(activos));
        lblNuevos.setText(String.valueOf(nuevos));
    }

    /**
     * Obtiene el índice de la fila seleccionada actualmente en la tabla.
     * @return Índice de la fila seleccionada, o -1 si no hay selección.
     */
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow();
    }

    /**
     * Obtiene el nombre de la empresa proveedora correspondiente a una fila específica.
     * @param fila Índice de la fila en la tabla.
     * @return Nombre de la empresa en formato String.
     */
    public String getNombreEmpresaEnFila(int fila) {
        return (String) modeloTabla.getValueAt(fila, 0);
    }

    /**
     * Obtiene el estado actual del proveedor en una fila específica.
     * @param fila Índice de la fila en la tabla.
     * @return Estado en formato String.
     */
    public String getEstadoEnFila(int fila) {
        return (String) modeloTabla.getValueAt(fila, 5);
    }

    /**
     * Muestra un cuadro de diálogo emergente con un mensaje de advertencia o aviso.
     * @param mensaje Mensaje a desplegar.
     */
    public void mostrarAviso(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación (Sí/No).
     * @param mensaje Pregunta o acción a confirmar.
     * @return true si el usuario selecciona Sí, false en caso contrario.
     */
    public boolean confirmar(String mensaje) {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        return opcion == javax.swing.JOptionPane.YES_OPTION;
    }

    // ── GETTERS QUE UTILIZA EL CONTROLADOR ──

    public JTable getTabla() {
        return tabla;
    }

    public JButton getBtnNuevoProveedor() {
        return btnNuevoProveedor;
    }

    public JButton getBtnCambiarEstado() {
        return btnCambiarEstado;
    }

    public JButton getBtnPrendas() {
        return btnPrendas;
    }

    public JButton getBtnInventario() {
        return btnInventario;
    }

    public JButton getBtnTecnicos() {
        return btnTecnicos;
    }

    /**
     * Método principal (main): Permite probar y visualizar la ventana de manera independiente.
     */
    public static void main(String[] args) {
        ProveedorView vista = new ProveedorView("Alejandro");
        vista.agregarFila("Textiles del Valle", "900123456", "3001234567", "contacto@tdv.com", "Hilos, telas", "Activo", "Alejandro Diaz");
        vista.agregarFila("Insumos TextilCol", "900987654", "3109876543", "ventas@textilcol.com", "Botones, cierres", "Inactivo", "Alejandro Diaz");
        vista.actualizarTarjetas(2, 1, 0);
        vista.setVisible(true);
    }
}

/**
 * Renderizador de celdas personalizado para otorgar colores distintivos a la columna de Estado según el estado del proveedor.
 */
class EstadoProveedorCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean foco, int fila, int columna) {

        JLabel etiqueta = new JLabel(valor != null ? valor.toString() : "", SwingConstants.CENTER);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 12));

        String estado = valor != null ? valor.toString() : "";

        if (estado.equalsIgnoreCase("Activo")) {
            etiqueta.setBackground(new Color(200, 240, 220)); // Verde suave para activo
            etiqueta.setForeground(new Color(15, 110, 86));
        } else {
            etiqueta.setBackground(new Color(250, 220, 220)); // Rojo suave para inactivo
            etiqueta.setForeground(new Color(160, 40, 40));
        }

        return etiqueta;
    }
}