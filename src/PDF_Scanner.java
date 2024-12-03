import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDF_Scanner {
    // TODO: ruta del fichero como parametro de entrada
    final static String INPUT_FILE = "resources\\pdfs\\04.pdf";
    public static void main(String[] args) {
        // abrir fichero
        File file = new File(INPUT_FILE);
        PDDocument doc;
        try {
            doc = Loader.loadPDF(file);
        } catch (IOException e) {
            System.err.println("Error al cargar el documento.");
            return;
        }

        // sacar titulo
        String titulo = title.getTitle(doc);

        // sacar estructura
        // String estructura = tree.getTree(doc);

        // fuentes
        HashMap<String, String[]> fuentes = fonts.getFuentes(doc);

        // sacar contenido ...
        List<Content> contenido = new ArrayList<>();

        for (PDPage page : doc.getPages()) {
            contenido.addAll(textAndFormat.getContenido(page));
        }

        // contenido.addAll(textAndFormat.getContenido(doc.getPage(0)));

        toFile.toFile(contenido.toString(), "output\\content.txt");

        try {
            doc.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar el documento");
        }
    }
}
