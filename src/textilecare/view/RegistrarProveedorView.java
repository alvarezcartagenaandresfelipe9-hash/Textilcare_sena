package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

public class RegistrarProveedorView extends JFrame {

    private JTextField txtEmpresa;
    private JTextField txtNit;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtProductos;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final Color cafe = new Color(181, 137, 103);
    private final Color marronOscuro = new Color(90, 58, 35);

    public RegistrarProveedorView() {
        setTitle("Registrar Proveedor - TextilCare");
        setSize(500, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // TITULO
        JLabel titulo = new JLabel("Registrar Proveedor");
        titulo.setBounds(140, 20, 220, 30);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(marronOscuro);
        add(titulo);

        // NOMBRE EMPRESA
        JLabel lblEmpresa = new JLabel("Nombre de la empresa:");
        lblEmpresa.setBounds(40, 70, 200, 25);
        add(lblEmpresa);

        txtEmpresa = new JTextField();
        txtEmpresa.setBounds(40, 100, 400, 30);
        add(txtEmpresa);

        // NIT
        JLabel lblNit = new JLabel("NIT:");
        lblNit.setBounds(40, 140, 200, 25);
        add(lblNit);

        txtNit = new JTextField();
        txtNit.setBounds(40, 170, 400, 30);
        add(txtNit);

        // CORREO
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(40, 210, 200, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(40, 240, 400, 30);
        add(txtCorreo);

        // TELEFONO
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(40, 280, 200, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(40, 310, 400, 30);
        add(txtTelefono);

        // PRODUCTOS
        JLabel lblProductos = new JLabel("Productos:");
        lblProductos.setBounds(40, 350, 200, 25);
        add(lblProductos);

        txtProductos = new JTextField();
        txtProductos.setBounds(40, 380, 400, 30);
        add(txtProductos);

        // BOTON GUARDAR
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 430, 120, 35);
        btnGuardar.setBackground(cafe);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnGuardar);

        // BOTON CANCELAR
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 430, 120, 35);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCancelar);
    }

    // Limpia el formulario despues de guardar
    public void limpiarFormulario() {
        txtEmpresa.setText("");
        txtNit.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtProductos.setText("");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── GETTERS que usa el Controlador ──
    public JTextField getTxtEmpresa() {
        return txtEmpresa;
    }

    public JTextField getTxtNit() {
        return txtNit;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtProductos() {
        return txtProductos;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    // Solo para ver rapido como se ve el formulario
    public static void main(String[] args) {
        RegistrarProveedorView vista = new RegistrarProveedorView();
        vista.setVisible(true);
    }
}