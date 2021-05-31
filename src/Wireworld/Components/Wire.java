package Wireworld.Components;

import Wireworld.Cells;
import Wireworld.World;

public class Wire {
    public static void Wire(int x, int y, int x2, int y2, World world){
        if( x > x2 ){
            int tmp = x;
            x = x2;
            x2 = tmp;
            tmp = y;
            y = y2;
            y2 = tmp;
        }
        for(int i = x; i <= x2; i++)
            world.wireworld[i][y] = Cells.Case.WIRE;
        if( y < y2 ){
            for(int i = y; i <= y2; i++)
                world.wireworld[x2][i] = Cells.Case.WIRE;
        }
        else{
            for(int i = y; i >= y2; i--)
                world.wireworld[x2][i] = Cells.Case.WIRE;
        }
    }
}
