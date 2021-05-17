package GUI;

import Wireworld.FileManager;
import Wireworld.World;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //@FXML
    private WorldCanvas gridPane;
    @FXML
    private BorderPane root;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button loadBtn;

    private World world;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void InitWorldGrid()
    {
        root.setCenter(scroll);
        gridPane = new WorldCanvas(100, 100, scroll.getWidth(), scroll.getHeight());
        CanvasEvents events = new CanvasEvents(gridPane);
        scroll.setContent(gridPane);
        scroll.addEventFilter(ScrollEvent.SCROLL, events.getOnScrollEventHandler());
        scroll.addEventFilter(MouseEvent.MOUSE_PRESSED, events.getOnMousePressedEventHandler());
        scroll.addEventFilter(MouseEvent.MOUSE_DRAGGED, events.getOnMouseDraggedEventHandler());
        gridPane.drawWorld(world.wireworld);
    }

    @FXML
    public void loadFile()
    {

        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Choose input file.");
        File input = filechooser.showOpenDialog(root.getScene().getWindow());
        world = FileManager.ReadFromFile(input.getAbsolutePath());
        InitWorldGrid();
    }
}
