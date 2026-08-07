package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Cursor;

import java.util.List;

import textilecare.model.Producto;

public class ProductoSinStockView extends JFrame {

    // Colores iguales al VendedorView
    private final Color morado       = new Color(215, 155, 175);
    private final Color moradoOscuro = new Color(145,  75,  95);
    private final Color fondoLila    = new Color(248, 240, 243);
    private final Color rojoAgotado  = new Color(180,  30,  30);

    // Panel donde van las tarjetas
    private JPanel  panelTarjetas;

    // Botón cerrar
    private JButton btnCerrar;

    public ProductoSinStockView(JFrame padre) {
        setTitle("TextilCare - Productos sin stock");
        setSize(820, 500);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        construirUI();
    }

    // Construye toda la interfaz
    private void construirUI() {
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCuerpo(),     BorderLayout.CENTER);
        add(crearPie(),        BorderLayout.SOUTH);
    }

    // Encabezado con título
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        panel.setBackground(moradoOscuro);

        JLabel lblTitulo = new JLabel("Productos sin stock (Agotados)");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo);

        return panel;
    }

    // Cuerpo con scroll de tarjetas
    private JScrollPane crearCuerpo() {
        panelTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 18));
        panelTarjetas.setBackground(fondoLila);

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        return scroll;
    }

    // Pie con botón cerrar
    private JPanel crearPie() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panel.setBackground(fondoLila);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCerrar.setBackground(moradoOscuro);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnCerrar);

        return panel;
    }

    // El controlador llama este método para dibujar las tarjetas
    public void mostrarProductosSinStock(List<Producto> productos) {
        panelTarjetas.removeAll();

        if (productos.isEmpty()) {
            // Si no hay agotados muestra un mensaje verde
            JLabel lblVacio = new JLabel("¡Todos los productos tienen stock disponible!");
            lblVacio.setFont(new Font("Arial", Font.BOLD, 15));
            lblVacio.setForeground(new Color(34, 139, 34));
            panelTarjetas.add(lblVacio);
        } else {
            // Dibuja una tarjeta por cada producto agotado
            for (Producto p : productos) {
                panelTarjetas.add(crearTarjetaAgotada(p));
            }
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    // Tarjeta con borde rojo para un producto agotado
    private JPanel crearTarjetaAgotada(Producto p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(rojoAgotado, 2),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        tarjeta.setPreferredSize(new Dimension(190, 155));

        // Nombre
        JLabel lblNombre = new JLabel(p.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setForeground(moradoOscuro);

        // Talla
        JLabel lblTalla = new JLabel("Talla: " + p.getTalla());
        lblTalla.setFont(new Font("Arial", Font.PLAIN, 12));

        // Stock en rojo (mostrará 0)
        JLabel lblStock = new JLabel("Stock: " + p.getStock());
        lblStock.setFont(new Font("Arial", Font.BOLD, 12));
        lblStock.setForeground(rojoAgotado);

        // Precio
        JLabel lblPrecio = new JLabel("$" + String.format("%,d", p.getPrecio()).replace(",", "."));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrecio.setForeground(morado);

        // Badge rojo "AGOTADO"
        JLabel lblEstado = new JLabel("AGOTADO", javax.swing.SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 11));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(rojoAgotado);
        lblEstado.setForeground(Color.WHITE);

        tarjeta.add(lblNombre);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 6)));
        tarjeta.add(lblTalla);
        tarjeta.add(lblStock);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 6)));
        tarjeta.add(lblPrecio);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 6)));
        tarjeta.add(lblEstado);

        return tarjeta;
    }

    // Muestra mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Getter para el controlador
    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    // Necesario para que NetBeans reconozca esto como JFrame Form
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductoSinStockView vista = new ProductoSinStockView(null);
                vista.setVisible(true);
            }
        });
    }
}
