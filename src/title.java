import org.apache.pdfbox.pdmodel.PDDocument;

public class title {

    public static String getTitle (PDDocument doc) {
        if (doc.getDocumentInformation().getTitle() != null && doc.getDocumentInformation().getTitle().length() != 0) {
            return doc.getDocumentInformation().getTitle();
        } 
        else if (doc.getDocumentInformation().getSubject() != null && doc.getDocumentInformation().getSubject().length() != 0) {
            return doc.getDocumentInformation().getSubject();
        } 
        // TODO: comprobar tags de accesibilidad, si el primero es unico podria ser el titulo
        else {
            return "Titulo no disponible";
        }
    } 
}
