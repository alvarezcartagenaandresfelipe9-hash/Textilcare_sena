package textilecare.controller;

import textilecare.model.Prenda;
import textilecare.model.ReportePDF;
import textilecare.view.SupervisorView;
import textilecare.view.RegistrarPrendaView;
import textilecare.view.EstadoPrendaView;
import textilecare.view.InventarioView;
import textilecare.view.ProveedorView;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.util.List;

public class SupervisorController {

    private SupervisorView vista;
    private Prenda modelo;
    private List<Prenda> listaCompleta;
    private String nombreSupervisor;

    // Constructor que inicializa los componentes principales y carga los datos
    public SupervisorController(SupervisorView vista, String nombreSupervisor) {
        this.vista = vista;
        this.modelo = new Prenda();
        this.nombreSupervisor = nombreSupervisor;

        cargarPrendas();
        agregarListeners();
    }

    // Carga todas las prendas desde la base de datos y las añade a la tabla
    private void cargarPrendas() {
        listaCompleta = modelo.listarTodas();

        for (Prenda p : listaCompleta) {
            vista.agregarFila(p.getId(), p.getTipo(), p.getNombreTecnico(), p.getNombreCliente(), p.getFecha(), p.getEstado());
        }
    }

    // Limpia la tabla visual y recarga los datos actualizados
    public void recargarPrendas() {
        vista.limpiarTabla();
        cargarPrendas();
    }

    // Configura los eventos para botones, clics en la tabla y buscador en tiempo real
    private void agregarListeners() {

        // Boton "Registrar Prenda": el ActionEvent 'e' avisa que hubo un clic,
        // y ahi mismo abrimos la ventana de registro
        vista.getBtnRegistrarPrenda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarPrendaView vistaRegistrar = new RegistrarPrendaView();
                new RegistrarPrendaController(vistaRegistrar, SupervisorController.this);
                vistaRegistrar.setVisible(true);
            }
        });

        // Boton "Exportar PDF": el ActionEvent 'e' avisa el clic para llamar a exportarPDF()
        vista.getBtnExportarPDF().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarPDF();
            }
        });

        // Clic en una fila de la tabla: abre el detalle de estado de esa prenda.
        // El MouseEvent 'e' trae informacion tecnica del clic (donde, cuando, etc)
        vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getFilaSeleccionada();
                if (fila >= 0) {
                    int idPrenda = vista.getIdPrenda(fila);
                    EstadoPrendaView vistaEstado = new EstadoPrendaView();
                    new EstadoPrendaController(vistaEstado, idPrenda);
                    vistaEstado.setVisible(true);
                }
            }
        });

        // Filtra los datos de la tabla mientras el usuario escribe en el buscador.
        // El DocumentEvent 'e' avisa QUE tipo de cambio hubo en el texto (se agrego, borro, etc)
        vista.getTxtBuscar().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        // Boton "Inventario": el ActionEvent 'e' avisa el clic para abrir esa ventana
        vista.getBtnInventario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventarioView vistaInventario = new InventarioView(nombreSupervisor);
                new InventarioController(vistaInventario);
                vistaInventario.setVisible(true);
            }
        });

        // Boton "Proveedores": el ActionEvent 'e' avisa el clic para abrir esa ventana
        vista.getBtnProveedores().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorView vistaProveedor = new ProveedorView(nombreSupervisor);
                new ProveedorController(vistaProveedor, nombreSupervisor);
                vistaProveedor.setVisible(true);
            }
        });
    }

    // Filtra las prendas en la tabla segun el texto ingresado
    private void filtrar() {
        String texto = vista.getTxtBuscar().getText().toLowerCase();
        vista.limpiarTabla();

        for (Prenda p : listaCompleta) {
            boolean coincide = p.getTipo().toLowerCase().contains(texto)
                    || p.getNombreTecnico().toLowerCase().contains(texto)
                    || p.getNombreCliente().toLowerCase().contains(texto);

            if (coincide) {
                vista.agregarFila(p.getId(), p.getTipo(), p.getNombreTecnico(), p.getNombreCliente(), p.getFecha(), p.getEstado());
            }
        }
    }

    // Gestiona la seleccion de ruta y la creacion del archivo PDF con el reporte
    private void exportarPDF() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de prendas");
        selector.setSelectedFile(new File("reporte_prendas.pdf"));

        int resultado = selector.showSaveDialog(vista);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta = ruta + ".pdf";
            }

            ReportePDF reporte = new ReportePDF();
            boolean exito = reporte.generarReportePrendas(modelo.listarTodas(), ruta);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Reporte PDF generado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(vista, "Ocurrio un error al generar el PDF.");
            }
        }
    }
}