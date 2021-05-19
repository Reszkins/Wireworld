package GUI;

import Wireworld.Cells;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class WorldCanvas extends Canvas {
    private int rows;
    private int cols;
    private double rectangleSize;
    private double scale = 1;
    private double viewportWidth;
    private double viewPortHeight;

    public WorldCanvas(int rows, int cols, double width, double height) {
        super(width, height);
        this.rows = rows;
        this.cols = cols;
        this.viewportWidth = width;
        this.viewPortHeight = height;
        getRectangleSize(width, height);

        setWidth(cols*rectangleSize);
        setHeight(rows*rectangleSize);

        centerCanvas();

        getGraphicsContext2D().setFill(Color.BLACK);
        getGraphicsContext2D().fillRect(0,0,getWidth(),getHeight());
        CanvasEvents events = new CanvasEvents(this);

        addEventFilter(ScrollEvent.SCROLL, events.getOnScrollEventHandler());
        addEventFilter(MouseEvent.MOUSE_PRESSED, events.getOnMousePressedEventHandler());
        addEventFilter(MouseEvent.MOUSE_DRAGGED, events.getOnMouseDraggedEventHandler());
        addEventFilter(MouseEvent.MOUSE_CLICKED, events.getOnClickEventHandler());
    }

    public void drawWorld(Cells.Case[][] world)
    {
        for( int i = 0 ; i < 100 ; i++){
            for( int j = 0 ; j < 100 ; j++){
                if(world[j][i] == Cells.Case.WIRE) draw(i,j,Color.YELLOW);
                else if(world[j][i] == Cells.Case.ELECTRON_HEAD) draw(i,j,Color.BLUE);
                else if(world[j][i] == Cells.Case.ELECTRON_TAIL) draw(i,j,Color.RED);
            }
        }
    }

    public void draw(int row, int col, Color color) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(color);
        double posX = getPos(col);
        double posY = getPos(row);
        gc.fillRect(posX, posY, rectangleSize, rectangleSize);
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getPositionByPixel(double pxl) {
        return (int)(pxl/rectangleSize);
    }

    private double getPos(int v) {
        return v * (rectangleSize);
    }

    private void getRectangleSize(double width, double height)
    {
        rectangleSize = Math.min(height / rows, width/ cols);
    }

    public double getScale() {
        return scale;
    }

    private void centerCanvas() {
        double dx = (viewportWidth - getWidth())/2;
        double dy = (viewPortHeight -getHeight())/2;

        setTranslateX(dx);
        setTranslateY(dy);
    }
}
