package battleship.map.mapelement;

import battleship.map.Vector2D;
import battleship.util.CoordinateParser;

public class Ship {

    private final String type;
    private final Vector2D bowCoordinates;
    private final Vector2D sternCoordinates;
    private final int length;
    private int cellsHit;

    public Ship(String type, String bowCoordinates, String sternCoordinates) {
        this.type = type;
        this.bowCoordinates = CoordinateParser.parseCoordinate(bowCoordinates);
        this.sternCoordinates = CoordinateParser.parseCoordinate(sternCoordinates);
        this.length = (int) Math.round(this.bowCoordinates.distance(this.sternCoordinates)) + 1;
        this.cellsHit = 0;
    }

    public Vector2D getBowCoordinates() {
        return bowCoordinates;
    }

    public Vector2D getSternCoordinates() {
        return sternCoordinates;
    }

    public String getType() {
        return type;
    }

    public void incrementHits() {
        this.cellsHit += 1;
    }

    public boolean isAfloat(){
        return this.length > this.cellsHit;
    }

    public boolean isAt(Vector2D vector) {
        Vector2D topLeft = bowCoordinates.topLeft(sternCoordinates);
        Vector2D bottomRight = bowCoordinates.bottomRight(sternCoordinates);
        return vector.isInBounds(topLeft, bottomRight);
    }

    public int getCellsHit() {
        return cellsHit;
    }
}
