package Wireworld;

public class World extends Cells {
    public static Case[][] wireworld = new Case[15][15];
    Case x = Case.EMPTY;

    public void Fill() {
        for( int i = 0 ; i < 15 ; i++){
            for( int j = 0 ; j < 15 ; j++){
                wireworld[i][j] = x;
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
}
