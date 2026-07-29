package textilecare.controller;

import textilecare.model.Producto;
import textilecare.model.Reparacion;
import textilecare.view.FormularioReparacionView;
import textilecare.view.TecnicoView;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.util.List;

public class FormularioReparacionController {

    private FormularioReparacionView vista;
    private Producto modeloProducto;
    private Reparacion modeloReparacion;

    private int idPrenda;
    private int idTecnico;
    private TecnicoController controladorTecnico;

    private final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    public FormularioReparacionController(FormularioReparacionView vista, int idPrenda,
            int idTecnico, TecnicoController controladorTecnico) {

        this.vista = vista;
        this.modeloProducto = new Producto();
        this.modeloReparacion = new Reparacion();
        this.idPrenda = idPrenda;
        this.idTecnico = idTecnico;
        this.controladorTecnico = controladorTecnico;

        cargarProductos();
        agregarListeners();
    }

    private void cargarProductos() {
        List<String> nombres = modeloProducto.listarNombresDisponibles();
        for (String nombre : nombres) {
            vista.getCmbProducto().addItem(nombre);
        }
    }

    private void agregarListeners() {

        // Boton "+ Agregar" material
        vista.getBtnAgregarMaterial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMaterial();
            }
        });

        // Boton "Eliminar seleccionado"
        vista.getBtnEliminarMaterial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMaterialSeleccionado();
            }
        });

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

    private void agregarMaterial() {
        String producto = (String) vista.getCmbProducto().getSelectedItem();
        String cantidadTexto = vista.getTxtCantidad().getText().trim();

        if (producto == null || producto.isEmpty()) {
            vista.mostrarError("Selecciona un producto.");
            return;
        }
        if (cantidadTexto.isEmpty()) {
            vista.mostrarError("Ingresa una cantidad.");
            return;
        }
        if (!cantidadTexto.matches("[0-9]+")) {
            vista.mostrarError("La cantidad solo puede contener numeros.");
            return;
        }
        if (Integer.parseInt(cantidadTexto) == 0) {
            vista.mostrarError("La cantidad debe ser mayor a 0.");
            return;
        }

        vista.agregarFilaMaterial(producto, cantidadTexto);
        vista.getTxtCantidad().setText("");
    }

    private void eliminarMaterialSeleccionado() {
        int fila = vista.getTablaMateriales().getSelectedRow();
        if (fila >= 0) {
            vista.getModeloMateriales().removeRow(fila);
        } else {
            vista.mostrarError("Selecciona un material para eliminar.");
        }
    }

    private void guardar() {
        String estado = (String) vista.getCmbEstado().getSelectedItem();
        if (estado == null || estado.equals("-- Seleccione --")) {
            vista.mostrarError("Debes seleccionar un estado.");
            return;
        }

        LocalDate fechaInicio = leerFecha(vista.getCmbDiaInicio(), vista.getCmbMesInicio(), vista.getCmbAnioInicio(), "Debes seleccionar la fecha de inicio completa.");
        if (fechaInicio == null) {
            return;
        }

        LocalDate fechaFin = leerFecha(vista.getCmbDiaFin(), vista.getCmbMesFin(), vista.getCmbAnioFin(), "Debes seleccionar la fecha de fin completa.");
        if (fechaFin == null) {
            return;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            vista.mostrarError("La fecha de fin no puede ser anterior a la de inicio.");
            return;
        }

        if (!validarFotosSegunEstado(estado)) {
            return;
        }

        DefaultTableModel modeloMateriales = vista.getModeloMateriales();
        if (estado.equals("Reparada") && modeloMateriales.getRowCount() == 0) {
            vista.mostrarError("Debes agregar al menos un material usado.");
            return;
        }

        int idReparacion = modeloReparacion.guardar(idPrenda, fechaInicio, fechaFin, estado);

        guardarFotoSiExiste(estado, vista.getFoto1());
        guardarFotoSiExiste(estado, vista.getFoto2());
        guardarFotoSiExiste(estado, vista.getFoto3());

        if (estado.equals("Reparada")) {
            if (!guardarMateriales(idReparacion, modeloMateriales)) {
                return;
            }
        }

        modeloReparacion.actualizarEstadoPrenda(idPrenda, estado);

        vista.mostrarExito("Reparacion guardada exitosamente.");

        controladorTecnico.recargarPrendas();

        vista.dispose();
    }

    private LocalDate leerFecha(JComboBox<String> cmbDia, JComboBox<String> cmbMes, JComboBox<String> cmbAnio, String mensajeError) {
        String dia = (String) cmbDia.getSelectedItem();
        String mes = (String) cmbMes.getSelectedItem();
        String anio = (String) cmbAnio.getSelectedItem();

        if (dia.equals("Dia") || mes.equals("Mes") || anio.equals("Año")) {
            vista.mostrarError(mensajeError);
            return null;
        }

        int numeroMes = 0;
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equals(mes)) {
                numeroMes = i + 1;
            }
        }

        try {
            return LocalDate.of(Integer.parseInt(anio), numeroMes, Integer.parseInt(dia));
        } catch (Exception ex) {
            vista.mostrarError("Una de las fechas no existe.");
            return null;
        }
    }

    private boolean validarFotosSegunEstado(String estado) {
        byte[] foto1 = vista.getFoto1();
        byte[] foto2 = vista.getFoto2();
        byte[] foto3 = vista.getFoto3();

        if (estado.equals("Pendiente") && foto1 == null) {
            vista.mostrarError("Debes subir 1 foto para el estado Pendiente.");
            return false;
        }
        if (estado.equals("En proceso") && (foto1 == null || foto2 == null)) {
            vista.mostrarError("Debes subir 2 fotos para el estado En proceso.");
            return false;
        }
        if (estado.equals("Reparada") && (foto1 == null || foto2 == null || foto3 == null)) {
            vista.mostrarError("Debes subir 3 fotos para el estado Reparada.");
            return false;
        }
        return true;
    }

    private void guardarFotoSiExiste(String estado, byte[] foto) {
        if (foto != null) {
            modeloReparacion.guardarFoto(idPrenda, estado, foto);
        }
    }

    private boolean guardarMateriales(int idReparacion, DefaultTableModel modeloMateriales) {
        for (int i = 0; i < modeloMateriales.getRowCount(); i++) {
            String nombreProducto = modeloMateriales.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(modeloMateriales.getValueAt(i, 1).toString());

            Producto producto = modeloProducto.buscarPorNombre(nombreProducto);

            if (producto == null) {
                continue;
            }

            if (cantidad > producto.getStock()) {
                vista.mostrarError("No hay suficiente stock de: " + nombreProducto
                        + "\nStock disponible: " + producto.getStock());
                return false;
            }

            modeloReparacion.guardarMaterialUsado(idReparacion, producto.getId(), cantidad);
            modeloProducto.descontarStock(producto.getId(), cantidad, producto.getStock());
        }
        return true;
    }
}