package Wireworld.Components;

public class AndNoGate extends Component {
    public AndNoGate(String position, String direction){
        element = new boolean[][]{
                {true, false, false, false, false, false, true, },
                {true, false, false, false, false, false, true, },
                {true, false, false, false, false, false, true, },
                {true, false, false, false, false, true, false, },
                {true, false, true, false, true, false, false, },
                {false, true, true, true, false, false, false, },
                {false, false, true, false, true, false, false, },
                {false, false, false, false, true, false, false, },
                {false, false, false, false, true, false, false, },
                {false, false, false, false, true, false, false, },
                {false, false, false, false, true, false, false, }
        };
        startX=0;
        startY=4;
        adjustElement(position, direction);
    }
}
