package Wireworld.Components;

import Wireworld.Cells;
import Wireworld.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WireTest {
    World world = new World();

    @Test
    void wireTest() {
        Cells.Case[][] tmp = new Cells.Case[][]{
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY,},
                {Cells.Case.EMPTY, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,},
                {Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.EMPTY, Cells.Case.WIRE,}
        };

        Wire.Wire(1, 1, 9, 9, world);

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                assertEquals(world.wireworld[j][i], tmp[i][j]);
            }
        }
    }
}