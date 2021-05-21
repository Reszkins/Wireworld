package GUI;

import GUI.utils.PropertiesManager;
import Wireworld.Cells;
import Wireworld.World;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class WorldCanvas extends Canvas {
    private int rows;
    private int cols;
    public double rectangleSize;
    private double scale = 1;
    private double borderSize = 0.25d;
    private double viewportWidth;
    private double viewPortHeight;
    private boolean isEditable = false;
    private boolean showGridLines = false;
    private World world;
    private GraphicsContext gc;
    private PropertiesManager properties;

    public WorldCanvas(World world) {
        super(0, 0);
        setWorld(world);
        gc = getGraphicsContext2D();
        properties = new PropertiesManager();
    }

    public WorldCanvas() {
        super(0, 0);
        gc = getGraphicsContext2D();
        properties = new PropertiesManager();
    }

    public void initCanvas() {
        gc.setImageSmoothing(false);
        //CanvasEvents events = new CanvasEvents(this);
        updateCanvas();
    }

    public void setWorld(World world) {
        this.world = world;
        this.rows = world.rows;
        this.cols = world.cols;
    }

    private void updateViewportSize() {
        ScrollPane parent = (ScrollPane) getScene().lookup("#scroll");
        viewPortHeight = parent.getViewportBounds().getHeight();
        viewportWidth = parent.getViewportBounds().getWidth();
    }

    public void updateProperties() {
        borderSize = Double.parseDouble(properties.getProperty("borderSize"));
        showGridLines = Boolean.parseBoolean(properties.getProperty("showGridLines"));
    }

    public void drawWorld()
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,getWidth(),getHeight());
        for( int i = 0 ; i < cols ; i++){
            for( int j = 0 ; j < rows ; j++){
                if(world.wireworld[j][i] == Cells.Case.WIRE) draw(i,j,Color.YELLOW);
                else if(world.wireworld[j][i] == Cells.Case.ELECTRON_HEAD) draw(i,j,Color.BLUE);
                else if(world.wireworld[j][i] == Cells.Case.ELECTRON_TAIL) draw(i,j,Color.RED);
            }
        }
        if(showGridLines) drawGridLines();
    }

    public void draw(int row, int col, Color color) {
        gc.setFill(color);
        double posX = getPos(col);
        double posY = getPos(row);
        gc.fillRect(posX, posY, rectangleSize, rectangleSize);
    }

    private void drawGridLines() {
        gc.setFill(Color.WHITE);
        for( int i = 1 ; i < rows ; i++){
            for( int j = 1 ; j < cols ; j++) {
                gc.fillRect(0, getPos(j)-borderSize*rectangleSize, getWidth(), borderSize*rectangleSize);
            }
            gc.fillRect(getPos(i)-borderSize*rectangleSize, 0, borderSize*rectangleSize, getHeight());
        }
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }

    public void setScale(double scale) {
        this.scale = scale;
        setScaleX(scale);
        setScaleY(scale);
        printScale();
    }

    public int getPositionByPixel(double pxl) {
        return (int)(pxl/(rectangleSize + rectangleSize*borderSize));
    }

    private double getPos(int v) {
        return v * (rectangleSize + rectangleSize*borderSize);
    }

    private void setRectangleSize()
    {
        double x = Math.min(viewPortHeight / rows, viewportWidth/ cols);
        double pow = Math.ceil(Math.log(x)/Math.log(2))-1;
        rectangleSize = Math.pow(2, pow);
        if( getDimensions(rows) > viewPortHeight || getDimensions(cols) > viewportWidth) {
            rectangleSize = Math.pow(2, pow-1);
        }
        rectangleSize*=2;
        setScale(0.5);
    }

    public void setCanvasSize() {
        System.out.println(getDimensions(rows));
        setWidth(getDimensions(rows));
        setHeight(getDimensions(cols));
    }

    private double getDimensions(int v) {
        return v*rectangleSize+ (v-1)*borderSize*rectangleSize;
    }

    public double getScale() {
        return scale;
    }

    public void setEditable(boolean b) { isEditable = b; }

    private void centerCanvas() {
        double dx = (viewportWidth - getWidth())/2;
        double dy = (viewPortHeight -getHeight())/2;

        setTranslateX(dx);
        setTranslateY(dy);
    }

    public void printScale() {
        Label zoomInfo = (Label) getScene().lookup("#zoomInfo");
        zoomInfo.setText("1:" + (int)(scale*rectangleSize) );
    }

    public  void updateCanvas() {
        updateProperties();
        updateViewportSize();

        setRectangleSize();
        setCanvasSize();

        centerCanvas();
        drawWorld();
    }
}
