package textilecare.controller;

import textilecare.model.Usuario;
import textilecare.view.AdministradorView;
import textilecare.view.RegistroUsuarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.List;

import textilecare.view.LoginView;
import textilecare.controller.LoginController;

public class AdministradorController {

    private AdministradorView vista;
    private Usuario modelo;
    private String rolActual;

    public AdministradorController(AdministradorView vista) {
        this.vista = vista;
        this.modelo = new Usuario();
        this.rolActual = "Cliente";

        agregarListeners();
        mostrarClientes();
    }

    private void agregarListeners() {

        // Boton "← Volver": regresa al Login
        vista.getBtnVolver().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlLogin();
            }
        });


        // Boton "Clientes"
        vista.getBtnClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        // Boton "Supervisores"
        vista.getBtnSupervisores().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSupervisores();
            }
        });

        // Boton "Tecnicos"
        vista.getBtnTecnicos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTecnicos();
            }
        });

        // Boton "Vendedores"
        vista.getBtnVendedores().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVendedores();
            }
        });

        // Boton "+ Registrar"
        vista.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormulario();
            }
        });

        // Cada tecla soltada en el buscador dispara la busqueda
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });
    }

    private void mostrarClientes() {
        rolActual = "Cliente";
        vista.configurarTabla("Clientes Registrados");
        cargarUsuarios(rolActual);
    }

    private void mostrarSupervisores() {
        rolActual = "Supervisor";
        vista.configurarTabla("Supervisores Registrados");
        cargarUsuarios(rolActual);
    }

    private void mostrarTecnicos() {
        rolActual = "Tecnico";
        vista.configurarTabla("Tecnicos Registrados");
        cargarUsuarios(rolActual);
    }

    private void mostrarVendedores() {
        rolActual = "Vendedor";
        vista.configurarTabla("Vendedores Registrados");
        cargarUsuarios(rolActual);
    }

    private void cargarUsuarios(String rol) {
        List<Usuario> lista = modelo.listarPorRolCompleto(rol);

        for (Usuario u : lista) {
            vista.agregarFila(u.getNombre(), u.getDocumento(), u.getCorreo(), u.getTelefono(), u.getEstado());
        }
    }

    private void buscar() {
        String texto = vista.getTxtBuscar().getText().trim();

        vista.configurarTabla(tituloSegunRol());

        List<Usuario> lista;

        if (texto.isEmpty()) {
            lista = modelo.listarPorRolCompleto(rolActual);
        } else {
            lista = modelo.buscarPorTextoYRol(texto, rolActual);
        }

        for (Usuario u : lista) {
            vista.agregarFila(u.getNombre(), u.getDocumento(), u.getCorreo(), u.getTelefono(), u.getEstado());
        }
    }

    private String tituloSegunRol() {
        switch (rolActual) {
            case "Cliente":
                return "Clientes Registrados";
            case "Supervisor":
                return "Supervisores Registrados";
            case "Tecnico":
                return "Tecnicos Registrados";
            case "Vendedor":
                return "Vendedores Registrados";
            default:
                return "Usuarios Registrados";
        }
    }

    private void abrirFormulario() {
        RegistroUsuarioView vistaRegistro = new RegistroUsuarioView(rolActual);
        new RegistroUsuarioController(vistaRegistro, this, rolActual);
        vistaRegistro.setVisible(true);
    }

    public void recargarSeccionActual() {
        vista.configurarTabla(tituloSegunRol());
        cargarUsuarios(rolActual);
    }

    public String getRolActual() {
        return rolActual;
    }

    // Cierra esta ventana y regresa a la pantalla de Login
    private void volverAlLogin() {
        vista.dispose();
        LoginView login = new LoginView();
        new LoginController(login);
        login.setVisible(true);
    }

}