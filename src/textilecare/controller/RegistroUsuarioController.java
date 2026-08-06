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

    // Método para conectar los botones de la vista con las acciones del controlador
    private void agregarListeners() {

        // Botón "Guardar": Cuando se hace clic, ejecuta el método guardar()
        vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        // Botón "Cancelar": Cierra la ventana actual sin guardar nada
        vista.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }

    // Método principal que valida todos los campos antes de enviar los datos al modelo
    private void guardar() {

        // ==========================================
        // 1. VALIDACIÓN DEL NOMBRE
        // ==========================================
        String nombre = vista.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            vista.mostrarError("El nombre es obligatorio.");
            return;
        }
        if (nombre.length() < 3) {
            vista.mostrarError("El nombre debe tener al menos 3 caracteres.");
            return;
        }
        // Valida que solo tenga letras (incluyendo tildes y eñes) y espacios
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            vista.mostrarError("El nombre solo puede contener letras y espacios.");
            return;
        }

        // ==========================================
        // 2. VALIDACIÓN DEL DOCUMENTO (Actualizado a 6-10 dígitos)
        // ==========================================
        String documento = vista.getTxtDocumento().getText().trim();
        if (documento.isEmpty()) {
            vista.mostrarError("El documento es obligatorio.");
            return;
        }
        // Valida que sean exclusivamente números
        if (!documento.matches("[0-9]+")) {
            vista.mostrarError("El documento solo puede contener números, sin signos ni letras.");
            return;
        }
        // Restricción solicitada: mínimo 6 y máximo 10 dígitos
        if (documento.length() < 6 || documento.length() > 10) {
            vista.mostrarError("El documento debe tener entre 6 y 10 dígitos.");
            return;
        }

        // ==========================================
        // 3. VALIDACIÓN DEL CORREO (Más estricta)
        // ==========================================
        String correo = vista.getTxtCorreo().getText().trim();
        if (correo.isEmpty()) {
            vista.mostrarError("El correo es obligatorio, registre un correo válido.");
            return;
        }
        
        // Validación mejorada para evitar errores comunes:
        // - Evita que empiece o termine con un punto.
        // - Evita puntos seguidos (..).
        // - Evita que haya un punto justo antes o después del arroba (ej: .@ o @.).
        String patronCorreo = "^[a-zA-Z0-9]([a-zA-Z0-9._%+-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9.-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$";
        
        if (!correo.matches(patronCorreo) || correo.contains("..")) {
            vista.mostrarError("El correo no tiene un formato válido (ej: usuario@dominio.com, sin puntos mal ubicados).");
            return;
        }

        // ==========================================
        // 4. VALIDACIÓN DEL TELÉFONO (Opcional)
        // ==========================================
        String telefono = vista.getTxtTelefono().getText().trim();
        if (!telefono.isEmpty()) {
            if (!telefono.matches("[0-9]+")) {
                vista.mostrarError("El teléfono solo puede contener números.");
                return;
            }
            if (telefono.length() < 7 || telefono.length() > 10) {
                vista.mostrarError("El teléfono debe tener entre 7 y 10 dígitos.");
                return;
            }
        }

        // ==========================================
        // 5. VALIDACIÓN DE LA CONTRASEÑA
        // ==========================================
        String contrasena = new String(vista.getTxtContrasena().getPassword()).trim();
        if (contrasena.isEmpty()) {
            vista.mostrarError("La contraseña es obligatoria.");
            return;
        }
        if (contrasena.length() < 4) {
            vista.mostrarError("La contraseña debe tener al menos 4 caracteres.");
            return;
        }

        // ==========================================
        // 6. REGISTRO EN LA BASE DE DATOS
        // ==========================================
        boolean guardado = modelo.registrar(nombre, documento, correo, telefono, contrasena, rol);

        if (guardado) {
            vista.mostrarExito(rol + " registrado correctamente.");
            vista.limpiarFormulario();
            controladorAdmin.recargarSeccionActual(); // Actualiza la tabla del administrador
            vista.dispose(); // Cierra la ventana de registro
        } else {
            vista.mostrarError("Ocurrió un error al registrar el usuario. Verifica que el documento no esté repetido.");
        }
    }
}