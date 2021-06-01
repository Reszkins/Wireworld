package Wireworld.Components;

public class FlipFlop extends Component {
    public FlipFlop(String position, String direction){
        element = new boolean[][]{
                {true, false, false, false, false, false, false, false, true, },
                {true, false, false, false, false, false, false, false, true, },
                {true, false, false, false, false, false, false, false, true, },
                {true, false, false, false, false, false, true, false, true, },
                {true, false, true, false, true, true, true, true, false, },
                {false, true, true, true, false, false, true, false, false, },
                {false, false, true, false, true, true, false, false, false, },
                {false, false, false, false, true, false, false, false, false, },
                {false, false, false, false, true, false, false, false, false, },
                {false, false, false, false, true, false, false, false, false, },
                {false, false, false, false, true, false, false, false, false, }
        };
        startX=0;
        startY=4;
        adjustElement(position, direction);
    }
}
