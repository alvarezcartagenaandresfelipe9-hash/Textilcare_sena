package textilecare.controller;

import textilecare.model.Prenda;
import textilecare.model.FotoPrenda;
import textilecare.view.EstadoPrendaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstadoPrendaController {

    private EstadoPrendaView vista;
    private Prenda modeloPrenda;
    private FotoPrenda modeloFoto;

    public EstadoPrendaController(EstadoPrendaView vista, int idPrenda) {
        this.vista = vista;
        this.modeloPrenda = new Prenda();
        this.modeloFoto = new FotoPrenda();

        Prenda p = modeloPrenda.buscarDetalleCompleto(idPrenda);

        if (p != null) {
            byte[] fotoPendiente = modeloFoto.buscarFoto(idPrenda, "Pendiente");
            byte[] fotoEnProceso = modeloFoto.buscarFoto(idPrenda, "En proceso");
            byte[] fotoReparada = modeloFoto.buscarFoto(idPrenda, "Reparada");

            vista.mostrarPrenda(p.getTipo(), p.getFecha(), p.getDescripcion(), p.getEstado(),
                    p.getNombreCliente(), p.getNombreTecnico(),
                    fotoPendiente, fotoEnProceso, fotoReparada);
        }

        // Boton "Volver"
        vista.getBtnVolver().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }
}