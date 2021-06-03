package Wireworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    World world = new World(3,3);

    Cells.Case[][] tmp = new Cells.Case[][]{
            {Cells.Case.WIRE, Cells.Case.EMPTY, Cells.Case.ELECTRON_TAIL},
            {Cells.Case.ELECTRON_HEAD, Cells.Case.EMPTY, Cells.Case.ELECTRON_HEAD},
            {Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.ELECTRON_TAIL},
    };

    Cells.Case[][] a = new Cells.Case[][]{
            {Cells.Case.ELECTRON_HEAD, Cells.Case.EMPTY, Cells.Case.WIRE},
            {Cells.Case.ELECTRON_TAIL, Cells.Case.EMPTY, Cells.Case.ELECTRON_TAIL},
            {Cells.Case.ELECTRON_HEAD, Cells.Case.ELECTRON_HEAD, Cells.Case.WIRE},
    };

    Cells.Case[][] b = new Cells.Case[][]{
            {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY},
            {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY},
            {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY},
    };

    @BeforeEach
    void prepare(){
        world.wireworld = tmp;
    }

    @Test
    void nextIteration() {
        world.NextIteration();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                assertEquals(world.wireworld[i][j], a[i][j]);
            }
        }
    }

    @Test
    void reset() {
        world.reset();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                assertEquals(world.wireworld[j][i], b[i][j]);
            }
        }
    }
}