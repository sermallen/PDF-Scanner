import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class fonts {
    public static HashMap<String, String[]> getFuentes (PDDocument doc) {
        HashMap<String, String[]> fontsMap = new HashMap<>();        

        for (PDPage page : doc.getPages()) {
            for (COSName font : page.getResources().getFontNames()) {
                PDFont f;
                try {
                    f = page.getResources().getFont(font);
                    String[] esp = { f.toString(), String.valueOf(f.getAverageFontWidth()) };
                    fontsMap.put(font.getName(), esp);
                } catch (IOException e) {
                    System.err.println("Error al obtener la fuente: " + font.toString());
                }
            }
        }
        
        return fontsMap;

        // for (String name : fontsMap.keySet()) {
        //     System.out.printf("Nombre: %s \n  Descripción: %s\n  Tamaño: %s \n\n", name, fontsMap.get(name)[0],
        //             fontsMap.get(name)[1]);
        // }
    }
}
