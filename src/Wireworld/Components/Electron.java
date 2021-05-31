package Wireworld.Components;

import Wireworld.Cells;
import Wireworld.World;

public class Electron {
    public static void Electron(String type, int x, int y, World world){
        switch(type){
            case "Head":
                world.wireworld[x][y] = Cells.Case.ELECTRON_HEAD;
                break;
            case "Tail":
                world.wireworld[x][y] = Cells.Case.ELECTRON_TAIL;
                break;
        }
    }
}
