package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.model.Prenda;
import textilecare.view.RegistrarPrendaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;

public class RegistrarPrendaController {

    private RegistrarPrendaView vista;
    private Usuario modeloUsuario;
    private Prenda modeloPrenda;
    private SupervisorController controladorSupervisor;

    public RegistrarPrendaController(RegistrarPrendaView vista, SupervisorController controladorSupervisor) {
        this.vista = vista;
        this.modeloUsuario = new Usuario();
        this.modeloPrenda = new Prenda();
        this.controladorSupervisor = controladorSupervisor;

        vista.cargarClientes(modeloUsuario.listarPorRol("Cliente"));
        vista.cargarTecnicos(modeloUsuario.listarPorRol("Tecnico"));

        agregarListeners();
    }

    private void agregarListeners() {

        // Boton "Guardar"
        vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
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

    private void guardar() {
        String tipo = (String) vista.getCmbTipo().getSelectedItem();
        if (tipo.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un tipo de prenda.");
            return;
        }

        String nombreCliente = (String) vista.getCmbCliente().getSelectedItem();
        if (nombreCliente.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un cliente.");
            return;
        }

        String nombreTecnico = (String) vista.getCmbTecnico().getSelectedItem();
        if (nombreTecnico.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un tecnico.");
            return;
        }

        String dia = (String) vista.getCmbDia().getSelectedItem();
        String mes = (String) vista.getCmbMes().getSelectedItem();
        String anio = (String) vista.getCmbAnio().getSelectedItem();

        if (dia.equals("Dia") || mes.equals("Mes") || anio.equals("Año")) {
            vista.mostrarError("Debes seleccionar dia, mes y año completos.");
            return;
        }

        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int numeroMes = 0;
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equals(mes)) {
                numeroMes = i + 1;
            }
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.of(Integer.parseInt(anio), numeroMes, Integer.parseInt(dia));
        } catch (Exception ex) {
            vista.mostrarError("La fecha no existe (ej: 31 de Febrero).");
            return;
        }

        if (!fecha.isEqual(LocalDate.now())) {
            vista.mostrarError("La fecha de ingreso debe ser el dia de hoy.");
            return;
        }

        String descripcion = vista.getTxtDescripcion().getText().trim();
        if (descripcion.isEmpty()) {
            vista.mostrarError("El campo Descripcion es obligatorio.");
            return;
        }
        if (descripcion.length() < 10) {
            vista.mostrarError("La descripcion debe tener al menos 10 caracteres.");
            return;
        }

        int idCliente = modeloUsuario.buscarIdPorNombreYRol(nombreCliente, "Cliente");
        if (idCliente == 0) {
            vista.mostrarError("El cliente no existe en la base de datos.");
            return;
        }

        int idTecnico = modeloUsuario.buscarIdPorNombreYRol(nombreTecnico, "Tecnico");
        if (idTecnico == 0) {
            vista.mostrarError("El tecnico no existe en la base de datos.");
            return;
        }

        boolean guardado = modeloPrenda.registrar(tipo, descripcion, fecha.toString(), idCliente, idTecnico);

        if (guardado) {
            vista.mostrarExito("Prenda registrada exitosamente.");
            vista.limpiarFormulario();
            controladorSupervisor.recargarPrendas();
        } else {
            vista.mostrarError("Ocurrio un error al guardar la prenda.");
        }
    }
}