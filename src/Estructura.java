class Estructura {
    private String name;
    private int page;
    private int coordX;
    private int coordY;

    public Estructura(String name, int page, int coordX, int coordY) {
        this.name = name;
        this.page = page;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public String getName() {
        return name;
    }

    public int getPage() {
        return page;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String toString() {
        return name + ": " + page + " (" + coordX + "," + coordY + ")\n";
    }
}