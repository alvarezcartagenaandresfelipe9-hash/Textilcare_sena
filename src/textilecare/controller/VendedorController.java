package textilecare.controller;

import textilecare.model.Producto;
import textilecare.model.ReporteInventarioPDF;
import textilecare.view.VendedorView;
import textilecare.view.RegistrarVentaView;

import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.time.LocalDate;

public class VendedorController {

    // La Vista con la que este Controlador habla (la ventana del Vendedor)
    private VendedorView vista;

    // El Modelo que sabe consultar y modificar productos en la base de datos
    private Producto modeloProducto;

    // Datos del vendedor que inicio sesion, para saber quien registra la venta/reporte
    private int idVendedor;
    private String nombreVendedor;

    // Se ejecuta apenas se crea el Controlador: carga los productos y activa los botones
    public VendedorController(VendedorView vista, int idVendedor, String nombreVendedor) {
        this.vista = vista;
        this.modeloProducto = new Producto();
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;

        cargarProductos();
        agregarListeners();
    }

    // Pide al Modelo la lista de productos de tienda y se la entrega a la Vista para mostrarla
    private void cargarProductos() {
        vista.mostrarProductos(modeloProducto.listarProductosTienda());
    }

    // Conecta cada boton con la accion que debe ejecutar al hacer clic
    private void agregarListeners() {

        // Boton "Buscar"
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });

        // Presionar Enter dentro del campo de busqueda
        vista.getTxtBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });

        // Boton "Registrar Venta"
        vista.getBtnRegistrarVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarVenta();
            }
        });

        // Boton "Generar Reporte"
        vista.getBtnReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePDF();
            }
        });

        // Boton "Salir"
        vista.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }

    // Si el buscador esta vacio, muestra todos los productos.
    // Si tiene texto, muestra solo los que coincidan con lo escrito.
    private void buscar() {
        String texto = vista.getTxtBuscar().getText().trim();

        if (texto.isEmpty()) {
            cargarProductos();
        } else {
            vista.mostrarProductos(modeloProducto.buscarProductosTienda(texto));
        }
    }

    // Abre la ventana "Registrar Venta" con su propio Controlador.
    // Cuando esa ventana se cierra, recarga los productos por si cambio el stock.
    private void abrirRegistrarVenta() {
        RegistrarVentaView vistaVenta = new RegistrarVentaView(vista);
        new RegistrarVentaController(vistaVenta, idVendedor);
        vistaVenta.setVisible(true);

        cargarProductos();
    }

    // Deja que el usuario elija donde guardar el PDF, y le pide a ReporteInventarioPDF
    // que lo arme. Este metodo NO sabe como se construye un PDF, solo coordina.
    private void generarReportePDF() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de inventario");
        selector.setSelectedFile(new File("reporte_inventario.pdf"));

        int resultado = selector.showSaveDialog(vista);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();

            // Si el usuario no escribio ".pdf" al final, se lo agregamos nosotros
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta = ruta + ".pdf";
            }

            String fecha = LocalDate.now().toString();
            ReporteInventarioPDF reporte = new ReporteInventarioPDF();
            boolean exito = reporte.generarReporte(modeloProducto.listarProductosTienda(), nombreVendedor, fecha, ruta);

            if (exito) {
                vista.mostrarExito("Reporte PDF generado exitosamente en:\n" + ruta);
            } else {
                vista.mostrarError("Ocurrio un error al generar el PDF.");
            }
        }
    }
}