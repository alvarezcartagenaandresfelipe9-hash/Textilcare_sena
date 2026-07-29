package textilecare.controller;

import textilecare.model.Producto;
import textilecare.model.Venta;
import textilecare.view.RegistrarVentaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrarVentaController {

    private RegistrarVentaView vista;
    private Producto modeloProducto;
    private Venta modeloVenta;
    private int idVendedor;

    private List<Producto> productosDisponibles;
    private List<Producto> productosAgregados = new ArrayList<>();
    private List<Integer> cantidadesAgregadas = new ArrayList<>();
    private int totalVenta = 0;

    public RegistrarVentaController(RegistrarVentaView vista, int idVendedor) {
        this.vista = vista;
        this.modeloProducto = new Producto();
        this.modeloVenta = new Venta();
        this.idVendedor = idVendedor;

        productosDisponibles = modeloProducto.listarProductosTienda();

        List<String> nombres = new ArrayList<>();
        for (Producto p : productosDisponibles) {
            nombres.add(p.getNombre());
        }
        vista.cargarProductos(nombres);

        agregarListeners();
    }

    private void agregarListeners() {

        // Boton "+ Agregar"
        vista.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarItem();
            }
        });

        // Boton "Confirmar Venta"
        vista.getBtnConfirmar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarVenta();
            }
        });

        // Boton "Cancelar"
        vista.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }

    private void agregarItem() {
        int indice = vista.getCmbProducto().getSelectedIndex();
        if (indice == -1) {
            vista.mostrarError("No hay productos disponibles.");
            return;
        }

        String cantidadTexto = vista.getTxtCantidad().getText().trim();
        if (cantidadTexto.isEmpty() || !cantidadTexto.matches("[0-9]+")) {
            vista.mostrarError("La cantidad debe ser un numero valido.");
            return;
        }

        int cantidad = Integer.parseInt(cantidadTexto);
        if (cantidad <= 0) {
            vista.mostrarError("La cantidad debe ser mayor a 0.");
            return;
        }

        Producto producto = productosDisponibles.get(indice);

        if (cantidad > producto.getStock()) {
            vista.mostrarError("Solo hay " + producto.getStock() + " unidades disponibles de " + producto.getNombre());
            return;
        }

        int subtotal = producto.getPrecio() * cantidad;
        totalVenta += subtotal;

        vista.agregarFilaItem(producto.getNombre(), cantidad, producto.getPrecio(), subtotal);
        vista.setTotal(totalVenta);

        productosAgregados.add(producto);
        cantidadesAgregadas.add(cantidad);

        vista.getTxtCantidad().setText("1");
    }

    private void confirmarVenta() {
        if (productosAgregados.isEmpty()) {
            vista.mostrarError("Agrega al menos un producto a la venta.");
            return;
        }

        String metodoPago = (String) vista.getCmbMetodoPago().getSelectedItem();

        boolean confirmado = vista.confirmar(
                "Metodo de pago: " + metodoPago + "\n"
                + "Total: $" + totalVenta + "\n\n"
                + "¿Confirmar la venta?"
        );

        if (!confirmado) {
            return;
        }

        int idVenta = modeloVenta.registrar(idVendedor, metodoPago, totalVenta);

        for (int i = 0; i < productosAgregados.size(); i++) {
            Producto producto = productosAgregados.get(i);
            int cantidad = cantidadesAgregadas.get(i);
            int subtotal = producto.getPrecio() * cantidad;

            modeloVenta.registrarDetalle(idVenta, producto.getId(), cantidad, producto.getPrecio(), subtotal);
            modeloProducto.descontarStock(producto.getId(), cantidad, producto.getStock());

            producto.setStock(producto.getStock() - cantidad);
        }

        vista.mostrarError("¡Venta registrada con exito!");
        vista.limpiarItems();
        vista.setTotal(0);
        productosAgregados.clear();
        cantidadesAgregadas.clear();
        totalVenta = 0;
        vista.dispose();
    }
}