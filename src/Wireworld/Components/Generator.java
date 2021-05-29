package Wireworld.Components;

public class Generator extends Component {
    public Generator(String position, String direction, int x, int y){
        element = new boolean[][]{
                {false, true, false, },
                {false, true, false, },
                {false, true, false, },
                {true, false, true, },
                {true, false, true, },
                {true, false, true, },
                {true, false, true, },
                {false, true, false, },
                {false, true, false, },
                {false, true, false, },
        };
        startX=0;
        startY=1;
        adjustElement(position, direction, x, y);
    }
}
