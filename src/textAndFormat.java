import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDPage;

public class textAndFormat {
    private static final Pattern patronBTET = Pattern.compile("BT(.*?)ET", Pattern.DOTALL);
    // private static final Pattern patronFormato = Pattern.compile("(\\/F\\d+)?(.*?)(\\[.*?\\]?TJ)+?", Pattern.DOTALL);
    private static final Pattern patronFormato = Pattern.compile("(\\/F\\d+)(.*?)(?=\\/F|$)", Pattern.DOTALL);

    public static List<String> getContenido (PDPage page){

        byte[] tx;
        try {
            tx = page.getContents().readAllBytes();
        } catch (IOException e) {
            System.err.println("Error al obtener el contenido de la pagina");
            return null;
        }

        String str = new String(tx, StandardCharsets.UTF_8);
        // str = str.replace('\n', ' ');
        str = Arrays.toString(getTagValues(str, patronBTET).toArray());
        
        return getContent(str, patronFormato);
    }

    private static List<String> getTagValues(String str, Pattern patron) {
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = patron.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    private static List<String> getContent(String str, Pattern patron) {
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = patron.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1) + " : { " + matcher.group(2) + " }\n");
        }
        return tagValues;
    }
}

class Content {
    String format;
    String parameters;
    String values;

    public Content() {
    }

    public Content(String format, String parameters, String value) {
    }
    
    public String getFormat() {
        return format;
    }

    public String getParameters() {
        return parameters;
    }

    public String getValues() {
        return values;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String toString() {
        return format + ": {" + parameters + " " + values + "}";
    }
}
