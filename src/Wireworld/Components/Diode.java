package Wireworld.Components;

import GUI.Controller;
import Wireworld.World;

import java.util.Arrays;

public class Diode extends Component {
    static boolean[][] diodeHorizontal = new boolean[8][3];
    static boolean[][] diodeVertical = new boolean [3][8];

    private static void FillHorizontal(){
        for( int i = 0 ; i < 8; i++) {
            Arrays.fill(diodeHorizontal[i], false);
        }
        diodeHorizontal[0][1]=true;
        diodeHorizontal[1][1]=true;
        diodeHorizontal[2][1]=true;
        diodeHorizontal[3][1]=true;
        diodeHorizontal[5][1]=true;
        diodeHorizontal[6][1]=true;
        diodeHorizontal[7][1]=true;
        diodeHorizontal[3][0]=true;
        diodeHorizontal[4][0]=true;
        diodeHorizontal[3][2]=true;
        diodeHorizontal[4][2]=true;
    }

    public static World Diode(String position, String direction, int x, int y, World world){
        FillHorizontal();

        switch (position) {
            case "Horizontal" -> {
                y--;
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, diodeHorizontal, x, y);
                    case "Reversed" -> {
                        diodeHorizontal = Reverse(position, diodeHorizontal);
                        world = World.Merge(world, diodeHorizontal, x, y);
                    }
                }
            }
            case "Vertical" -> {
                x--;
                diodeVertical = FillVertical(diodeVertical, diodeHorizontal);
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, diodeVertical, x, y);
                    case "Reversed" -> {
                        diodeVertical = Reverse(position, diodeVertical);
                        world = World.Merge(world, diodeVertical, x, y);
                    }
                }
            }
            default -> Controller.displayError("Błąd w parametrach komponentu (Dioda)!");
        }

        return world;
    }
}
