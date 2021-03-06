package Wireworld;

import GUI.Controller;

import java.util.ArrayList;

public class World extends Cells {
    public int rows;
    public int cols;
    public int generation;
    public Case[][] wireworld;
    private Case[][] previous;
    private Case[][] initialWireworld;
    public ArrayList<String> list;

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        generation = 0;
        wireworld = new Case[rows][cols];
        initialWireworld = new Case[rows][cols];
        list = new ArrayList<>();
        Fill();
    }

    public World() {
        this(100, 100);
    }

    public void Fill() {
        for( int i = 0 ; i < rows ; i++){
            for( int j = 0 ; j < cols ; j++){
                wireworld[i][j] = Case.EMPTY;
                initialWireworld[i][j] = Case.EMPTY;
            }
        }
    }

    public void Merge(boolean[][] element, int x, int y){
        if(x<0 || y<0){
            Controller.displayError("Błędne dane wejściowe - komponenty wychodzą poza planszę!");
            return;
        }
        for(int i = x ; i < x + element.length ; i++) {
            for(int j = y ; j < y + element[1].length ; j++) {
                if(i >= rows || j>= cols){
                    Controller.displayError("Błędne dane wejściowe - komponenty wychodzą poza planszę!");
                }
                if(element[i-x][j-y]){
                    if(wireworld[i][j] == Case.WIRE)
                        Controller.displayError("Błędne dane wejściowe - komponenty nachodzą na siebie!");
                    else wireworld[i][j] = Case.WIRE;
                }
            }
        }
    }

    public void NextIteration(){
        if(generation == 0) {
            for(int i = 0; i < wireworld.length; i++)
                initialWireworld[i] = wireworld[i].clone();
        }
        previous = new Case[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                previous[i][j] = wireworld[i][j];
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(wireworld[i][j] != Case.EMPTY) {
                    if(wireworld[i][j] == Case.ELECTRON_HEAD)
                        wireworld[i][j] = Case.ELECTRON_TAIL;
                    else if(wireworld[i][j] == Case.ELECTRON_TAIL)
                        wireworld[i][j] = Case.WIRE;
                    else if(wireworld[i][j] == Case.WIRE){
                        int n = CountElectronNeighbors(i-1,j-1,previous);
                        if(n == 1 || n == 2)
                            wireworld[i][j] = Case.ELECTRON_HEAD;
                    }
                }
            }
        }
        generation++;
    }

    public void reset() {
        for(int i = 0; i < initialWireworld.length; i++)
            wireworld[i] = initialWireworld[i].clone();
        generation=0;
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

    public boolean hasChanged(int x, int y) {
        return  wireworld[x][y] != previous[x][y];
    }
}
