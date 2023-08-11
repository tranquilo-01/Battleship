package battleship.map;

import battleship.map.mapelement.Ship;
import battleship.util.Config;
import battleship.util.CoordinateParser;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Map {

    private final int height;
    private final int width;
    private final Vector2D lowerBound;
    private final Vector2D upperBound;
    private final char[][] map;
    private final ArrayList<Ship> ships;

    public Map() {
        this.width = Config.MAP_WIDTH;
        this.height = Config.MAP_HEIGHT;
        this.map = new char[this.height][this.width];
        this.lowerBound = new Vector2D(0, 0);
        this.upperBound = new Vector2D(this.height - 1, this.width - 1);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                map[i][j] = '~';
            }
        }
        this.ships = new ArrayList<>();
    }

    public void display(boolean shipsVisible) {
        char a = 'A';
        System.out.print("  ");
        for (int i = 1; i <= this.width; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            System.out.print((char) (a + i) + " ");
            for (int j = 0; j < this.width; j++) {
                if (!shipsVisible && map[i][j] == 'O') {
                    System.out.print("~ ");
                } else {
                    System.out.print(map[i][j] + " ");

                }
            }
            System.out.println();
        }
    }


    public boolean canPlaceShip(String bowCoordinates, String sternCoordinates) {
        Vector2D bowCoord = CoordinateParser.parseCoordinate(bowCoordinates);
        Vector2D sternCoord = CoordinateParser.parseCoordinate(sternCoordinates);

        if (!isInBounds(bowCoord) || !isInBounds(sternCoord)) {
            System.out.println("Error! Ship out of map bounds! Try again:");
            return false;
        }

        if (bowCoord.x != sternCoord.x && bowCoord.y != sternCoord.y) {
            System.out.println("Error! Ship cannot be placed diagonally! Try again:");
            return false;
        }

        for (int i = bowCoord.x; i <= sternCoord.x; i++) {
            for (int j = bowCoord.y; j <= sternCoord.y; j++) {
                if (map[i][j] != '~' && map[i][j] != 'M') {
                    System.out.println("Error! You cannot place the ship on fields taken by other ship! Try again:");
                    return false;
                }
            }
        }
        Vector2D topLeft = new Vector2D(Math.max(0, bowCoord.topLeft(sternCoord).x - 1), Math.max(0, bowCoord.topLeft(sternCoord).y - 1));
        Vector2D bottomRight = new Vector2D(Math.min(this.height - 1, bowCoord.bottomRight(sternCoord).x + 1), Math.min(this.width - 1, bowCoord.bottomRight(sternCoord).y + 1));

        for (int i = topLeft.x; i <= bottomRight.x; i++) {
            for (int j = topLeft.y; j <= bottomRight.y; j++) {
                if (map[i][j] != '~' && map[i][j] != 'M') {
                    System.out.println("Error! The ships are too close");
                    return false;
                }
            }
        }

        return true;
    }

    public void placeShip(Ship ship) {
        Vector2D bowCoord = ship.getBowCoordinates();
        Vector2D sternCoord = ship.getSternCoordinates();

        Vector2D topLeft = bowCoord.topLeft(sternCoord);
        Vector2D bottomRight = bowCoord.bottomRight(sternCoord);

        for (int i = topLeft.x; i <= bottomRight.x; i++) {
            for (int j = topLeft.y; j <= bottomRight.y; j++) {
                map[i][j] = 'O';
            }
        }
        this.ships.add(ship);

    }

    public char shoot(String shotCoordinate) {
        Vector2D coordinate = CoordinateParser.parseCoordinate(shotCoordinate);

        if (!isInBounds(coordinate)) {
            return 'B';
        } else {
            switch (map[coordinate.x][coordinate.y]) {
                case '~' -> {
                    map[coordinate.x][coordinate.y] = 'M';
                    return '~';
                }
                case 'O' -> {
                    map[coordinate.x][coordinate.y] = 'X';
                    for(Ship ship : ships){
                        if(ship.isAt(coordinate)){
                            ship.incrementHits();
                            if(!ship.isAfloat()){
                                return 'S';
                            }
                        }
                    }
                    return 'O';
                }
                case 'X' -> {
                    return 'X';
                }
                default -> throw new IllegalStateException("Unexpected map behaviour");
            }
        }
    }

    private boolean isInBounds(Vector2D vector) {
        return vector.isInBounds(this.lowerBound, this.upperBound);
    }

}
