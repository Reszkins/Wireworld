package Wireworld.Components;

public class OrGate extends Component {
    public OrGate(String position, String direction, int x, int y){
        element = new boolean[][]{
                {true, false, false, false, true, },
                {true, false, false, false, true, },
                {true, false, false, false, true, },
                {true, false, true, false, true, },
                {false, true, true, true, false, },
                {false, false, true, false, false, },
                {false, false, true, false, false, },
                {false, false, true, false, false, },
                {false, false, true, false, false, }
        };
        startX=0;
        startY=2;
        adjustElement(position, direction, x, y);
    }
}