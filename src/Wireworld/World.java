package Wireworld;

import GUI.Controller;
import Wireworld.Components.*;

public class World extends Cells {
    public int rows = 100;
    public int cols = 100;
    public static Case[][] wireworld = new Case[100][100];

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        wireworld = new Case[rows][cols];
        Fill();
    }

    public World() {

    }

    public void Fill() {
        for( int i = 0 ; i < rows ; i++){
            for( int j = 0 ; j < cols ; j++){
                wireworld[i][j] = Case.EMPTY;
            }
        }
    }

    public static World Merge(World world, boolean[][] element, int x, int y){
        for(int i = x ; i < x + element.length ; i++) {
            for(int j = y ; j < y + element[1].length ; j++) {
                if(i >= world.rows || j>= world.cols){
                    Controller.displayError("Błędne dane wejściowe - komponenty wychodzą poza planszę!");
                }
                if(element[i-x][j-y]){
                    if(world.wireworld[i][j] == Case.WIRE)
                        Controller.displayError("Błędne dane wejściowe - komponenty nachodzą na siebie!");
                    else world.wireworld[i][j] = Case.WIRE;
                }
            }
        }
        return world;
    }

    public void NextIteration(){
        Case[][] tmp = new Case[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tmp[i][j] = wireworld[i][j];
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(wireworld[i][j] == Case.EMPTY);
                else{
                    if(wireworld[i][j] == Case.ELECTRON_HEAD)
                        wireworld[i][j] = Case.ELECTRON_TAIL;
                    else if(wireworld[i][j] == Case.ELECTRON_TAIL)
                        wireworld[i][j] = Case.WIRE;
                    else if(wireworld[i][j] == Case.WIRE){
                        int n = CountElectronNeighbors(i-1,j-1,tmp);
                        if(n == 1 || n == 2)
                            wireworld[i][j] = Case.ELECTRON_HEAD;
                    }
                }
            }
        }
    }

    private int CountElectronNeighbors(int x, int y, Case[][] tmp) {
        if(x < 0) x = 0;
        if(y < 0) y = 0;

        int result = 0;

        for(int i = x; i < x+3 && i < rows; i++){
            for(int j = y; j < y+3 && j < cols; j++){
                if(tmp[i][j] == Case.ELECTRON_HEAD)
                    result++;
            }
        }

        return result;
    }

}
