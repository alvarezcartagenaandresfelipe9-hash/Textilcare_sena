package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

// Importaciones de AWT para la gestión de fuentes, colores e imágenes
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

// Importaciones para el manejo de archivos, lectura de imágenes 
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class FormularioReparacionView extends JFrame {

    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbDiaInicio, cmbMesInicio, cmbAnioInicio;
    private JComboBox<String> cmbDiaFin, cmbMesFin, cmbAnioFin;
    private JComboBox<String> cmbProducto;
    private JTextField txtCantidad;
    private JTable tablaMateriales;
    private DefaultTableModel modeloMateriales;

    private JLabel lblPreview1, lblPreview2, lblPreview3;
    private byte[] foto1, foto2, foto3;

    private JButton btnAgregarMaterial;
    private JButton btnEliminarMaterial;
    private JButton btnGuardar;
    private JButton btnCancelar;

    // Paleta de colores institucional y arreglo de meses para la gestión de fechas
    private final Color cafe = new Color(181, 137, 103);
    private final Color fondo = new Color(238, 232, 224);
    private final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    // Constructor: Inicializa la ventana de actualización de reparación, sus dimensiones, colores y módulos visuales
    public FormularioReparacionView(String tipoPrenda) {
        setTitle("Actualizar prenda - " + tipoPrenda);
        setSize(620, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(fondo);

        construirEncabezado(tipoPrenda);
        construirEstado();
        construirFechas();
        construirFotos();
        construirMateriales();
        construirBotonesFinales();
    }

    // Configura y posiciona el título principal de la ventana con el tipo de prenda a actualizar
    private void construirEncabezado(String tipoPrenda) {
        JLabel titulo = new JLabel("Actualizar: " + tipoPrenda);
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        titulo.setForeground(new Color(100, 70, 40));
        titulo.setBounds(20, 15, 400, 30);
        add(titulo);
    }

    // Construye el selector desplegable para modificar el estado del proceso de la prenda
    private void construirEstado() {
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 55, 150, 25);
        add(lblEstado);

        cmbEstado = new JComboBox<>();
        cmbEstado.addItem("-- Seleccione --");
        cmbEstado.addItem("Pendiente");
        cmbEstado.addItem("En proceso");
        cmbEstado.addItem("Reparada");
        cmbEstado.setBounds(20, 80, 560, 30);
        add(cmbEstado);
    }

    // Construye los selectores de fecha de inicio y de fecha de fin (días, meses y años)
    private void construirFechas() {
        JLabel lblFechaInicio = new JLabel("Fecha de inicio:");
        lblFechaInicio.setBounds(20, 120, 150, 25);
        add(lblFechaInicio);

        cmbDiaInicio = crearComboDias();
        cmbDiaInicio.setBounds(20, 145, 80, 30);
        add(cmbDiaInicio);

        cmbMesInicio = crearComboMeses();
        cmbMesInicio.setBounds(110, 145, 130, 30);
        add(cmbMesInicio);

        cmbAnioInicio = crearComboAnios();
        cmbAnioInicio.setBounds(250, 145, 100, 30);
        add(cmbAnioInicio);

        JLabel lblFechaFin = new JLabel("Fecha de fin:");
        lblFechaFin.setBounds(20, 185, 150, 25);
        add(lblFechaFin);

        cmbDiaFin = crearComboDias();
        cmbDiaFin.setBounds(20, 210, 80, 30);
        add(cmbDiaFin);

        cmbMesFin = crearComboMeses();
        cmbMesFin.setBounds(110, 210, 130, 30);
        add(cmbMesFin);

        cmbAnioFin = crearComboAnios();
        cmbAnioFin.setBounds(250, 210, 100, 30);
        add(cmbAnioFin);
    }

    // Método auxiliar para rellenar un ComboBox con los días del 1 al 31
    private JComboBox<String> crearComboDias() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Dia");
        for (int i = 1; i <= 31; i++) {
            combo.addItem(String.valueOf(i));
        }
        return combo;
    }

    // Método auxiliar para rellenar un ComboBox con los meses del año
    private JComboBox<String> crearComboMeses() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Mes");
        for (String mes : meses) {
            combo.addItem(mes);
        }
        return combo;
    }

    // Método auxiliar para rellenar un ComboBox con un rango de años habilitados
    private JComboBox<String> crearComboAnios() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Año");
        for (int i = 2024; i <= 2030; i++) {
            combo.addItem(String.valueOf(i));
        }
        return combo;
    }

    // Construye las secciones de vista previa y botones para cargar hasta 3 fotografías de la reparación
    private void construirFotos() {
        JLabel lblFotos = new JLabel("Fotos:");
        lblFotos.setFont(new Font("Serif", Font.BOLD, 14));
        lblFotos.setBounds(20, 255, 100, 25);
        add(lblFotos);

        lblPreview1 = crearPreviewFoto(20);
        JButton btnFoto1 = crearBotonFoto("Foto 1", 20, lblPreview1, 1);
        add(lblPreview1);
        add(btnFoto1);

        lblPreview2 = crearPreviewFoto(145);
        JButton btnFoto2 = crearBotonFoto("Foto 2", 145, lblPreview2, 2);
        add(lblPreview2);
        add(btnFoto2);

        lblPreview3 = crearPreviewFoto(270);
        JButton btnFoto3 = crearBotonFoto("Foto 3", 270, lblPreview3, 3);
        add(lblPreview3);
        add(btnFoto3);

        JLabel lblNota = new JLabel("<html><i>Pendiente=1 foto, En proceso=2 fotos, Reparada=3 fotos</i></html>");
        lblNota.setBounds(20, 435, 560, 20);
        lblNota.setForeground(Color.GRAY);
        add(lblNota);
    }

    // Crea un componente etiqueta estilizado para mostrar la vista previa de una imagen cargada
    private JLabel crearPreviewFoto(int x) {
        JLabel lbl = new JLabel("Sin foto");
        lbl.setBounds(x, 285, 110, 110);
        lbl.setBorder(new LineBorder(new Color(200, 190, 180)));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBackground(Color.WHITE);
        lbl.setOpaque(true);
        return lbl;
    }

    // Crea un botón vinculado a una ranura de fotografía específica para disparar el selector de archivos
    private JButton crearBotonFoto(String texto, int x, JLabel preview, int numeroFoto) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, 400, 110, 28);
        btn.setBackground(cafe);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> seleccionarFoto(preview, numeroFoto));
        return btn;
    }

    // Abre el selector de archivos, renderiza la vista previa de la imagen y almacena su representación en bytes
    private void seleccionarFoto(JLabel preview, int numeroFoto) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar foto");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes (jpg, png)", "jpg", "jpeg", "png"));

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            File archivo = fileChooser.getSelectedFile();
            BufferedImage imagen = ImageIO.read(archivo);

            ImageIcon icono = new ImageIcon(imagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            preview.setIcon(icono);
            preview.setText("");

            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            ImageIO.write(imagen, "jpg", salida);
            byte[] bytesFoto = salida.toByteArray();

            if (numeroFoto == 1) {
                foto1 = bytesFoto;
            } else if (numeroFoto == 2) {
                foto2 = bytesFoto;
            } else {
                foto3 = bytesFoto;
            }

        } catch (Exception ex) {
            mostrarError("Error al cargar imagen: " + ex.getMessage());
        }
    }

    // Construye la sección de gestión de materiales usados (producto, cantidad, tabla y botones de control)
    private void construirMateriales() {
        JLabel lblMateriales = new JLabel("Materiales usados (solo si esta Reparada):");
        lblMateriales.setFont(new Font("Serif", Font.BOLD, 13));
        lblMateriales.setBounds(20, 460, 400, 25);
        add(lblMateriales);

        cmbProducto = new JComboBox<>();
        cmbProducto.setBounds(20, 490, 270, 30);
        add(cmbProducto);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(300, 490, 90, 30);
        add(txtCantidad);

        btnAgregarMaterial = new JButton("+ Agregar");
        btnAgregarMaterial.setBounds(400, 490, 130, 30);
        btnAgregarMaterial.setBackground(cafe);
        btnAgregarMaterial.setForeground(Color.WHITE);
        btnAgregarMaterial.setFocusPainted(false);
        add(btnAgregarMaterial);

        modeloMateriales = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloMateriales.addColumn("Producto");
        modeloMateriales.addColumn("Cantidad");

        tablaMateriales = new JTable(modeloMateriales);
        tablaMateriales.setRowHeight(28);
        tablaMateriales.setFont(new Font("Serif", Font.PLAIN, 12));
        tablaMateriales.getTableHeader().setBackground(cafe);
        tablaMateriales.getTableHeader().setForeground(Color.WHITE);
        tablaMateriales.getTableHeader().setFont(new Font("Serif", Font.BOLD, 12));

        JScrollPane scroll = new JScrollPane(tablaMateriales);
        scroll.setBounds(20, 530, 560, 100);
        scroll.setBorder(new LineBorder(new Color(200, 190, 180), 1));
        add(scroll);

        btnEliminarMaterial = new JButton("Eliminar seleccionado");
        btnEliminarMaterial.setBounds(20, 638, 190, 28);
        btnEliminarMaterial.setBackground(new Color(200, 80, 80));
        btnEliminarMaterial.setForeground(Color.WHITE);
        btnEliminarMaterial.setFocusPainted(false);
        add(btnEliminarMaterial);
    }

    // Construye los botones de acción final ("Guardar" y "Cancelar")
    private void construirBotonesFinales() {
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(330, 675, 120, 35);
        btnGuardar.setBackground(cafe);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(460, 675, 110, 35);
        btnCancelar.setFocusPainted(false);
        add(btnCancelar);
    }

    // Agrega una fila con los datos de un material a la tabla (utilizado por el Controlador)
    public void agregarFilaMaterial(String producto, String cantidad) {
        modeloMateriales.addRow(new Object[]{producto, cantidad});
    }

    // ── GETTERS que usa el Controlador ──
    public JComboBox<String> getCmbEstado() {
        return cmbEstado;
    }

    public JComboBox<String> getCmbDiaInicio() {
        return cmbDiaInicio;
    }

    public JComboBox<String> getCmbMesInicio() {
        return cmbMesInicio;
    }

    public JComboBox<String> getCmbAnioInicio() {
        return cmbAnioInicio;
    }

    public JComboBox<String> getCmbDiaFin() {
        return cmbDiaFin;
    }

    public JComboBox<String> getCmbMesFin() {
        return cmbMesFin;
    }

    public JComboBox<String> getCmbAnioFin() {
        return cmbAnioFin;
    }

    public JComboBox<String> getCmbProducto() {
        return cmbProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTable getTablaMateriales() {
        return tablaMateriales;
    }

    public DefaultTableModel getModeloMateriales() {
        return modeloMateriales;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public JButton getBtnAgregarMaterial() {
        return btnAgregarMaterial;
    }

    public JButton getBtnEliminarMaterial() {
        return btnEliminarMaterial;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    // Despliega un cuadro de diálogo emergente para reportar un error
    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    // Despliega un cuadro de diálogo emergente para confirmar una acción exitosa
    public void mostrarExito(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Exito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Método principal para visualizar rápidamente la interfaz del formulario de reparación de forma independiente
    public static void main(String[] args) {
        FormularioReparacionView vista = new FormularioReparacionView("Camisa");
        vista.setVisible(true);
    }
}