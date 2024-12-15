
public class Values {
    float coordX, coordY;
    String text;

    public Values(String param1, String param2, String text) {
        this.coordX = Float.parseFloat(param1);
        this.coordY = Float.parseFloat(param2);
        this.text = text;
    }

    public float getCoordX() {
        return coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return coordX + " " + coordY + " " + text;
    }
}
