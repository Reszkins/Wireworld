package GUI;

import GUI.utils.PausableTask;
import Wireworld.FileManager;
import Wireworld.World;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Controller implements Initializable {
    //@FXML
    private WorldCanvas worldCanvas;
    @FXML
    private BorderPane root;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button clearBtn;
    @FXML
    private GridPane initView;
    @FXML
    private TextField iterationsField;

    private PausableTask task;
    private ThreadPoolExecutor executor;

    private World world;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        worldCanvas = new WorldCanvas();
        CanvasEvents events = new CanvasEvents(worldCanvas);
        worldCanvas.addEventFilter(ScrollEvent.SCROLL, events.getOnScrollEventHandler());
        worldCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, events.getOnMousePressedEventHandler());
        worldCanvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, events.getOnMouseDraggedEventHandler());
        worldCanvas.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            int row = worldCanvas.getPositionByPixel(e.getY());
            int col = worldCanvas.getPositionByPixel(e.getX());
            Label positionInfo = (Label) worldCanvas.getScene().lookup("#positionInfo");
            positionInfo.setText("Row: " + row + " Col: " + col);

            e.consume();
        });
        scroll.addEventFilter(ScrollEvent.SCROLL, events.getOnScrollEventHandler());
        scroll.addEventFilter(MouseEvent.MOUSE_PRESSED, events.getOnMousePressedEventHandler());
        scroll.addEventFilter(MouseEvent.MOUSE_DRAGGED, events.getOnMouseDraggedEventHandler());
    }

    @FXML
    public void InitWorldGrid()
    {
        worldCanvas.setWorld(world);
        scroll.setContent(worldCanvas);
        worldCanvas.initCanvas();

        Stage stage = (Stage) scroll.getScene().getWindow();
        stage.widthProperty().addListener((observableValue, number, t1) -> worldCanvas.updateCanvas());
        stage.heightProperty().addListener((observableValue, number, t1) -> worldCanvas.updateCanvas());

        clearBtn.setVisible(true);
    }

    @FXML
    public void loadFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose input file.");
        File input = fileChooser.showOpenDialog(root.getScene().getWindow());
        FileManager fm = new FileManager();
        world = fm.ReadFromFile(input.getAbsolutePath());
        InitWorldGrid();
    }

    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose output directory.");
        File selectedFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        FileManager.WriteToFile(selectedFile.getAbsolutePath(),world);
    }

    @FXML
    public void displaySettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/settingsView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 488, 424);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        scene.setOnMousePressed(pressEvent -> scene.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setOnCloseRequest(e -> {
            try {
                worldCanvas.updateCanvas();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        stage.show();
    }

    @FXML
    public void displayNewWorld() {
        Dialog<Map> dialog = new Dialog<>();
        dialog.setTitle("Dialog Test");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField rows = new TextField("Rows");
        TextField cols = new TextField("Cols");
        dialogPane.setContent(new VBox(rows, cols));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                Map<String, Integer> result = new HashMap<>();
                result.put("rows", Integer.parseInt(rows.getText()));
                result.put("cols", Integer.parseInt(cols.getText()));
                return result;
            }
            return null;
        });
        Optional<Map> optional = dialog.showAndWait();
        optional.ifPresent((Map result) -> {
            world = new World((int)result.get("rows"), (int)result.get("cols"));
            InitWorldGrid();
        });
    }

    @FXML
    public void resetStage() {
        world = null;
        if(task != null) task.shutDown();
        if(executor != null) executor.shutdownNow();
        executor = null;
        scroll.setContent(initView);
        clearBtn.setVisible(false);
    }

    @FXML
    public void start() {
        if(executor == null) {
            BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
            executor = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS, queue);
        }
        if(executor.getActiveCount() == 0) {
            int iterations = Integer.parseInt(iterationsField.getText());
            task = new PausableTask(worldCanvas, iterations);
            executor.execute(task);
        }
        else {
            task.resume();
        }
    }

    @FXML
    public void pause() {
        task.pause();
    }

    public static void displayError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorText);

        alert.showAndWait();

        System.exit(2);
    }
}

