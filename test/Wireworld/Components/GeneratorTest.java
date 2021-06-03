package Wireworld.Components;

import Wireworld.Cells;
import Wireworld.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    Component c = new Component();
    World world = new World(10,10);

    @Test
    void generatorTest(){
        c = new Generator("Vertical","Normal");
        world.Merge(c.element,5-c.startX,0-c.startY);

        Cells.Case[][] tmp = new Cells.Case [][]{
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,},
                {Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.WIRE,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,Cells.Case.EMPTY,}
        };

        for(int i=0;i<10;++i){
            for(int j=0;j<10;++j){
                assertEquals(world.wireworld[j][i],tmp[i][j]);
            }
        }
    }


}