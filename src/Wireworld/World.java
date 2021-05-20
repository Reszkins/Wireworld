package Wireworld;

public class World extends Cells {
    public int rows = 100;
    public int cols = 100;
    public static Case[][] wireworld = new Case[100][100];

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        Fill();
        wireworld = new Case[rows][cols];
    }

    public World() {

    }

    public void Fill() {
        for( int i = 0 ; i < 100 ; i++){
            for( int j = 0 ; j < 100 ; j++){
                wireworld[i][j] = Case.EMPTY;
            }
        }
    }

    public static World Merge(World world, boolean[][] element, int x, int y){
        for(int i = x ; i < x + element.length ; i++) {
            for(int j = y ; j < y + element[1].length ; j++) {
                world.wireworld[i][j] = (!element[i - x][j - y]) ? Case.EMPTY : Case.WIRE;
            }
        }
        return world;
    }

    public void NextIteration(){
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                if(wireworld[i][j] == Case.EMPTY);
                else{
                    if(wireworld[i][j] == Case.ELECTRON_HEAD)
                        wireworld[i][j] = Case.ELECTRON_TAIL;
                    else if(wireworld[i][j] == Case.ELECTRON_TAIL)
                        wireworld[i][j] = Case.WIRE;
                    else if(wireworld[i][j] == Case.WIRE){
                        int n = CountElectronNeighbors(i-1,j-1);
                        if(n == 1 || n == 2)
                            wireworld[i][j] = Case.ELECTRON_HEAD;
                    }
                }
            }
        }
    }

    private int CountElectronNeighbors(int x, int y) {
        if(x < 0) x = 0;
        if(y < 0) y = 0;

        int result = 0;

        for(int i = x; i < x+3 && i < 100; i++){
            for(int j = y; j < y+3 && j < 100; j++){
                if(wireworld[i][j] == Case.ELECTRON_HEAD)
                    result++;
            }
        }
        return result;
    }
}
