package experimentos;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDestinationNameTreeNode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;

public class namedDestinations {
    final static String INPUT_PATH = "resources\\pdfs\\04.pdf";

    public static void main(String[] args) throws IOException {
        // cargar documento
        File file = new File(INPUT_PATH);
        PDDocument doc = Loader.loadPDF(file);

        Map<String, PDPageDestination> filterSections = filterSections(getAllNamedDestinations(doc));

        System.out.println(filterSections);
    }


    // https://stackoverflow.com/questions/52185391/java-pdfbox-list-all-named-destinations-of-a-page
    public static Map<String, PDPageDestination> getAllNamedDestinations(PDDocument document) {

        Map<String, PDPageDestination> namedDestinations = new HashMap<>(10);

        // get catalog
        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();

        PDDocumentNameDictionary names = documentCatalog.getNames();

        if (names == null)
            return namedDestinations;

        PDDestinationNameTreeNode dests = names.getDests();

        try {
            if (dests.getNames() != null)
                namedDestinations.putAll(dests.getNames());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<PDNameTreeNode<PDPageDestination>> kids = dests.getKids();

        traverseKids(kids, namedDestinations);

        return namedDestinations;
    }

    private static void traverseKids(List<PDNameTreeNode<PDPageDestination>> kids,
            Map<String, PDPageDestination> namedDestinations) {

        if (kids == null)
            return;

        try {
            for (PDNameTreeNode<PDPageDestination> kid : kids) {
                if (kid.getNames() != null) {
                    try {
                        namedDestinations.putAll(kid.getNames());
                    } catch (Exception e) {
                        System.out.println("INFO: Duplicate named destinations in document.");
                        e.printStackTrace();
                    }
                }

                if (kid.getKids() != null)
                    traverseKids(kid.getKids(), namedDestinations);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, PDPageDestination> filterSections(Map<String, PDPageDestination> namedDestinations) {
        Map<String, PDPageDestination> filtered = new HashMap<String, PDPageDestination>();
        for (Map.Entry<String, PDPageDestination> entry : namedDestinations.entrySet()) {
            if (entry.getKey().toLowerCase().contains("section")) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }
}
