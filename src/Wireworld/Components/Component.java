package Wireworld.Components;

import GUI.Controller;
import Wireworld.utils.ArrayHelper;

public class Component {
    public static boolean[][] element;
    public int startX;
    public int startY;

    public Component() {
    }

    public void adjustElement(String position, String direction) {
        switch (position) {
            case "Horizontal" -> {
                switch (direction) {
                    case "Reversed" -> {
                        element = ArrayHelper.mirrorHorizontal(element);
                    }
                }
            }
            case "Vertical" -> {
                element = ArrayHelper.rotate(element);
                int tmp;
                tmp = startX;
                startX = startY;
                startY = tmp;
                switch (direction) {
                    case "Reversed" -> {
                        element = ArrayHelper.mirrorVertical(element);
                    }
                }
            }
            default -> Controller.displayError("Błąd w parametrach komponentu (" + this.getClass().getName() + ")!");
        }
    }

}
