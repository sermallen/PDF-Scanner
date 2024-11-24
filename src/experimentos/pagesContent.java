package experimentos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

public class pagesContent {
    final static String OUTPUT_PATH = "output\\pdfPages.txt";
    final static String INPUT_PATH = "resources\\pdfs\\03.pdf";

    public static void main(String[] args) throws IOException {
        // cargar documento
        File file = new File(INPUT_PATH);
        PDDocument doc;
        doc = Loader.loadPDF(file);

        int nPages = doc.getNumberOfPages();

        File text = new File(OUTPUT_PATH);
        text.createNewFile();
        FileWriter myWriter = new FileWriter(OUTPUT_PATH);

        for (int i = 0; i < nPages; i++) {
            byte[] tx = doc.getPage(i).getContents().readAllBytes();

            myWriter.append("\n\n--------------- Pagina " + i + " ---------------\n\n");
            for (byte t : tx)
                myWriter.append((char) t);
        }
        
        myWriter.close();
        doc.close();
    }
}
