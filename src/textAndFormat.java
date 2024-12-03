import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDPage;

// Tf: tamaño de la fuente
// Td: posicion
// RG/rg: colores de la fuente
// Tm: matriz de posicion y orientacion
// TL: leading
// Tc: espacio de caracteres

public class textAndFormat {
    // TODO: comprobar que BT y ET no están entre parentesis o corchetes
    private static final Pattern patronBTET = Pattern.compile("BT(.*?)ET", Pattern.DOTALL);
    private static final Pattern patronFormato = Pattern.compile("(\\/F\\d+ )(\\d+\\.?\\d*) Tf (.*?)(?=\\/F|$)", Pattern.DOTALL);
    // private static final Pattern patronFormato = Pattern.compile("(\\/F\\d+)?(.*?)(\\[.*?\\]?TJ)+?", Pattern.DOTALL);

    public static List<Content> getContenido (PDPage page){
        String str = "";
        try {
            InputStream input = page.getContents();
            int tam = input.available();
            while (tam > 0) {
                str += new String(input.readNBytes(tam), StandardCharsets.UTF_8); // TODO: investigar charsets
                tam = input.available();
            }
        } catch (IOException e) {
            System.err.println("Error al obtener el contenido de la pagina");
            return null;
        }

        str = str.replace('\n', ' ');
        List<String> list = getTagValues(str, patronBTET);
        
        return getContent(list.toString(), patronFormato);
    }

    private static List<String> getTagValues(String str, Pattern patron) {
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = patron.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    private static List<Content> getContent(String str, Pattern patron) {
        final List<Content> tagValues = new ArrayList<Content>();
        final Matcher matcher = patron.matcher(str);
        while (matcher.find()) {
            tagValues.add(new Content (matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return tagValues;
    }
}