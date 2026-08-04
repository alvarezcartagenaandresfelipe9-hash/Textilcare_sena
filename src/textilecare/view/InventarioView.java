package textilecare.view;

// Importaciones de Swing para la construcción de componentes gráficos e interfaces de usuario
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

// Importaciones de AWT para la gestión de contenedores, diseño, fuentes, colores e imágenes
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Cursor;

import java.net.URL;
import java.util.List;

/**
 * Vista principal de gestión del inventario (InventarioView) para TextilCare.
 * Permite registrar nuevos productos, ingresar existencias a productos actuales y visualizar la tabla de stock con alertas por colores.
 */
public class InventarioView extends JFrame {

    // Componentes del formulario de registro de nuevos productos
    private JTextField txtNombre;
    private JComboBox<String> cmbCategoria;
    private JTextField txtCantidad;
    private JComboBox<String> cmbUnidad;
    private JComboBox<String> cmbProveedor;
    private JTextField txtPrecio;
    private JButton btnGuardarProducto;

    // Componentes del formulario de ingreso de stock a productos existentes
    private JComboBox<String> cmbProductoExistente;
    private JTextField txtCantidadIngreso;
    private JButton btnIngresarCantidad;

    // Componentes de la tabla de inventario
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Botones de navegación de la barra lateral
    private JButton btnPrendas, btnTecnicos, btnProveedores;

    // Paleta de colores institucional para mantener coherencia visual
   private final Color morado = new Color(215, 155, 175);       
private final Color moradoOscuro = new Color(145, 75, 95);    
private final Color fondoLila = new Color(248, 240, 243);     

    /**
     * Constructor principal: Inicializa la ventana de inventario, su diseño general, barra lateral y contenido central.
     * @param nombreSupervisor Nombre del supervisor autenticado que aparece en el saludo del menú.
     */
    public InventarioView(String nombreSupervisor) {
        setTitle("TextilCare - Inventario");
        setSize(1100, 700);
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
        JButton btnInventario = crearBotonMenu("Inventario", true); // Sección actual activa
        btnTecnicos = crearBotonMenu("Tecnicos", false);
        btnProveedores = crearBotonMenu("Proveedores", false);

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
     * Construye el contenedor principal del área central (formularios superiores y tabla inferior).
     * @return JPanel con el contenido organizativo de la vista.
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(fondoLila);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        JPanel panelFormularios = new JPanel();
        panelFormularios.setLayout(new BoxLayout(panelFormularios, BoxLayout.X_AXIS));
        panelFormularios.setBackground(fondoLila);

        panelFormularios.add(crearFormularioNuevoProducto());
        panelFormularios.add(Box.createRigidArea(new Dimension(15, 0)));
        panelFormularios.add(crearFormularioIngresoCantidad());

        panel.add(panelFormularios, BorderLayout.NORTH);
        panel.add(crearTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Construye la tarjeta de formulario para el registro de nuevos productos en el inventario.
     * @return JPanel con los campos de texto, combos y diseño de la tarjeta.
     */
    private JPanel crearFormularioNuevoProducto() {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(222, 212, 238), 1));
        card.setPreferredSize(new Dimension(400, 340));

        JLabel titulo = new JLabel("Registrar nuevo producto");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setBounds(15, 12, 350, 22);
        card.add(titulo);

        card.add(crearEtiqueta("Nombre del producto:", 15, 45));
        txtNombre = new JTextField();
        txtNombre.setBounds(15, 65, 365, 30);
        card.add(txtNombre);

        card.add(crearEtiqueta("Categoria:", 15, 105));
        cmbCategoria = new JComboBox<>();
        cmbCategoria.addItem("Hilos");
        cmbCategoria.addItem("Telas");
        cmbCategoria.addItem("Botones");
        cmbCategoria.addItem("Cierres");
        cmbCategoria.addItem("Agujas");
        cmbCategoria.setBounds(15, 125, 365, 30);
        card.add(cmbCategoria);

        card.add(crearEtiqueta("Cantidad inicial:", 15, 165));
        txtCantidad = new JTextField();
        txtCantidad.setBounds(15, 185, 175, 30);
        card.add(txtCantidad);

        card.add(crearEtiqueta("Unidad:", 200, 165));
        cmbUnidad = new JComboBox<>();
        cmbUnidad.addItem("Unidades");
        cmbUnidad.addItem("Metros");
        cmbUnidad.addItem("Rollos");
        cmbUnidad.setBounds(200, 185, 180, 30);
        card.add(cmbUnidad);

        card.add(crearEtiqueta("Proveedor:", 15, 225));
        cmbProveedor = new JComboBox<>();
        cmbProveedor.setBounds(15, 245, 365, 30);
        card.add(cmbProveedor);

        card.add(crearEtiqueta("Precio unitario:", 15, 280));
        txtPrecio = new JTextField();
        txtPrecio.setBounds(15, 300, 365, 30);
        card.add(txtPrecio);

        return card;
    }

    /**
     * Construye la tarjeta de formulario para ingresar stock adicional a un producto ya existente.
     * @return JPanel con los selectores y botones de acción rápida.
     */
    private JPanel crearFormularioIngresoCantidad() {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(222, 212, 238), 1));
        card.setPreferredSize(new Dimension(420, 340));

        JLabel titulo = new JLabel("Ingresar cantidad a producto existente");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setBounds(15, 12, 390, 22);
        card.add(titulo);

        card.add(crearEtiqueta("Seleccionar producto:", 15, 45));
        cmbProductoExistente = new JComboBox<>();
        cmbProductoExistente.setBounds(15, 65, 390, 30);
        card.add(cmbProductoExistente);

        card.add(crearEtiqueta("Cantidad a ingresar:", 15, 105));
        txtCantidadIngreso = new JTextField();
        txtCantidadIngreso.setBounds(15, 125, 390, 30);
        card.add(txtCantidadIngreso);

        btnIngresarCantidad = new JButton("Ingresar al inventario");
        btnIngresarCantidad.setBounds(15, 175, 390, 32);
        btnIngresarCantidad.setBackground(Color.WHITE);
        btnIngresarCantidad.setForeground(morado);
        btnIngresarCantidad.setFont(new Font("Arial", Font.BOLD, 12));
        btnIngresarCantidad.setFocusPainted(false);
        btnIngresarCantidad.setBorder(new LineBorder(morado, 1));
        btnIngresarCantidad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.add(btnIngresarCantidad);

        btnGuardarProducto = new JButton("Guardar producto");
        btnGuardarProducto.setBounds(15, 260, 390, 32);
        btnGuardarProducto.setBackground(morado);
        btnGuardarProducto.setForeground(Color.WHITE);
        btnGuardarProducto.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardarProducto.setFocusPainted(false);
        btnGuardarProducto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.add(btnGuardarProducto);

        return card;
    }

    /**
     * Método auxiliar para generar etiquetas de texto uniformes dentro de los formularios.
     * @param texto Texto de la etiqueta.
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @return JLabel configurado.
     */
    private JLabel crearEtiqueta(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setBounds(x, y, 250, 18);
        return lbl;
    }

    /**
     * Construye la tabla de inventario y su contenedor scroll con renderizado de colores para el estado del stock.
     * @return JScrollPane que envuelve la JTable.
     */
    private JScrollPane crearTabla() {
        String[] columnas = {"Producto", "Categoria", "Stock", "Unidad", "Proveedor", "Precio unit.", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Celdas no editables directamente
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

        // Asigna el renderizador personalizado para dar color a la columna de Estado
        tabla.getColumnModel().getColumn(6).setCellRenderer(new EstadoStockCellRenderer());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(228, 218, 238), 1));
        scroll.setPreferredSize(new Dimension(0, 250));

        return scroll;
    }

    /**
     * Método de compatibilidad para la lista de productos (no utilizado directamente, reemplazado por agregarFilaProducto).
     * @param filas Lista de cadenas.
     */
    public void mostrarProductos(List<String> filas) {
        // No se usa, se reemplaza por agregarFilaProducto
    }

    /**
     * Agrega una fila con los datos de un producto a la tabla de inventario.
     * @param nombre Nombre del producto.
     * @param categoria Categoría del insumo.
     * @param stock Cantidad actual en inventario.
     * @param unidad Unidad de medida.
     * @param proveedor Proveedor asociado.
     * @param precio Precio unitario.
     * @param estado Estado del stock ("Disponible", "Stock bajo", etc.).
     */
    public void agregarFilaProducto(String nombre, String categoria, int stock, String unidad, String proveedor, int precio, String estado) {
        modeloTabla.addRow(new Object[]{nombre, categoria, stock, unidad, proveedor, "$" + precio, estado});
    }

    /**
     * Limpia todas las filas de la tabla de inventario.
     */
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Carga el listado de proveedores en el ComboBox correspondiente.
     * @param nombres Lista con los nombres de los proveedores.
     */
    public void cargarProveedores(List<String> nombres) {
        cmbProveedor.removeAllItems();
        for (String nombre : nombres) {
            cmbProveedor.addItem(nombre);
        }
    }

    /**
     * Carga el listado de productos existentes en el ComboBox de ingresos.
     * @param nombres Lista con los nombres de los productos.
     */
    public void cargarProductosExistentes(List<String> nombres) {
        cmbProductoExistente.removeAllItems();
        for (String nombre : nombres) {
            cmbProductoExistente.addItem(nombre);
        }
    }

    /**
     * Restablece los campos del formulario de registro de nuevos productos.
     */
    public void limpiarFormularioNuevoProducto() {
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        cmbCategoria.setSelectedIndex(0);
        cmbUnidad.setSelectedIndex(0);
        if (cmbProveedor.getItemCount() > 0) {
            cmbProveedor.setSelectedIndex(0);
        }
    }

    /**
     * Despliega un cuadro de diálogo emergente para reportar un error.
     * @param mensaje Mensaje descriptivo del error.
     */
    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Despliega un cuadro de diálogo emergente para confirmar una acción exitosa.
     * @param mensaje Mensaje de éxito.
     */
    public void mostrarExito(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Exito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS PARA CONTROLADORES Y EVENTOS ──

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JComboBox<String> getCmbCategoria() {
        return cmbCategoria;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JComboBox<String> getCmbUnidad() {
        return cmbUnidad;
    }

    public JComboBox<String> getCmbProveedor() {
        return cmbProveedor;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnGuardarProducto() {
        return btnGuardarProducto;
    }

    public JComboBox<String> getCmbProductoExistente() {
        return cmbProductoExistente;
    }

    public JTextField getTxtCantidadIngreso() {
        return txtCantidadIngreso;
    }

    public JButton getBtnIngresarCantidad() {
        return btnIngresarCantidad;
    }

    public JButton getBtnPrendas() {
        return btnPrendas;
    }

    public JButton getBtnTecnicos() {
        return btnTecnicos;
    }

    public JButton getBtnProveedores() {
        return btnProveedores;
    }

    /**
     * Método principal (main): Permite probar y ejecutar esta ventana de manera independiente.
     */
    public static void main(String[] args) {
        InventarioView vista = new InventarioView("Alejandro");
        vista.agregarFilaProducto("Hilo negro", "Hilos", 50, "Unidades", "Textiles del Valle", 2000, "Disponible");
        vista.setVisible(true);
    }
}

/**
 * Renderizador de celdas personalizado para otorgar colores distintivos a la columna de Estado según el nivel de stock.
 */
class EstadoStockCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor,
            boolean seleccionado, boolean foco, int fila, int columna) {

        JLabel etiqueta = new JLabel(valor.toString(), SwingConstants.CENTER);
        etiqueta.setOpaque(true);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 12));

        switch (valor.toString()) {
            case "Disponible":
                etiqueta.setBackground(new Color(200, 240, 220)); // Verde suave
                etiqueta.setForeground(new Color(15, 110, 86));
                break;
            case "Stock bajo":
                etiqueta.setBackground(new Color(255, 235, 190)); // Amarillo / Naranja pastel
                etiqueta.setForeground(new Color(133, 79, 11));
                break;
            default:
                etiqueta.setBackground(new Color(250, 220, 220)); // Rojo suave para crítico o agotado
                etiqueta.setForeground(new Color(160, 40, 40));
        }

        return etiqueta;
    }
}