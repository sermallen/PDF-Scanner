package experimentos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;


public class pdf_to_text {
    final static String OUTPUT_PATH = "output\\pdfText.txt";
    final static String INPUT_PATH = "resources\\pdfs\\03.pdf";

    public static void main(String[] args) {
        PDDocument document;
        try {
            File file = new File(INPUT_PATH);
            document = Loader.loadPDF(file);

            PDFTextStripperByArea stripper;

            stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            document.close();

            File text = new File(OUTPUT_PATH);
            text.createNewFile();

            FileWriter myWriter = new FileWriter(OUTPUT_PATH);
            myWriter.write(pdfFileInText);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
