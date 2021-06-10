package Wireworld.Components;

import Wireworld.Cells;
import Wireworld.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectronTest {
    World world = new World(3,3);

    Cells.Case[][] tmp = new Cells.Case[][]{
            {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.ELECTRON_TAIL},
            {Cells.Case.ELECTRON_HEAD, Cells.Case.EMPTY, Cells.Case.ELECTRON_HEAD},
            {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.ELECTRON_TAIL},
    };

    @Test
    void electronTest(){
        Electron.Electron("Head",0,1,world);
        Electron.Electron("Head",2,1,world);
        Electron.Electron("Tail",2,0,world);
        Electron.Electron("Tail",2,2,world);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                assertEquals(world.wireworld[j][i], tmp[i][j]);
            }
        }
    }


}