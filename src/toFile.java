import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class toFile {
    public static void toFile(String content, String fileOutput) {
        File text = new File(fileOutput);
        try {
            text.createNewFile();
            FileWriter myWriter = new FileWriter(fileOutput);
    
            myWriter.append(content);
    
            myWriter.close();
        } catch (IOException e) {
            System.err.println("Error al generar el fichero de salida");
        }
    }

    public static void toFile(List<String> content, String fileOutput) {
        try {
            File text = new File(fileOutput);
            text.createNewFile();
            FileWriter myWriter = new FileWriter(fileOutput);

            for (String string : content) {
                myWriter.append(string);
            }

            myWriter.close();
        } catch (IOException e) {
            System.err.println("Error al generar el fichero de salida");
        }
    }
}
