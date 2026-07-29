package textilecare.controller;

import textilecare.model.Producto;
import textilecare.model.Proveedor;
import textilecare.view.InventarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventarioController {

    private InventarioView vista;
    private Producto modeloProducto;
    private Proveedor modeloProveedor;

    public InventarioController(InventarioView vista) {
        this.vista = vista;
        this.modeloProducto = new Producto();
        this.modeloProveedor = new Proveedor();

        cargarCombos();
        cargarTabla();
        agregarListeners();
    }

    private void cargarCombos() {
        vista.cargarProveedores(modeloProveedor.listarNombres());
        vista.cargarProductosExistentes(modeloProducto.listarTodosLosNombres());
    }

    private void cargarTabla() {
        for (Producto p : modeloProducto.listarTodos()) {
            vista.agregarFilaProducto(p.getNombre(), p.getCategoria(), p.getStock(), p.getUnidad(), p.getNombreProveedor(), p.getPrecio(), p.getEstado());
        }
    }

    private void recargarTodo() {
        vista.limpiarTabla();
        cargarTabla();
        vista.cargarProductosExistentes(modeloProducto.listarTodosLosNombres());
    }

    private void agregarListeners() {

        // Boton "Guardar producto"
        vista.getBtnGuardarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        // Boton "Ingresar al inventario"
        vista.getBtnIngresarCantidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarCantidad();
            }
        });

        // Boton "Prendas" (vuelve a la ventana anterior)
        vista.getBtnPrendas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }

    private void guardarProducto() {
        String nombre = vista.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            vista.mostrarError("El nombre del producto es obligatorio.");
            return;
        }

        String categoria = (String) vista.getCmbCategoria().getSelectedItem();
        String unidad = (String) vista.getCmbUnidad().getSelectedItem();

        String cantidadTexto = vista.getTxtCantidad().getText().trim();
        if (cantidadTexto.isEmpty() || !cantidadTexto.matches("[0-9]+")) {
            vista.mostrarError("La cantidad debe ser un numero valido.");
            return;
        }

        String precioTexto = vista.getTxtPrecio().getText().trim();
        if (precioTexto.isEmpty() || !precioTexto.matches("[0-9]+")) {
            vista.mostrarError("El precio debe ser un numero valido.");
            return;
        }

        String nombreProveedor = (String) vista.getCmbProveedor().getSelectedItem();
        if (nombreProveedor == null) {
            vista.mostrarError("Debes tener al menos un proveedor registrado.");
            return;
        }

        int idProveedor = modeloProveedor.buscarIdPorNombre(nombreProveedor);
        if (idProveedor == 0) {
            vista.mostrarError("El proveedor no existe en la base de datos.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadTexto);
        int precio = Integer.parseInt(precioTexto);

        boolean guardado = modeloProducto.registrar(nombre, categoria, cantidad, unidad, precio, idProveedor);

        if (guardado) {
            vista.mostrarExito("Producto guardado exitosamente.");
            vista.limpiarFormularioNuevoProducto();
            recargarTodo();
        } else {
            vista.mostrarError("Ocurrio un error al guardar el producto.");
        }
    }

    private void ingresarCantidad() {
        String nombreProducto = (String) vista.getCmbProductoExistente().getSelectedItem();
        if (nombreProducto == null) {
            vista.mostrarError("No hay productos registrados todavia.");
            return;
        }

        String cantidadTexto = vista.getTxtCantidadIngreso().getText().trim();
        if (cantidadTexto.isEmpty() || !cantidadTexto.matches("[0-9]+")) {
            vista.mostrarError("La cantidad a ingresar debe ser un numero valido.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadTexto);
        boolean actualizado = modeloProducto.ingresarCantidad(nombreProducto, cantidad);

        if (actualizado) {
            vista.mostrarExito("Stock actualizado correctamente.");
            vista.getTxtCantidadIngreso().setText("");
            recargarTodo();
        } else {
            vista.mostrarError("Ocurrio un error al actualizar el stock.");
        }
    }
}