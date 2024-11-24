package experimentos;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class destinations {
    final static String INPUT_PATH = "resources\\pdfs\\02.pdf";

    public static void main(String[] args) throws IOException {
        // cargar documento
        File file = new File(INPUT_PATH);
        PDDocument doc = Loader.loadPDF(file);

        if (doc.getDocumentCatalog().getDocumentOutline().hasChildren()) {
            Iterator<PDOutlineItem> it = doc.getDocumentCatalog().getDocumentOutline().children().iterator();
            while (it.hasNext()) {
                PDOutlineItem i = it.next();
                System.out.println(i.getTitle());
                if (i.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) i.getDestination();
                    System.out.println("Destination page: " + (pd.retrievePageNumber() + 1));
                } else if (i.getAction() instanceof PDActionGoTo) {
                    PDActionGoTo gta = (PDActionGoTo) i.getAction();
                    System.out.println("Destination page: " + gta.getDestination().getCOSObject());
                }
            }
        }

        doc.close();
    }
}
