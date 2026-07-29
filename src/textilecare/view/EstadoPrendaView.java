package textilecare.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class EstadoPrendaView extends JFrame {

    private JLabel lblNombrePrenda;
    private JLabel lblEstadoActual;
    private JPanel panelTarjetas;
    private JButton btnVolver;

    private final Color cafe = new Color(181, 137, 103);
    private final Color fondo = new Color(238, 232, 224);

    public EstadoPrendaView() {
        setTitle("Estado de prenda");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(fondo);

        // TITULO
        JLabel titulo = new JLabel("Estado de prenda");
        titulo.setBounds(40, 20, 300, 40);
        titulo.setOpaque(true);
        titulo.setBackground(cafe);
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo);

        // NOMBRE DE LA PRENDA
        lblNombrePrenda = new JLabel();
        lblNombrePrenda.setBounds(40, 75, 300, 25);
        lblNombrePrenda.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblNombrePrenda);

        // ESTADO ACTUAL
        lblEstadoActual = new JLabel();
        lblEstadoActual.setBounds(40, 105, 300, 20);
        lblEstadoActual.setFont(new Font("Arial", Font.PLAIN, 13));
        lblEstadoActual.setForeground(Color.GRAY);
        add(lblEstadoActual);

        // Panel donde van las tarjetas (1, 2 o 3 segun el estado)
        panelTarjetas = new JPanel();
        panelTarjetas.setBounds(40, 135, 1000, 430);
        panelTarjetas.setLayout(null);
        panelTarjetas.setBackground(fondo);
        add(panelTarjetas);

        // BOTON VOLVER
        btnVolver = new JButton("← Volver");
        btnVolver.setBounds(40, 580, 120, 35);
        btnVolver.setBackground(cafe);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        add(btnVolver);
    }

    // Muestra los datos de la prenda y crea las tarjetas segun el estado.
    // Las fotos pueden venir en null si el tecnico no ha subido nada para esa etapa todavia.
    public void mostrarPrenda(String tipo, String fecha, String descripcion, String estado, String cliente, String tecnico,
                               byte[] fotoPendiente, byte[] fotoEnProceso, byte[] fotoReparada) {

        lblNombrePrenda.setText(tipo);
        lblEstadoActual.setText("Estado actual: " + estado);

        panelTarjetas.removeAll();

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

    // Crea una tarjeta visual con los datos de un estado, incluyendo su foto (si existe).
    private JPanel crearTarjeta(int posicion, String fecha, String cliente, String tecnico, String descripcion, String estado, byte[] fotoBytes) {
        int x = posicion * 240;

        JPanel tarjeta = new JPanel();
        tarjeta.setBounds(x, 0, 220, 430);
        tarjeta.setLayout(null);
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(new LineBorder(new Color(210, 200, 190)));

        // Banner de color con el nombre del estado
        JPanel panelColor = new JPanel();
        panelColor.setBounds(0, 0, 220, 45);
        panelColor.setLayout(null);

        JLabel lblEstadoTop = new JLabel(estado, SwingConstants.CENTER);
        lblEstadoTop.setBounds(0, 10, 220, 25);
        lblEstadoTop.setFont(new Font("Arial", Font.BOLD, 14));

        if (estado.equals("Reparada")) {
            panelColor.setBackground(new Color(200, 240, 220));
            lblEstadoTop.setForeground(new Color(15, 110, 86));
        } else if (estado.equals("En proceso")) {
            panelColor.setBackground(new Color(255, 235, 190));
            lblEstadoTop.setForeground(new Color(133, 79, 11));
        } else {
            panelColor.setBackground(new Color(230, 228, 220));
            lblEstadoTop.setForeground(new Color(80, 78, 74));
        }

        panelColor.add(lblEstadoTop);
        tarjeta.add(panelColor);

        // Foto de la prenda en ese estado (o un aviso de texto si no hay foto)
        JLabel lblFoto = crearLabelFoto(fotoBytes);
        lblFoto.setBounds(10, 55, 200, 110);
        tarjeta.add(lblFoto);

        JLabel lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setBounds(10, 175, 200, 20);
        tarjeta.add(lblFecha);

        JLabel lblCliente = new JLabel("Cliente: " + cliente);
        lblCliente.setBounds(10, 200, 200, 20);
        tarjeta.add(lblCliente);

        JLabel lblTecnico = new JLabel("Tecnico: " + tecnico);
        lblTecnico.setBounds(10, 225, 200, 20);
        tarjeta.add(lblTecnico);

        JLabel lblDesc = new JLabel("<html>" + descripcion + "</html>");
        lblDesc.setBounds(10, 255, 200, 80);
        lblDesc.setForeground(Color.GRAY);
        tarjeta.add(lblDesc);

        return tarjeta;
    }

    // Convierte los bytes de la foto en una imagen real que se pueda mostrar (proceso inverso a guardarla).
    // Si no hay foto (fotoBytes es null), muestra un texto de aviso en su lugar.
    private JLabel crearLabelFoto(byte[] fotoBytes) {
        JLabel lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(new LineBorder(new Color(220, 215, 210)));
        lblFoto.setOpaque(true);
        lblFoto.setBackground(new Color(245, 245, 245));

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
        }

        return lblFoto;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    // Solo para ver rapido como se ve la ventana (sin fotos reales, solo con el aviso "Sin foto")
    public static void main(String[] args) {
        EstadoPrendaView vista = new EstadoPrendaView();
        vista.mostrarPrenda("Camisa", "2026-07-01", "Mancha de cafe en el cuello", "En proceso", "Carlos Perez", "Daniel Ramirez", null, null, null);
        vista.setVisible(true);
    }
}