package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.view.RegistroUsuarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuarioController {

    private RegistroUsuarioView vista;
    private Usuario modelo;
    private AdministradorController controladorAdmin;
    private String rol;

    public RegistroUsuarioController(RegistroUsuarioView vista, AdministradorController controladorAdmin, String rol) {
        this.vista = vista;
        this.modelo = new Usuario();
        this.controladorAdmin = controladorAdmin;
        this.rol = rol;
        agregarListeners();
    }

    private void agregarListeners() {

        // Boton "Guardar": el ActionEvent 'e' avisa que hubo clic, y ahi llamamos a guardar()
        vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        // Boton "Cancelar": el ActionEvent 'e' avisa el clic, y cerramos la ventana
        vista.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }

    private void guardar() {

        // 1. Nombre: obligatorio, solo letras y espacios, minimo 3 caracteres
        String nombre = vista.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            vista.mostrarError("El nombre es obligatorio.");
            return;
        }
        if (nombre.length() < 3) {
            vista.mostrarError("El nombre debe tener al menos 3 caracteres.");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            vista.mostrarError("El nombre solo puede contener letras y espacios.");
            return;
        }

        // 2. Documento: obligatorio, solo numeros (sin signos ni letras), entre 6 y 15 digitos
        String documento = vista.getTxtDocumento().getText().trim();
        if (documento.isEmpty()) {
            vista.mostrarError("El documento es obligatorio.");
            return;
        }
        if (!documento.matches("[0-9]+")) {
            vista.mostrarError("El documento solo puede contener numeros, sin signos ni letras.");
            return;
        }
        if (documento.length() < 6 || documento.length() > 15) {
            vista.mostrarError("El documento debe tener entre 6 y 15 digitos.");
            return;
        }

        // 3. Correo: obligatorio, con formato valido (algo@algo.algo)
        String correo = vista.getTxtCorreo().getText().trim();
        if (correo.isEmpty()) {
            vista.mostrarError("El correo es obligatorio,registre un correo valido.");
            return;
        }
        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            vista.mostrarError("El correo no tiene un formato valido. Ejemplo: nombre@dominio.com");
            return;
        }

        // 4. Telefono: opcional, pero si se llena debe ser solo numeros de 7 a 10 digitos
        String telefono = vista.getTxtTelefono().getText().trim();
        if (!telefono.isEmpty()) {
            if (!telefono.matches("[0-9]+")) {
                vista.mostrarError("El telefono solo puede contener numeros.");
                return;
            }
            if (telefono.length() < 7 || telefono.length() > 10) {
                vista.mostrarError("El telefono debe tener entre 7 y 10 digitos.");
                return;
            }
        }

        // 5. Contrasena: obligatoria, minimo 4 caracteres
        String contrasena = new String(vista.getTxtContrasena().getPassword()).trim();
        if (contrasena.isEmpty()) {
            vista.mostrarError("La contrasena es obligatoria.");
            return;
        }
        if (contrasena.length() < 4) {
            vista.mostrarError("La contrasena debe tener al menos 4 caracteres.");
            return;
        }

        // 6. Si todo paso las validaciones, se intenta guardar
        boolean guardado = modelo.registrar(nombre, documento, correo, telefono, contrasena, rol);

        if (guardado) {
            vista.mostrarExito(rol + " registrado correctamente.");
            vista.limpiarFormulario();
            controladorAdmin.recargarSeccionActual();
            vista.dispose();
        } else {
            vista.mostrarError("Ocurrio un error al registrar el usuario. Verifica que el documento no este repetido.");
        }
    }
}