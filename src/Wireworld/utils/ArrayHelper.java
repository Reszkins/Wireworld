package Wireworld.utils;

public class ArrayHelper {
    public static boolean[][] rotate(boolean[][] element){
        int rows = element.length;
        int cols = element[0].length;
        boolean[][] tmp = new boolean[cols][rows];

        for(int i=0;i<rows;++i){
            for(int j=0;j< cols;++j){
                tmp[j][i] = element[i][j];
            }
        }
        element = tmp;
        return element;
    }

    public static boolean[][] mirrorHorizontal(boolean[][] element){
        for(int i=0;i<element.length/2;i++){
            for(int j=0;j<element[0].length;j++){
                boolean tmp = element[i][j];
                element[i][j] = element[element.length-i-1][j];
                element[element.length-i-1][j] = tmp;
            }
        }
        return element;
    }

    public static boolean[][] mirrorVertical(boolean[][] element){
        for(int i=0;i<element[0].length/2;i++){
            for(int j=0;j<element.length;j++){
                boolean tmp = element[j][i];
                element[j][i] = element[j][element[0].length-i-1];
                element[j][element[0].length-i-1] = tmp;
            }
        }
        return element;
    }
}
