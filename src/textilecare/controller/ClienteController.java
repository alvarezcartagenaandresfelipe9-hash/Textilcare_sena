package textilecare.controller;

import textilecare.model.Prenda;
import textilecare.view.ClienteView;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class ClienteController {

    private ClienteView vista;
    private Prenda modelo;
    private int idCliente;

    public ClienteController(ClienteView vista, int idCliente) {
        this.vista = vista;
        this.modelo = new Prenda();
        this.idCliente = idCliente;

        cargarPrendas();
        agregarListeners();
    }

    private void cargarPrendas() {
        List<Prenda> lista = modelo.listarPorCliente(idCliente);

        for (Prenda p : lista) {
            vista.agregarFila(p.getId(), p.getTipo(), p.getDescripcion(), p.getFecha(), p.getEstado());
        }
    }

    private void agregarListeners() {

        // Boton "Salir"
        vista.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });

        // Boton "Ver Detalle"
        vista.getBtnDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalleSeleccionado();
            }
        });
    }

    private void verDetalleSeleccionado() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona una prenda primero.");
            return;
        }

        int idPrenda = vista.getIdPrenda(fila);
        Prenda p = modelo.buscarPorId(idPrenda);

        if (p == null) {
            JOptionPane.showMessageDialog(vista, "No se encontro la prenda.");
            return;
        }

        String mensaje = "Tipo: " + p.getTipo() + "\n"
                + "Descripcion: " + p.getDescripcion() + "\n"
                + "Estado: " + p.getEstado() + "\n"
                + "Fecha inicio: " + p.getFechaInicio() + "\n"
                + "Fecha fin: " + p.getFechaFin();

        JOptionPane.showMessageDialog(vista, mensaje, "Detalle de prenda", JOptionPane.INFORMATION_MESSAGE);
    }
}