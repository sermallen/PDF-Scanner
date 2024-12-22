package experimentos;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class destinations {
    final static String INPUT_PATH = "resources\\pdfs\\04.pdf";

    public static void main(String[] args) throws IOException {
        // cargar documento
        File file = new File(INPUT_PATH);
        PDDocument doc = Loader.loadPDF(file);

        if (doc.getDocumentCatalog().getDocumentOutline().hasChildren()) {
            Iterator<PDOutlineItem> it = doc.getDocumentCatalog().getDocumentOutline().children().iterator();
            getOutline(it);
        }
        doc.close();
    }

    private static void getOutline(Iterator<PDOutlineItem> it) throws IOException {
        while (it.hasNext()) {
            PDOutlineItem i = it.next();
            System.out.println(i.getTitle());
            
            if (i.getDestination() instanceof PDPageDestination) {
                PDPageDestination pd = (PDPageDestination) i.getDestination();
                System.out.println("Destination page: " + (pd.retrievePageNumber() + 1));
            } else if (i.getAction() instanceof PDActionGoTo) {
                PDActionGoTo gta = (PDActionGoTo) i.getAction();
                System.out.println("Destination page: " + gta.getDestination().getCOSObject());
            } else if (i.getDestination() instanceof PDNamedDestination) {
                PDNamedDestination nd = (PDNamedDestination) i.getDestination();
                System.out.println("Destination page: " + (nd.getNamedDestination()));
            }

            if (i.children() != null) {
                getOutline(i.children().iterator());
            }
        }
    }
}
