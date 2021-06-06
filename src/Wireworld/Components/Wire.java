package Wireworld.Components;

import GUI.Controller;
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

        if(x<0 || y<0){
            Controller.displayError("Błędne dane wejściowe - komponenty wychodzą poza planszę!");
            return;
        }

        for(int i = x; i <= x2; i++){
            //world.wireworld[i][y] = Cells.Case.WIRE;
            if(world.wireworld[i][y] == Cells.Case.WIRE)
                Controller.displayError("Błędne dane wejściowe - komponenty nachodzą na siebie!");
            else world.wireworld[i][y] = Cells.Case.WIRE;
        }
        if( y < y2 ){
            for(int i = y+1; i <= y2; i++){
                //world.wireworld[x2][i] = Cells.Case.WIRE;
                if(world.wireworld[x2][i] == Cells.Case.WIRE)
                    Controller.displayError("Błędne dane wejściowe - komponenty nachodzą na siebie!");
                else world.wireworld[x2][i] = Cells.Case.WIRE;
            }
        }
        else{
            for(int i = y-1; i >= y2; i--){
                //world.wireworld[x2][i] = Cells.Case.WIRE;
                if(world.wireworld[x2][i] == Cells.Case.WIRE)
                    Controller.displayError("Błędne dane wejściowe - komponenty nachodzą na siebie!");
                else world.wireworld[x2][i] = Cells.Case.WIRE;
            }
        }
    }
}
