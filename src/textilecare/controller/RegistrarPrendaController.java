package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.model.Prenda;
import textilecare.view.RegistrarPrendaView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistrarPrendaController {

    private RegistrarPrendaView    vista;
    private Usuario                modeloUsuario;
    private Prenda                 modeloPrenda;
    private SupervisorController   controladorSupervisor;

    public RegistrarPrendaController(RegistrarPrendaView vista, SupervisorController controladorSupervisor) {
        this.vista                 = vista;
        this.modeloUsuario         = new Usuario();
        this.modeloPrenda          = new Prenda();
        this.controladorSupervisor = controladorSupervisor;

        // Cargar los combos con datos reales de la BD al abrir la ventana
        vista.cargarClientes(modeloUsuario.listarPorRol("Cliente"));
        vista.cargarTecnicos(modeloUsuario.listarPorRol("Tecnico"));

        // Cargar la tabla de prendas al abrir
        cargarTablaPrendas();

        agregarListeners();
    }

    // Conecta los botones con sus métodos
    private void agregarListeners() {

        // Botón Guardar → valida y guarda la prenda
        vista.getBtnGuardar().addActionListener(e -> guardar());

        // Botón Cancelar → cierra la ventana sin guardar
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());

        // Botón Recargar → actualiza la tabla de prendas
        vista.getBtnRecargar().addActionListener(e -> cargarTablaPrendas());
    }

    // Carga la tabla con todas las prendas del sistema
    private void cargarTablaPrendas() {
        // Trae todas las prendas de la BD
        List<Prenda> prendas = modeloPrenda.listarTodas();

        // Convierte cada Prenda en una fila de Object[] para la tabla
        List<Object[]> filas = new ArrayList<>();

        for (Prenda p : prendas) {
            Object[] fila = {
                p.getId(),
                p.getTipo(),
                p.getNombreCliente(),
                p.getNombreTecnico(),
                p.getFecha(),
                p.getEstado()
            };
            filas.add(fila);
        }

        // Le pasa las filas a la vista para que las muestre
        vista.cargarPrendasEnTabla(filas);
    }

    // Valida el formulario y guarda la prenda en la BD
    private void guardar() {

        // 1. Validar tipo de prenda
        String tipo = (String) vista.getCmbTipo().getSelectedItem();
        if (tipo.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un tipo de prenda.");
            return;
        }

        // 2. Validar cliente
        String nombreCliente = (String) vista.getCmbCliente().getSelectedItem();
        if (nombreCliente.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un cliente.");
            return;
        }

        // 3. Validar técnico
        String nombreTecnico = (String) vista.getCmbTecnico().getSelectedItem();
        if (nombreTecnico.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un tecnico.");
            return;
        }

        // 4. Validar fecha
        String dia  = (String) vista.getCmbDia().getSelectedItem();
        String mes  = (String) vista.getCmbMes().getSelectedItem();
        String anio = (String) vista.getCmbAnio().getSelectedItem();

        if (dia.equals("Dia") || mes.equals("Mes") || anio.equals("Año")) {
            vista.mostrarError("Debes seleccionar dia, mes y año completos.");
            return;
        }

        // Convierte el nombre del mes a número
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                          "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        int numeroMes = 0;
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equals(mes)) {
                numeroMes = i + 1;
            }
        }

        // Construye la fecha y verifica que sea válida
        LocalDate fecha;
        try {
            fecha = LocalDate.of(Integer.parseInt(anio), numeroMes, Integer.parseInt(dia));
        } catch (Exception ex) {
            vista.mostrarError("La fecha no existe (ej: 31 de Febrero no existe).");
            return;
        }

        // La fecha de ingreso debe ser hoy
        if (!fecha.isEqual(LocalDate.now())) {
            vista.mostrarError("La fecha de ingreso debe ser el dia de hoy.");
            return;
        }

        // 5. Validar descripción
        String descripcion = vista.getTxtDescripcion().getText().trim();
        if (descripcion.isEmpty()) {
            vista.mostrarError("El campo Descripcion es obligatorio.");
            return;
        }
        if (descripcion.length() < 10) {
            vista.mostrarError("La descripcion debe tener al menos 10 caracteres.");
            return;
        }

        // 6. Buscar los IDs reales del cliente y técnico en la BD
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

        // 7. Guardar la prenda en la BD
        boolean guardado = modeloPrenda.registrar(tipo, descripcion, fecha.toString(), idCliente, idTecnico);

        if (guardado) {
            vista.mostrarExito("Prenda registrada exitosamente.");
            vista.limpiarFormulario();

            // Recarga la tabla para mostrar la prenda recién guardada
            cargarTablaPrendas();

            // Avisa al supervisor para que recargue su tabla también
            if (controladorSupervisor != null) {
                controladorSupervisor.recargarPrendas();
            }

        } else {
            vista.mostrarError("Ocurrio un error al guardar la prenda.");
        }
    }
}