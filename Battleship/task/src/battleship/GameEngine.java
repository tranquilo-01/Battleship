package battleship;

import battleship.map.Map;
import battleship.map.mapelement.Ship;
import battleship.util.CoordinateParser;
import battleship.util.ShipType;

import java.util.Scanner;

public class GameEngine {

    private final Map[] maps;
    private final Scanner scanner;
    private int turn;

    public GameEngine(Map map1, Map map2) {
        maps = new Map[2];
        maps[0] = map1;
        maps[1] = map2;
        turn = 0;
        this.scanner = new Scanner(System.in);
    }


    public void run() {
        System.out.println("Player 1, place your ships to the game field");
        placeAllShips(maps[0]);

        System.out.println("Player 2, place your ships to the game field");
        placeAllShips(maps[1]);

        battle();

        endGame();
    }


    private void placeAllShips(Map map) {
        map.display(true);
        System.out.println();
        int i = 0;
        while (i < ShipType.values().length) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", ShipType.values()[i].getType(), ShipType.values()[i].getLength());

            String bow = scanner.next();
            String stern = scanner.next();
            scanner.nextLine();
            if (CoordinateParser.calculateDistance(bow, stern) + 1 != ShipType.values()[i].getLength()) {
                System.out.println("Error! Wrong length of the ship. Try Again!");
            } else if (map.canPlaceShip(bow, stern)) {
                map.placeShip(new Ship(ShipType.values()[i].getType(), bow, stern));
                map.display(true);
                System.out.println();
                i++;
            }
        }
        System.out.println("""
                Press Enter and pass the move to another player
                ...""");
        scanner.nextLine();
    }

    private void battle() {
        String shotCoordinates;
        char shotResult;
        int[] sunkShips = new int[2];
        boolean gameEnded = false;
        while (true) {
            System.out.println();
            displayMaps();
            System.out.println();
            System.out.println("Player " + ((turn % 2) + 1) + " it's your turn:");
            shotCoordinates = scanner.next();
            scanner.nextLine();
            System.out.println();
            shotResult = maps[(turn + 1) % 2].shoot(shotCoordinates);
            switch (shotResult) {
                case 'B' -> System.out.println("Error! Coordinates out of map bounds! Try again:");
                case '~' -> System.out.println("You missed!");
                case 'O', 'X' -> System.out.println("You hit a ship!");
                case 'S' -> {
                    sunkShips[(turn + 1) % 2]++;
                    if (sunkShips[(turn + 1) % 2] == ShipType.values().length) {
                        gameEnded = true;
                        break;
                    }
                    System.out.println("You sank a ship! Specify a new target");
                }
            }
            if(gameEnded){
                break;
            }
            turn++;
            System.out.println("Press Enter and pass the move to another player\n" + "...");
            scanner.nextLine();
        }
    }

    private void endGame() {
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void displayMaps() {
        maps[(turn + 1) % 2].display(false);
        System.out.println("---------------------");
        maps[turn % 2].display(true);
    }
}
