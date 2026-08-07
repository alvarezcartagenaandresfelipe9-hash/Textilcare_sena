package textilecare.controller;

import textilecare.model.Producto;
import textilecare.model.ReporteInventarioPDF;
import textilecare.view.VendedorView;
import textilecare.view.RegistrarVentaView;
import textilecare.view.ProductoSinStockView;

import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.time.LocalDate;

public class VendedorController {

    // La vista que maneja este controlador
    private VendedorView vista;

    // El modelo que consulta productos en la base de datos
    private Producto modeloProducto;

    // Datos del vendedor que inició sesión
    private int    idVendedor;
    private String nombreVendedor;

    // Se ejecuta al crear el controlador
    public VendedorController(VendedorView vista, int idVendedor, String nombreVendedor) {
        this.vista          = vista;
        this.modeloProducto = new Producto();
        this.idVendedor     = idVendedor;
        this.nombreVendedor = nombreVendedor;

        // Carga los productos al abrir la ventana
        cargarProductos();

        // Conecta todos los botones con sus acciones
        agregarListeners();
    }

    // Pide los productos al modelo y los muestra en la vista
    private void cargarProductos() {
        vista.mostrarProductos(modeloProducto.listarProductosTienda());
    }

    // Conecta cada botón con el método que debe ejecutar
    private void agregarListeners() {

        // Botón "Buscar" → filtra tarjetas por nombre
        vista.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });

        // Presionar Enter en el buscador → igual que hacer clic en Buscar
        vista.getTxtBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });

        // Botón "Registrar Venta" → abre el formulario de venta
        vista.getBtnRegistrarVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarVenta();
            }
        });

        // Botón "Generar Reporte" → guarda el PDF en disco
        vista.getBtnReporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePDF();
            }
        });

        // Botón "Salir" → cierra la ventana del vendedor
        vista.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });

        // Botón "Productos sin stock" → abre la ventana de productos agotados
        vista.getBtnSinStock().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProductosSinStock();
            }
        });
    }

    // Si el buscador está vacío muestra todos los productos.
    // Si tiene texto muestra solo los que coincidan.
    private void buscar() {
        String texto = vista.getTxtBuscar().getText().trim();

        if (texto.isEmpty()) {
            cargarProductos();
        } else {
            vista.mostrarProductos(modeloProducto.buscarProductosTienda(texto));
        }
    }

    // Abre la ventana de registrar venta y recarga los productos al cerrarla
    private void abrirRegistrarVenta() {
        RegistrarVentaView vistaVenta = new RegistrarVentaView(vista);
        new RegistrarVentaController(vistaVenta, idVendedor);
        vistaVenta.setVisible(true);

        // Recarga por si cambió el stock al vender
        cargarProductos();
    }

    // Abre la ventana que muestra los productos sin stock
    private void abrirProductosSinStock() {
        ProductoSinStockView vistaSinStock = new ProductoSinStockView(vista);
        new ProductoSinStockController(vistaSinStock);
        vistaSinStock.setVisible(true);
    }

    // Genera y guarda el PDF del reporte de inventario
    private void generarReportePDF() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de inventario");
        selector.setSelectedFile(new File("reporte_inventario.pdf"));

        int resultado = selector.showSaveDialog(vista);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();

            // Si no tiene .pdf al final, se lo agregamos
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta = ruta + ".pdf";
            }

            String fecha = LocalDate.now().toString();
            ReporteInventarioPDF reporte = new ReporteInventarioPDF();
            boolean exito = reporte.generarReporte(
                modeloProducto.listarProductosTienda(),
                nombreVendedor,
                fecha,
                ruta
            );

            if (exito) {
                vista.mostrarExito("Reporte PDF generado exitosamente en:\n" + ruta);
            } else {
                vista.mostrarError("Ocurrió un error al generar el PDF.");
            }
        }
    }
}