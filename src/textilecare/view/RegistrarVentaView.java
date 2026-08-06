package textilecare.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

import java.util.List;

public class RegistrarVentaView extends JDialog {

    private JComboBox<String> cmbProducto;
    private JTextField txtCantidad;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnAgregar;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private final Color morado = new Color(215, 155, 175);
    private final Color moradoOscuro = new Color(145, 75, 95);
    private final Color verde = new Color(50, 160, 60);

    public RegistrarVentaView(JFrame padre) {
        super(padre, "Registrar Venta", true);
        setSize(650, 550);
        setLocationRelativeTo(padre);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        construirTitulo();
        construirFormularioProducto();
        construirTabla();
        construirTotalYBotones();
    }

    private void construirTitulo() {
        JLabel titulo = new JLabel("Registrar Venta", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(morado);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(0, 0, 650, 45);
        add(titulo);
    }

    private void construirFormularioProducto() {
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(20, 60, 80, 25);
        add(lblProducto);

        cmbProducto = new JComboBox<>();
        cmbProducto.setBounds(20, 85, 260, 30);
        add(cmbProducto);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(295, 60, 80, 25);
        add(lblCantidad);

        txtCantidad = new JTextField("1");
        txtCantidad.setBounds(295, 85, 60, 30);
        add(txtCantidad);

        btnAgregar = new JButton("+ Agregar");
        btnAgregar.setBackground(morado);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregar.setBounds(470, 85, 140, 30);
        add(btnAgregar);

        JLabel lblMetodo = new JLabel("Metodo de pago:");
        lblMetodo.setBounds(20, 130, 150, 25);
        add(lblMetodo);

        cmbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia", "Tarjeta debito", "Tarjeta credito"});
        cmbMetodoPago.setBounds(160, 130, 200, 30);
        add(cmbMetodoPago);
    }

    private void construirTabla() {
        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));

        tabla.getTableHeader().setBackground(moradoOscuro);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 175, 610, 240);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(215, 205, 235)));
        add(scroll);
    }

    private void construirTotalYBotones() {
        JLabel lblTextoTotal = new JLabel("Total:");
        lblTextoTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTextoTotal.setBounds(400, 425, 60, 30);
        add(lblTextoTotal);

        lblTotal = new JLabel("$0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(moradoOscuro);
        lblTotal.setBounds(465, 425, 165, 30);
        add(lblTotal);

        btnConfirmar = new JButton("Confirmar Venta");
        btnConfirmar.setBackground(verde);
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 13));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBounds(20, 470, 180, 40);
        add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(210, 470, 120, 40);
        add(btnCancelar);
    }

    // Llena el combo de productos disponibles
    public void cargarProductos(List<String> nombres) {
        for (String nombre : nombres) {
            cmbProducto.addItem(nombre);
        }
    }

    // Agrega una fila a la tabla de items de la venta
    public void agregarFilaItem(String producto, int cantidad, int precioUnitario, int subtotal) {
        modeloTabla.addRow(new Object[]{producto, cantidad, "$" + precioUnitario, "$" + subtotal});
    }

    public void limpiarItems() {
        modeloTabla.setRowCount(0);
    }

    public void setTotal(int total) {
        lblTotal.setText("$" + total);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmar(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar venta", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    // ── GETTERS que usa el Controlador ──
    public JComboBox<String> getCmbProducto() {
        return cmbProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JComboBox<String> getCmbMetodoPago() {
        return cmbMetodoPago;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}