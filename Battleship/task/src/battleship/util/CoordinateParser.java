package battleship.util;

import battleship.map.Vector2D;

public class CoordinateParser {
    public static Vector2D parseCoordinate(String stringCoord) {
        int firstCoord = stringCoord.charAt(0) - 65;
        int secondCoord = Integer.parseInt(stringCoord.replaceAll("[^0-9]", "")) - 1;
        return new Vector2D(firstCoord, secondCoord);
    }

    public static int calculateDistance(String coord1, String coord2){
        Vector2D v1 = parseCoordinate(coord1);
        Vector2D v2 = parseCoordinate(coord2);

        return (int) Math.round(v1.distance(v2));
    }
}
