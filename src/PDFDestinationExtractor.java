import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDDestinationNameTreeNode;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitHeightDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitRectangleDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFDestinationExtractor {
    public static ArrayList<Estructura> getEstructura(PDDocument doc) {
        // Obtener y filtrar destinos nombrados
        Map<String, PDPageDestination> filterSections = filterSections(getAllNamedDestinations(doc));

        // Convertir los destinos en una lista y ordenar por número de página
        List<NamedDestination> destinations = new ArrayList<>();
        for (Map.Entry<String, PDPageDestination> entry : filterSections.entrySet()) {
            destinations.add(new NamedDestination(entry.getKey(), entry.getValue()));
        }

        // Ordenar la lista por número de página
        destinations.sort(Comparator.comparing(NamedDestination::getName));

        ArrayList<Estructura> estruct = new ArrayList<>();

        // Mostrar destinos con coordenadas
        for (NamedDestination destination : destinations) {
            PDPageDestination pd = destination.getDestination();
            if (pd instanceof PDPageXYZDestination) {
                PDPageXYZDestination xyz = (PDPageXYZDestination) pd;
                estruct.add(new Estructura(destination.getName(), destination.getPageNumber(), xyz.getLeft(),
                        xyz.getTop()));
                // TODO: comprobar si coords deberian ser 0 (no deberian)
            } else if (pd instanceof PDPageFitDestination) {
                PDPageFitDestination f = (PDPageFitDestination) pd;
                estruct.add(new Estructura(destination.getName(), destination.getPageNumber(), 0, 0));
            } else if (pd instanceof PDPageFitHeightDestination) {
                PDPageFitHeightDestination h = (PDPageFitHeightDestination) pd;
                estruct.add(new Estructura(destination.getName(), destination.getPageNumber(), 0, h.getLeft()));
            } else if (pd instanceof PDPageFitRectangleDestination) {
                System.out.println("PageFitRectangle");
                PDPageFitRectangleDestination r = (PDPageFitRectangleDestination) pd;
                estruct.add(
                        new Estructura(destination.getName(), destination.getPageNumber(), r.getTop(), r.getLeft()));
            } else if (pd instanceof PDPageFitWidthDestination) {
                PDPageFitWidthDestination w = (PDPageFitWidthDestination) pd;
                estruct.add(new Estructura(destination.getName(), destination.getPageNumber(), w.getTop(), 0));
            }
        }
        return estruct;
    }

    public static Map<String, PDPageDestination> getAllNamedDestinations(PDDocument document) {
        Map<String, PDPageDestination> namedDestinations = new HashMap<>();

        // Obtener el catálogo
        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();

        PDDocumentNameDictionary names = documentCatalog.getNames();
        if (names == null)
            return namedDestinations;

        PDDestinationNameTreeNode dests = names.getDests();

        try {
            if (dests.getNames() != null)
                namedDestinations.putAll(dests.getNames());
        } catch (Exception e) {
            System.err.println("Error al obtener el outline");
            return namedDestinations;
        }

        // Recorrer nodos secundarios
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
                        System.out.println("INFO: Destinos nombrados duplicados en el documento.");
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
        Map<String, PDPageDestination> filtered = new HashMap<>();
        for (Map.Entry<String, PDPageDestination> entry : namedDestinations.entrySet()) {
            if (entry.getKey().toLowerCase().contains("section.")) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

    static class NamedDestination {
        private final String name;
        private final PDPageDestination destination;

        public NamedDestination(String name, PDPageDestination destination) {
            this.name = name;
            this.destination = destination;
        }

        public String getName() {
            return name;
        }

        public PDPageDestination getDestination() {
            return destination;
        }

        public int getPageNumber() {
            return destination.retrievePageNumber() + 1; // Número de página (1-indexado)
        }
    }
}
