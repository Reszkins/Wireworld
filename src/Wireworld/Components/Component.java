package Wireworld.Components;

public class Component {
    public static boolean[][] Reverse(String position, boolean[][] element){
        switch(position) {
            case "Horizontal":
                for(int i=0;i<element.length/2;i++){
                    for(int j=0;j<element[0].length;j++){
                        boolean tmp = element[i][j];
                        element[i][j] = element[element.length-i-1][j];
                        element[element.length-i-1][j] = tmp;
                    }
                }
                break;
            case "Vertical":
                for(int i=0;i<element[0].length/2;i++){
                    for(int j=0;j<element.length;j++){
                        boolean tmp = element[j][i];
                        element[j][i] = element[j][element[0].length-i-1];
                        element[j][element[0].length-i-1] = tmp;
                    }
                }
                break;
        }
        return element;
    }

    public static boolean[][] FillVertical(boolean[][] elementVertical,boolean[][] elementHorizontal){
        for(int i=0;i<8;++i){
            for(int j=0;j<3;++j){
                elementVertical[j][i] = elementHorizontal[i][j];
            }
        }
        return elementVertical;
    }
}
