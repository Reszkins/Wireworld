package Wireworld.Components;

import Wireworld.World;

import java.util.Arrays;

public class FlipFlop extends Component {
    static boolean[][] flipflopHorizontal = new boolean[11][9];
    static boolean[][] flipflopVertical = new boolean[9][11];

    private static void FillHorizontal() {
        for( int i = 0 ; i < 11; i++) {
            Arrays.fill(flipflopHorizontal[i], false);
        }
        flipflopHorizontal[0][0] = true;
        flipflopHorizontal[1][0] = true;
        flipflopHorizontal[2][0] = true;
        flipflopHorizontal[3][0] = true;
        flipflopHorizontal[4][0] = true;
        flipflopHorizontal[0][8] = true;
        flipflopHorizontal[1][8] = true;
        flipflopHorizontal[2][8] = true;
        flipflopHorizontal[3][8] = true;
        flipflopHorizontal[5][1] = true;
        flipflopHorizontal[5][2] = true;
        flipflopHorizontal[4][2] = true;
        flipflopHorizontal[6][2] = true;
        flipflopHorizontal[5][3] = true;
        flipflopHorizontal[4][4] = true;
        flipflopHorizontal[6][4] = true;
        flipflopHorizontal[7][4] = true;
        flipflopHorizontal[8][4] = true;
        flipflopHorizontal[9][4] = true;
        flipflopHorizontal[10][4] = true;
        flipflopHorizontal[4][5] = true;
        flipflopHorizontal[6][5] = true;
        flipflopHorizontal[3][6] = true;
        flipflopHorizontal[4][6] = true;
        flipflopHorizontal[5][6] = true;
        flipflopHorizontal[4][7] = true;
    }

    public static World FlipFlop(String position, String direction, int x, int y, World world){
        FillHorizontal();

        switch (position) {
            case "Horizontal" -> {
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, flipflopHorizontal, x, y);
                    case "Reversed" -> {
                        y-=4;
                        flipflopHorizontal = Reverse(position, flipflopHorizontal);
                        world = World.Merge(world, flipflopHorizontal, x, y);
                    }
                }
            }
            case "Vertical" -> {
                flipflopVertical = FillVertical(flipflopVertical, flipflopHorizontal);
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, flipflopVertical, x, y);
                    case "Reversed" -> {
                        x-=4;
                        flipflopVertical = Reverse(position, flipflopVertical);
                        world = World.Merge(world, flipflopVertical, x, y);
                    }
                }
            }
        }
        return world;
    }
}
