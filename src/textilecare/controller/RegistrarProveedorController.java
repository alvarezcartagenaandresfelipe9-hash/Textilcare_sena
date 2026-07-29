package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.model.Proveedor;
import textilecare.view.RegistrarProveedorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarProveedorController {

    private RegistrarProveedorView vista;
    private Usuario modeloUsuario;
    private Proveedor modeloProveedor;
    private ProveedorController controladorProveedor;
    private String nombreSupervisor;

    public RegistrarProveedorController(RegistrarProveedorView vista, ProveedorController controladorProveedor, String nombreSupervisor) {
        this.vista = vista;
        this.modeloUsuario = new Usuario();
        this.modeloProveedor = new Proveedor();
        this.controladorProveedor = controladorProveedor;
        this.nombreSupervisor = nombreSupervisor;
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
        String empresa = vista.getTxtEmpresa().getText().trim();
        if (empresa.isEmpty()) {
            vista.mostrarError("El nombre de la empresa es obligatorio.");
            return;
        }
        if (empresa.length() < 3) {
            vista.mostrarError("El nombre de la empresa debe tener al menos 3 caracteres.");
            return;
        }

        String nit = vista.getTxtNit().getText().trim();
        if (nit.isEmpty()) {
            vista.mostrarError("El NIT es obligatorio.");
            return;
        }
        if (!nit.matches("[0-9]+")) {
            vista.mostrarError("El NIT solo puede contener numeros.");
            return;
        }

        String correo = vista.getTxtCorreo().getText().trim();
        if (correo.isEmpty()) {
            vista.mostrarError("El correo es obligatorio.");
            return;
        }
        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            vista.mostrarError("El correo no es valido. Ejemplo correcto: nombre@dominio.com");
            return;
        }

        String telefono = vista.getTxtTelefono().getText().trim();
        if (telefono.isEmpty()) {
            vista.mostrarError("El telefono es obligatorio.");
            return;
        }
        if (!telefono.matches("[0-9]+")) {
            vista.mostrarError("El telefono solo puede contener numeros.");
            return;
        }
        if (telefono.length() != 10) {
            vista.mostrarError("El telefono debe tener exactamente 10 digitos.");
            return;
        }

        String productos = vista.getTxtProductos().getText().trim();
        if (productos.isEmpty()) {
            vista.mostrarError("El campo productos es obligatorio.");
            return;
        }
        if (!productos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            vista.mostrarError("El campo productos solo puede contener letras y espacios.");
            return;
        }

        int idSupervisor = modeloUsuario.buscarIdPorNombreYRol(nombreSupervisor, "Supervisor");
        if (idSupervisor == 0) {
            vista.mostrarError("No se pudo identificar al supervisor.");
            return;
        }

        boolean guardado = modeloProveedor.registrar(empresa, nit, telefono, correo, productos, idSupervisor);

        if (guardado) {
            vista.mostrarExito("Proveedor registrado exitosamente.");
            vista.limpiarFormulario();
            controladorProveedor.recargarDatos();
        } else {
            vista.mostrarError("Ocurrio un error al guardar el proveedor.");
        }
    }
}