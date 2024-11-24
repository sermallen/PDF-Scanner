import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class tree {

    // TODO: profundizar en la busqueda
    // TODO: clase para guardar la estrcutura
    public static String getTree(PDDocument doc) {
        String tree = "";
        if (doc.getDocumentCatalog().getDocumentOutline().hasChildren()) {
            Iterator<PDOutlineItem> it = doc.getDocumentCatalog().getDocumentOutline().children().iterator();

            while (it.hasNext()) {
                PDOutlineItem i = it.next();
                tree += i.getTitle() + "\n";

                if (i.hasChildren()) {
                    Iterator<PDOutlineItem> it2 = i.children().iterator();
                    while (it2.hasNext()) {
                        tree += "\t" + it2.next().getTitle() + "\n";
                    }
                }
            }
        }
        return tree;
    }
}
