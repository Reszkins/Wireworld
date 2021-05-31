package Wireworld.Components;

public class Diode extends Component {
    public Diode(String position, String direction){
        element = new boolean[][]{
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {true, true, true},
                {true, false, true},
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {false, true, false}};
        startX=0;
        startY=1;
        adjustElement(position, direction);
    }
}
