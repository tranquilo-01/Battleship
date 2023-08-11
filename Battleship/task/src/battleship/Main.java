package battleship;

import battleship.map.Map;

public class Main {

    public static void main(String[] args) {
        String s = "J10";
        System.out.println(Integer.parseInt(s.replaceAll("[^0-9]", "")) - 1);
        Map map1 = new Map();
        Map map2 = new Map();
        GameEngine engine = new GameEngine(map1, map2);
        engine.run();

    }
}

//    B2 B6 E3 H3 I5 I7 B9 D9 F7 F8

// player1 F3 F7 A1 D1 J10 J8 B9 D9 I2 J2


