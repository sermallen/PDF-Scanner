class Content {
    String format;
    float fontSize;
    String values;

    //TODO: guardar parametros Tm y/o cm
    // a b c d e f
    // a,d: tamaño      e,f: posicion    b,c: rotacion(deberia ser 0)

    // TODO: guardar los textos de una clase Contenido que tengan el mismo tamaño en el mismo value

    public Content(String format, int fontSize, String values) {
        this.format = format;
        this.fontSize = fontSize;
        this.values = values;
    }

    public Content(String format, String fontSize, String values) {
        this.format = format;
        this.fontSize = Float.parseFloat(fontSize);
        this.values = values;
    }

    public String getFormat() {
        return format;
    }

    public float getParameters() {
        return fontSize;
    }

    public String getValues() {
        return values;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setParameters(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String toString() {
        return format + ": {\n\t" + fontSize + "\n\t" + values + "\n}\n";
    }
}
