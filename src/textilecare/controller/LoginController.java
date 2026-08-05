package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.view.LoginView;
import textilecare.view.ClienteView;
import textilecare.view.TecnicoView;
import textilecare.view.SupervisorView;
import textilecare.view.VendedorView;
import textilecare.view.AdministradorView;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView vista;
    private Usuario   modelo;

    public LoginController(LoginView vista) {
        this.vista  = vista;
        this.modelo = new Usuario();
        agregarListeners();
    }

    // Conecta el botón "Continuar" con el método intentarLogin()
    private void agregarListeners() {
        vista.getBtnContinuar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intentarLogin();
            }
        });
    }

    // Valida los campos y verifica las credenciales
    private void intentarLogin() {

        String documento  = vista.getDocumento();
        String contrasena = vista.getContrasena();

        // Validar que ningún campo esté vacío
        if (documento.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarMensaje("Completa todos los campos.");
            return;
        }

        // Buscar el usuario solo con documento y contraseña
        // Ya NO se pasa el rol — la BD lo devuelve automáticamente
        Usuario usuario = modelo.buscarUsuario(documento, contrasena);

        // Si no encontró ningún usuario con esas credenciales
        if (usuario == null) {
            vista.mostrarMensaje("Documento o contraseña incorrectos.");
            return;
        }

        // Si el usuario existe pero está desactivado
        if (usuario.getEstado().equalsIgnoreCase("Inactivo")) {
            vista.mostrarMensaje("Tu usuario esta inactivo.");
            return;
        }

        // Todo correcto: cerrar login y abrir el módulo del rol correspondiente
        vista.limpiarMensaje();
        vista.dispose();
        abrirModulo(usuario);
    }

    // Abre la pantalla correcta según el rol que devolvió la base de datos
    private void abrirModulo(Usuario usuario) {

        String rol = usuario.getRol();

        if (rol.equalsIgnoreCase("Cliente")) {
            ClienteView vistaCliente = new ClienteView(usuario.getNombre());
            new ClienteController(vistaCliente, usuario.getId());
            vistaCliente.setVisible(true);

        } else if (rol.equalsIgnoreCase("Tecnico")) {
            TecnicoView vistaTecnico = new TecnicoView(usuario.getNombre());
            new TecnicoController(vistaTecnico, usuario.getId());
            vistaTecnico.setVisible(true);

        } else if (rol.equalsIgnoreCase("Supervisor")) {
            SupervisorView vistaSupervisor = new SupervisorView(usuario.getNombre());
            new SupervisorController(vistaSupervisor, usuario.getNombre());
            vistaSupervisor.setVisible(true);

        } else if (rol.equalsIgnoreCase("Vendedor")) {
            VendedorView vistaVendedor = new VendedorView(usuario.getNombre());
            new VendedorController(vistaVendedor, usuario.getId(), usuario.getNombre());
            vistaVendedor.setVisible(true);

        } else if (rol.equalsIgnoreCase("Administrador")) {
            AdministradorView vistaAdmin = new AdministradorView(usuario.getNombre());
            new AdministradorController(vistaAdmin);
            vistaAdmin.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
        }
    }
}