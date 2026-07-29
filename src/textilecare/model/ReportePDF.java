package textilecare.model;

// Importa las clases principales de la librería OpenPDF para estructurar el documento, párrafos, fuentes y diseño general
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Font;
import org.openpdf.text.Element;
import org.openpdf.text.Phrase;
// Importa las clases específicas de OpenPDF para el manejo de la salida en PDF, tablas estructuradas y celdas individuales
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfPCell;

// Importa la clase Color para definir los estilos visuales y de fondo
import java.awt.Color;
// Importa FileOutputStream para la escritura y almacenamiento del archivo PDF en el disco
import java.io.FileOutputStream;
// Importa la interfaz List para manejar la colección de prendas a reportar
import java.util.List;

public class ReportePDF {

    // Genera un PDF con la lista de prendas y lo guarda en la ruta indicada
    public boolean generarReportePrendas(List<Prenda> lista, String rutaArchivo) {
        try {
            // Inicializa una nueva instancia del documento PDF y configura su salida mediante un flujo de archivos
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Configura la fuente, el estilo y el texto centrado para el título principal del reporte de prendas
            Font fuenteTitulo = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(90, 58, 35));
            Paragraph titulo = new Paragraph("Reporte de Prendas - TextilCare", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Añade un espacio en blanco para separar el título de la tabla
            documento.add(new Paragraph(" "));

            // Crea una tabla de 5 columnas configurada para ocupar el 100% del ancho disponible
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);

            // Agrega los encabezados correspondientes para cada columna de la tabla
            agregarEncabezado(tabla, "Prenda");
            agregarEncabezado(tabla, "Tecnico");
            agregarEncabezado(tabla, "Cliente");
            agregarEncabezado(tabla, "Fecha");
            agregarEncabezado(tabla, "Estado");

            // Recorre la colección de prendas para rellenar las celdas con la información respectiva
            for (Prenda p : lista) {
                tabla.addCell(p.getTipo());
                tabla.addCell(p.getNombreTecnico());
                tabla.addCell(p.getNombreCliente());
                tabla.addCell(p.getFecha());
                tabla.addCell(p.getEstado());
            }

            // Agrega la tabla construida al documento y lo cierra correctamente para finalizar la exportación
            documento.add(tabla);
            documento.close();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al generar PDF: " + ex.getMessage());
            return false;
        }
    }

    // Agrega una celda de encabezado con fondo cafe y texto blanco
    private void agregarEncabezado(PdfPTable tabla, String texto) {
        Font fuenteEncabezado = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuenteEncabezado));
        celda.setBackgroundColor(new Color(90, 58, 35));
        celda.setPadding(6);
        tabla.addCell(celda);
    }
}