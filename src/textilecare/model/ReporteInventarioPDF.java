package textilecare.model;

// Importa las clases principales de OpenPDF para la creación de documentos, párrafos, fuentes y diseño
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Font;
import org.openpdf.text.Element;
import org.openpdf.text.Phrase;
// Importa las clases de OpenPDF específicas para la creación de archivos PDF, tablas y celdas
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfPCell;

// Importa clases para la gestión de colores y el manejo de flujos de salida de archivos
import java.awt.Color;
import java.io.FileOutputStream;
// Importa la interfaz List para manejar la colección de productos
import java.util.List;

public class ReporteInventarioPDF {

    // Método principal que genera un reporte en PDF del inventario de productos utilizando OpenPDF
    public boolean generarReporte(List<Producto> productos,
                                String nombreVendedor,
                                String fecha,
                                String rutaArchivo) {

        try {

            // Inicializa el documento PDF y configura el escritor de archivos
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Configura la fuente y estilo para el título principal del reporte
            Font fuenteTitulo = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(90, 58, 35));

            Paragraph titulo = new Paragraph("Reporte de Inventario - TextilCare", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Añade un espacio en blanco de separación
            documento.add(new Paragraph(" "));

            // Agrega la información del vendedor y la fecha actual del reporte
            documento.add(new Paragraph("Vendedor: " + nombreVendedor));
            documento.add(new Paragraph("Fecha: " + fecha));

            // Añade otro espacio en blanco antes de la tabla
            documento.add(new Paragraph(" "));

            // Crea una tabla de 4 columnas configurada para ocupar todo el ancho de la página
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            // Agrega los encabezados correspondientes a la tabla de inventario
            agregarEncabezado(tabla, "Producto");
            agregarEncabezado(tabla, "Talla");
            agregarEncabezado(tabla, "Stock");
            agregarEncabezado(tabla, "Precio");

            // Recorre la lista de productos para rellenar las filas de la tabla con sus datos
            for (Producto p : productos) {

                tabla.addCell(p.getNombre());
                tabla.addCell(p.getTalla());
                tabla.addCell(String.valueOf(p.getStock()));
                tabla.addCell("$ " + p.getPrecio());

            }

            // Añade la tabla completada al documento y cierra el flujo
            documento.add(tabla);
            documento.close();

            return true;

        } catch (Exception ex) {

            System.out.println("Error al generar PDF: " + ex.getMessage());
            ex.printStackTrace();

            return false;
        }

    }

    // Método auxiliar para estilizar y agregar celdas de encabezado con fondo de color y padding
    private void agregarEncabezado(PdfPTable tabla, String texto) {

        Font fuente = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);

        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));

        celda.setBackgroundColor(new Color(90, 58, 35));
        celda.setPadding(6);

        tabla.addCell(celda);

    }

}