import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDF_Scanner {
    final static String INPUT_FILE = "resources\\pdfs\\01.pdf";
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
        String estructura = tree.getTree(doc);

        // fuentes
        HashMap<String, String[]> fuentes = fonts.getFuentes(doc);

        // sacar contenido ...
        List<String> contenido = new ArrayList<>();
        for (PDPage page : doc.getPages()) {
            contenido.addAll(textAndFormat.getContenido(page));
        }

        try {
            doc.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar el documento");
        }
    }
}
