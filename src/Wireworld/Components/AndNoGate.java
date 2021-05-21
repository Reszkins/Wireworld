package Wireworld.Components;

import GUI.Controller;
import Wireworld.World;

import java.util.Arrays;

public class AndNoGate extends Component {
    static boolean[][] gateHorizontal = new boolean[11][7];
    static boolean[][] gateVertical = new boolean[7][11];

    private static void FillHorizontal() {
        for( int i = 0 ; i < 11; i++) {
            Arrays.fill(gateHorizontal[i], false);
        }
        gateHorizontal[0][0] = true;
        gateHorizontal[1][0] = true;
        gateHorizontal[2][0] = true;
        gateHorizontal[3][0] = true;
        gateHorizontal[4][0] = true;
        gateHorizontal[0][6] = true;
        gateHorizontal[1][6] = true;
        gateHorizontal[2][6] = true;
        gateHorizontal[6][4] = true;
        gateHorizontal[7][4] = true;
        gateHorizontal[8][4] = true;
        gateHorizontal[9][4] = true;
        gateHorizontal[10][4] = true;
        gateHorizontal[4][4] = true;
        gateHorizontal[3][5] = true;
        gateHorizontal[4][2] = true;
        gateHorizontal[5][2] = true;
        gateHorizontal[5][1] = true;
        gateHorizontal[5][3] = true;
        gateHorizontal[6][2] = true;
    }

    public static World AndNoGate(String position, String direction, int x, int y, World world){
        FillHorizontal();

        switch (position) {
            case "Horizontal" -> {
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, gateHorizontal, x, y);
                    case "Reversed" -> {
                        y-=4;
                        gateHorizontal = Reverse(position, gateHorizontal);
                        world = World.Merge(world, gateHorizontal, x, y);
                    }
                }
            }
            case "Vertical" -> {
                gateVertical = FillVertical(gateVertical, gateHorizontal);
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, gateVertical, x, y);
                    case "Reversed" -> {
                        x-=4;
                        gateVertical = Reverse(position, gateVertical);
                        world = World.Merge(world, gateVertical, x, y);
                    }
                }
            }
            default -> Controller.displayError("Błąd w parametrach komponentu (AndNoGate)!");
        }
        return world;
    }
}
