package Wireworld.Components;

import Wireworld.World;

import java.util.Arrays;

public class Generator extends Component {
    static boolean[][] generatorHorizontal = new boolean[10][3];
    static boolean[][] generatorVertical = new boolean[3][10];

    private static void FillHorizontal() {
        for (int i = 0; i < 10; i++) {
            Arrays.fill(generatorHorizontal[i], false);
            generatorHorizontal[0][1] = true;
            generatorHorizontal[1][1] = true;
            generatorHorizontal[2][1] = true;
            generatorHorizontal[3][0] = true;
            generatorHorizontal[4][0] = true;
            generatorHorizontal[5][0] = true;
            generatorHorizontal[6][0] = true;
            generatorHorizontal[3][2] = true;
            generatorHorizontal[4][2] = true;
            generatorHorizontal[5][2] = true;
            generatorHorizontal[6][2] = true;
            generatorHorizontal[7][1] = true;
            generatorHorizontal[8][1] = true;
            generatorHorizontal[9][1] = true;
        }
    }

    public static World Generator(String position, int x, int y, World world){
        FillHorizontal();

        switch (position) {
            case "Horizontal" -> {
                y--;
                world = World.Merge(world, generatorHorizontal, x, y);
            }
            case "Vertical" -> {
                x--;
                generatorVertical = FillVertical(generatorVertical, generatorHorizontal);
                world = World.Merge(world, generatorVertical, x, y);
            }
        }

        return world;
    }
}
