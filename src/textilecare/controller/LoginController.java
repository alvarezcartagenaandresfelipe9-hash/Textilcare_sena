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
    private Usuario modelo;

    public LoginController(LoginView vista) {
        this.vista = vista;
        this.modelo = new Usuario();
        agregarListeners();
    }

    private void agregarListeners() {
        vista.getBtnContinuar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intentarLogin();
            }
        });
    }

    private void intentarLogin() {
        String documento  = vista.getDocumento();
        String contrasena = vista.getContrasena();
        String rol        = vista.getRol();

        if (documento.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarMensaje("Completa todos los campos.");
            return;
        }

        Usuario usuario = modelo.buscarUsuario(documento, contrasena, rol);

        if (usuario == null) {
            vista.mostrarMensaje("Documento, contraseña o rol incorrectos.");
            return;
        }

        if (usuario.getEstado().equalsIgnoreCase("Inactivo")) {
            vista.mostrarMensaje("Tu usuario esta inactivo.");
            return;
        }

        vista.limpiarMensaje();
        vista.dispose();
        abrirModulo(usuario);
    }

    private void abrirModulo(Usuario usuario) {
        if (usuario.getRol().equalsIgnoreCase("Cliente")) {
            ClienteView vistaCliente = new ClienteView(usuario.getNombre());
            new ClienteController(vistaCliente, usuario.getId());
            vistaCliente.setVisible(true);

        } else if (usuario.getRol().equalsIgnoreCase("Tecnico")) {
            TecnicoView vistaTecnico = new TecnicoView(usuario.getNombre());
            new TecnicoController(vistaTecnico, usuario.getId());
            vistaTecnico.setVisible(true);

        } else if (usuario.getRol().equalsIgnoreCase("Supervisor")) {
            SupervisorView vistaSupervisor = new SupervisorView(usuario.getNombre());
            new SupervisorController(vistaSupervisor, usuario.getNombre());
            vistaSupervisor.setVisible(true);

        } else if (usuario.getRol().equalsIgnoreCase("Vendedor")) {
            VendedorView vistaVendedor = new VendedorView(usuario.getNombre());
            new VendedorController(vistaVendedor, usuario.getId(), usuario.getNombre());
            vistaVendedor.setVisible(true);

        } else if (usuario.getRol().equalsIgnoreCase("Administrador")) {
            AdministradorView vistaAdmin = new AdministradorView(usuario.getNombre());
            new AdministradorController(vistaAdmin);
            vistaAdmin.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
        }
    }
}