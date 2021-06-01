package GUI;

import GUI.utils.PausableTask;
import Wireworld.Components.Component;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
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
    private Button addBtn;
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
        addBtn.setVisible(false);
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
        if(world == null) return;
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
        TextField rows = new TextField("20");
        TextField cols = new TextField("20");
        Label r = new Label("rows:");
        Label c = new Label("cols:");
        VBox labels = new VBox(r,c);
        labels.setSpacing(16);
        VBox textfields = new VBox(rows,cols);
        textfields.setSpacing(5);
        dialogPane.setContent(new HBox(labels, textfields));
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
            addBtn.setVisible(true);
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
        addBtn.setVisible(false);
    }

    @FXML
    public void  reset() {
        if(task != null) task.shutDown();
        if(executor != null) executor.shutdownNow();
        executor = null;
        world.reset();
        InitWorldGrid();
    }

    @FXML
    public void  reset() {
        if(task != null) task.shutDown();
        if(executor != null) executor.shutdownNow();
        executor = null;
        world.reset();
        InitWorldGrid();
    }

    @FXML
    public void start() {
        if(world == null) return;
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
        Class<?>[] result = Component.class.getClasses();
        System.out.println(result.toString());
    }

    @FXML
    public void pause() {
        if (task != null) task.pause();
    }

    public static void displayError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorText);

        alert.showAndWait();

        System.exit(2);
    }

    @FXML
    public void addComponent() {
        List<String> choices = new ArrayList<>();
        choices.add("Diode");
        choices.add("OrGate");
        choices.add("AndNoGate");
        choices.add("FlipFlop");
        choices.add("Generator");
        choices.add("Wire");
        choices.add("Electron");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Diode", choices);
        dialog.setTitle("Add component to world");
        dialog.setHeaderText("Add component");
        dialog.setContentText("Choose component");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            specifyArguments(result.get());
        }
// The Java 8 way to get the response value (with lambda expression).
//        result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }

    @FXML
    public void specifyArguments(String component) {

        if(component.equals("Diode")||component.equals("OrGate")||component.equals("AndNoGate")||component.equals("FlipFlop")){
            Dialog<Map> dialog = new Dialog<>();
            dialog.setTitle("Specify arguments");
            dialog.setHeaderText("Please specify…");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            TextField direction = new TextField("Normal");
            TextField position = new TextField("Horizontal");
            TextField x = new TextField("3");
            TextField y = new TextField("3");
            VBox textfields = new VBox(position, direction, x, y);
            Label l_direction = new Label("direction:");
            Label l_position = new Label("position:");
            Label l_x = new Label("starting point x:");
            Label l_y = new Label("starting point y:");
            VBox labels = new VBox(l_position,l_direction,l_x,l_y);
            labels.setSpacing(16);
            textfields.setSpacing(5);
            dialogPane.setContent(new HBox(labels, textfields));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    Map<String, String> result = new HashMap<>();
                    result.put("direction", direction.getText());
                    result.put("position", position.getText());
                    result.put("x", x.getText());
                    result.put("y", y.getText());
                    return result;
                }
                return null;
            });
            Optional<Map> optional = dialog.showAndWait();
            optional.ifPresent((Map result) -> {
                ArrayList<String> arguments = new ArrayList<String>();
                arguments.add((String)result.get("position"));
                arguments.add((String)result.get("direction"));
                arguments.add((String)result.get("x"));
                arguments.add((String)result.get("y"));
                FileManager fm = new FileManager();
                world = fm.AddComponentToWorld(component,arguments,world);
                InitWorldGrid();
            });
        }
        else if(component.equals("Wire")){
            Dialog<Map> dialog = new Dialog<>();
            dialog.setTitle("Specify arguments");
            dialog.setHeaderText("Please specify…");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            TextField x1 = new TextField("1");
            TextField y1 = new TextField("1");
            TextField x2 = new TextField("3");
            TextField y2 = new TextField("3");
            VBox textfields = new VBox(x1, y1, x2, y2);
            Label l_x1 = new Label("starting point x:");
            Label l_y1 = new Label("starting point y:");
            Label l_x2 = new Label("ending point x:");
            Label l_y2 = new Label("ending point y:");
            VBox labels = new VBox(l_x1,l_y1,l_x2,l_y2);
            labels.setSpacing(16);
            textfields.setSpacing(5);
            dialogPane.setContent(new HBox(labels, textfields));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    Map<String, String> result = new HashMap<>();
                    result.put("x1", x1.getText());
                    result.put("y1", y1.getText());
                    result.put("x2", x2.getText());
                    result.put("y2", y2.getText());
                    return result;
                }
                return null;
            });
            Optional<Map> optional = dialog.showAndWait();
            optional.ifPresent((Map result) -> {
                ArrayList<String> arguments = new ArrayList<String>();
                arguments.add((String)result.get("x1"));
                arguments.add((String)result.get("y1"));
                arguments.add((String)result.get("x2"));
                arguments.add((String)result.get("y2"));
                FileManager fm = new FileManager();
                world = fm.AddComponentToWorld(component,arguments,world);
                InitWorldGrid();
            });
        }
        else if(component.equals("Electron")){
            Dialog<Map> dialog = new Dialog<>();
            dialog.setTitle("Specify arguments");
            dialog.setHeaderText("Please specify…");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            TextField type = new TextField("Head");
            TextField x = new TextField("3");
            TextField y = new TextField("3");
            VBox textfields = new VBox(type,x,y);
            Label l_type = new Label("type:");
            Label l_x = new Label("starting point x:");
            Label l_y = new Label("starting point y:");
            VBox labels = new VBox(l_type,l_x,l_y);
            labels.setSpacing(16);
            textfields.setSpacing(5);
            dialogPane.setContent(new HBox(labels, textfields));
                dialog.setResultConverter((ButtonType button) -> {
                    if (button == ButtonType.OK) {
                        Map<String, String> result = new HashMap<>();
                        result.put("type", type.getText());
                        result.put("x", x.getText());
                        result.put("y", y.getText());
                        return result;
                    }
                    return null;
                });
                Optional<Map> optional = dialog.showAndWait();
                optional.ifPresent((Map result) -> {
                    ArrayList<String> arguments = new ArrayList<String>();
                    arguments.add((String)result.get("type"));
                    arguments.add((String)result.get("x"));
                    arguments.add((String)result.get("y"));
                    FileManager fm = new FileManager();
                    world = fm.AddComponentToWorld(component,arguments,world);
                    InitWorldGrid();
                });
        }
        else if(component.equals("Generator")){
            Dialog<Map> dialog = new Dialog<>();
            dialog.setTitle("Specify arguments");
            dialog.setHeaderText("Please specify…");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            TextField position = new TextField("Horizontal");
            TextField x = new TextField("3");
            TextField y = new TextField("3");
            VBox textfields = new VBox(position,x,y);
            Label l_position = new Label("position:");
            Label l_x = new Label("starting point x:");
            Label l_y = new Label("starting point y:");
            VBox labels = new VBox(l_position,l_x,l_y);
            labels.setSpacing(16);
            textfields.setSpacing(5);
            dialogPane.setContent(new HBox(labels, textfields));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    Map<String, String> result = new HashMap<>();
                    result.put("position", position.getText());
                    result.put("x", x.getText());
                    result.put("y", y.getText());
                    return result;
                }
                return null;
            });
            Optional<Map> optional = dialog.showAndWait();
            optional.ifPresent((Map result) -> {
                ArrayList<String> arguments = new ArrayList<String>();
                arguments.add((String)result.get("position"));
                arguments.add((String)result.get("x"));
                arguments.add((String)result.get("y"));
                FileManager fm = new FileManager();
                world = fm.AddComponentToWorld(component,arguments,world);
                InitWorldGrid();
            });
        }


    }
}

