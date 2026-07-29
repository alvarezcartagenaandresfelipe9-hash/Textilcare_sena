package textilecare.controller;

import textilecare.model.Proveedor;
import textilecare.view.ProveedorView;
import textilecare.view.RegistrarProveedorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProveedorController {

    private ProveedorView vista;
    private Proveedor modelo;
    private String nombreSupervisor;

    public ProveedorController(ProveedorView vista, String nombreSupervisor) {
        this.vista = vista;
        this.modelo = new Proveedor();
        this.nombreSupervisor = nombreSupervisor;

        cargarDatos();
        agregarListeners();
    }

    private void cargarDatos() {
        for (Proveedor p : modelo.listarTodos()) {
            vista.agregarFila(p.getNombreEmpresa(), p.getNit(), p.getTelefono(), p.getCorreo(), p.getProductos(), p.getEstado(), p.getNombreSupervisor());
        }

        vista.actualizarTarjetas(modelo.contarTotal(), modelo.contarActivos(), modelo.contarNuevosHoy());
    }

    public void recargarDatos() {
        vista.limpiarTabla();
        cargarDatos();
    }

    private void agregarListeners() {

        // Boton "+ Nuevo Proveedor"
        vista.getBtnNuevoProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarProveedorView vistaRegistrar = new RegistrarProveedorView();
                new RegistrarProveedorController(vistaRegistrar, ProveedorController.this, nombreSupervisor);
                vistaRegistrar.setVisible(true);
            }
        });

        // Boton "Cambiar Estado"
        vista.getBtnCambiarEstado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstado();
            }
        });

        // Botones de menu que aun no se han construido
        ActionListener proximamente = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.mostrarAviso("Esta seccion se construira mas adelante.");
            }
        };
        vista.getBtnPrendas().addActionListener(proximamente);
        vista.getBtnInventario().addActionListener(proximamente);
        vista.getBtnTecnicos().addActionListener(proximamente);
    }

    private void cambiarEstado() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            vista.mostrarAviso("Selecciona un proveedor de la tabla primero.");
            return;
        }

        String empresa = vista.getNombreEmpresaEnFila(fila);
        String estadoActual = vista.getEstadoEnFila(fila);
        String nuevoEstado = estadoActual.equals("Activo") ? "Inactivo" : "Activo";

        boolean confirmado = vista.confirmar(
                "Proveedor: " + empresa + "\n"
                + "Estado actual: " + estadoActual + "\n"
                + "¿Deseas cambiarlo a: " + nuevoEstado + "?"
        );

        if (confirmado) {
            modelo.cambiarEstado(empresa, nuevoEstado);
            recargarDatos();
        }
    }
}