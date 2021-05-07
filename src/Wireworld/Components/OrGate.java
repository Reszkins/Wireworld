package Wireworld.Components;

import Wireworld.World;

import java.util.Arrays;

public class OrGate extends Component{
    static boolean[][] gateHorizontal = new boolean[9][5];
    static boolean[][] gateVertical = new boolean[5][9];

    private static void FillHorizontal(){
        for( int i = 0 ; i < 9; i++) {
            Arrays.fill(gateHorizontal[i], false);
        }
        gateHorizontal[0][0] = true;
        gateHorizontal[1][0] = true;
        gateHorizontal[2][0] = true;
        gateHorizontal[3][0] = true;
        gateHorizontal[0][4] = true;
        gateHorizontal[1][4] = true;
        gateHorizontal[2][4] = true;
        gateHorizontal[3][4] = true;
        gateHorizontal[3][2] = true;
        gateHorizontal[4][1] = true;
        gateHorizontal[4][2] = true;
        gateHorizontal[4][3] = true;
        gateHorizontal[5][2] = true;
        gateHorizontal[6][2] = true;
        gateHorizontal[7][2] = true;
        gateHorizontal[8][2] = true;
    }

    public static World OrGate(String position, String direction, int x, int y, World world){
        FillHorizontal();

        for(int i=0;i<5;i++){
            for(int j=0;j<9;++j){
                System.out.print(gateHorizontal[j][i]+"  ");
            }
            System.out.println();
        }

        switch (position) {
            case "Horizontal" -> {
                switch (direction) {
                    case "Normal" -> world = World.Merge(world, gateHorizontal, x, y);
                    case "Reversed" -> {
                        y-=2;
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
                        x-=2;
                        gateVertical = Reverse(position, gateVertical);
                        world = World.Merge(world, gateVertical, x, y);
                    }
                }
            }
        }

        return world;
    }
}
