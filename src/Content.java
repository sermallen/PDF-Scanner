import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Content {
    String format;
    float fontSize;
    List<Values> content;

    public Content(String format, int fontSize, String values) {
        this.format = format;
        this.fontSize = fontSize;
        this.content = separateValues(values);
    }

    public Content(String format, String fontSize, String values) {
        this.format = format;
        this.fontSize = Float.parseFloat(fontSize);
        this.content = separateValues(values);
    }

    public String getFormat() {
        return format;
    }

    public float getParameters() {
        return fontSize;
    }

    public List<Values> getContent() {
        return content;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setParameters(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setValues(String values) {
        this.content = separateValues(values);
    }

    public String toString() {
        return format + ": {\n\t" + fontSize + "\n\t" + content.toString() + "\n}\n";
    }

    private List<Values> separateValues(String values) {
        Pattern patron = Pattern.compile("(?:(-?\\d*(?:\\.\\d*?)?) (-?\\d*(?:\\.\\d*?)?) (?:Td|Tm|cm)) \\[(.*?)\\]TJ",
                Pattern.DOTALL);
        final List<Values> tagValues = new ArrayList<Values>();
        final Matcher matcher = patron.matcher(values);
        while (matcher.find()) {
            tagValues.add(new Values(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return tagValues;
    }
}