package textilecare.controller;

import textilecare.model.Producto;
import textilecare.view.ProductoSinStockView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoSinStockController {

    // La vista que maneja este controlador
    private ProductoSinStockView vista;

    // El modelo que consulta la base de datos
    private Producto modeloProducto;

    // Constructor: recibe la vista, carga los datos y activa los botones
    public ProductoSinStockController(ProductoSinStockView vista) {
        this.vista          = vista;
        this.modeloProducto = new Producto();

        // Carga los productos sin stock al abrir la ventana
        cargarProductosSinStock();

        // Conecta el botón cerrar
        agregarListeners();
    }

    // Pide al modelo los productos agotados y se los manda a la vista
    private void cargarProductosSinStock() {
        List<Producto> sinStock = modeloProducto.listarProductosSinStock();
        vista.mostrarProductosSinStock(sinStock);
    }

    // Conecta el botón "Cerrar" con la acción de cerrar la ventana
    private void agregarListeners() {
        vista.getBtnCerrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }
}