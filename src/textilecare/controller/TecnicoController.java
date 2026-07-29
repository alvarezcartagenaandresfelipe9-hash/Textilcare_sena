package textilecare.controller;

// Importa el modelo Prenda para gestionar los datos y consultas de las prendas de ropa
import textilecare.model.Prenda;
// Importa la vista principal del panel del técnico
import textilecare.view.TecnicoView;
// Importa la vista del formulario para registrar o actualizar la reparación de una prenda
import textilecare.view.FormularioReparacionView;

// Importa un adaptador para manejar eventos de clics y acciones del ratón en la interfaz
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// Importa la interfaz List para manejar colecciones de prendas de forma ordenada
import java.util.List;

public class TecnicoController {

    private TecnicoView vista;
    private Prenda modelo;
    private int idTecnico;

    // Constructor usado cuando inicia sesión un técnico de forma normal
    public TecnicoController(TecnicoView vista, int idTecnico) {
        this.vista = vista;
        this.modelo = new Prenda();
        this.idTecnico = idTecnico;

        cargarPrendas();
        agregarListeners();
    }

    // Constructor temporal para evitar excepciones cuando se instancia sin un técnico activo
    public TecnicoController(TecnicoView vista, String nombreSupervisor) {
        this.vista = vista;
        this.modelo = new Prenda();

        // No existe un técnico seleccionado en esta modalidad
        this.idTecnico = 0;

        agregarListeners();
    }

    // Carga en la tabla las prendas que se encuentran asignadas específicamente a este técnico
    private void cargarPrendas() {

        vista.limpiarTabla();

        // Si no hay un ID de técnico válido, detiene la carga
        if (idTecnico <= 0) {
            return;
        }

        List<Prenda> lista = modelo.listarPorTecnico(idTecnico);

        for (Prenda p : lista) {
            vista.agregarFila(
                    p.getId(),
                    p.getTipo(),
                    p.getNombreCliente(),
                    p.getFecha(),
                    p.getEstado());
        }
    }

    // Limpia y vuelve a cargar la tabla para reflejar los datos más recientes
    public void recargarPrendas() {
        cargarPrendas();
    }

    // Configura los escuchadores de eventos, como los clics sobre las filas de la tabla
    private void agregarListeners() {

        vista.getTabla().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = vista.getFilaSeleccionada();

                // Si se selecciona una fila válida y hay un técnico activo, abre el formulario
                if (fila >= 0 && idTecnico > 0) {
                    abrirFormulario(fila);
                }

            }
        });

    }

    // Prepara e instancia la ventana del formulario de reparación para la prenda seleccionada
    private void abrirFormulario(int fila) {

        int idPrenda = vista.getIdPrenda(fila);
        String tipoPrenda = vista.getTipoPrenda(fila);

        FormularioReparacionView vistaFormulario =
                new FormularioReparacionView(tipoPrenda);

        new FormularioReparacionController(
                vistaFormulario,
                idPrenda,
                idTecnico,
                this);

        vistaFormulario.setVisible(true);

    }

}