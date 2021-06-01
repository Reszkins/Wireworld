package GUI.controllers;

import GUI.utils.PropertiesManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private GridPane root;
    @FXML
    private Spinner<Double> borderSize;
    @FXML
    private CheckBox showGridlines;
    @FXML
    private ColorPicker wireColor;
    @FXML
    private ColorPicker headColor;
    @FXML
    private ColorPicker tailColor;
    @FXML
    private TextField maxFps;

    private PropertiesManager properties;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        properties = new PropertiesManager();
        Double initialValue = Double.parseDouble(properties.getProperty("borderSize"));
        borderSize.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 0.5, initialValue,.05));
        showGridlines.setSelected(Boolean.parseBoolean(properties.getProperty("showGridLines")));
        wireColor.setValue(Color.web(properties.getProperty("wireColor")));
        headColor.setValue(Color.web(properties.getProperty("headColor")));
        tailColor.setValue(Color.web(properties.getProperty("tailColor")));
        maxFps.setText(properties.getProperty("maxFPS"));
    }

    @FXML
    public void close() {
        Stage stage = (Stage) borderSize.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    public void saveSettings() {
        properties.setProperty("borderSize", borderSize.getValue().toString());
        properties.setProperty("showGridLines", Boolean.toString(showGridlines.isSelected()));
        properties.setProperty("wireColor", wireColor.getValue().toString());
        properties.setProperty("headColor", headColor.getValue().toString());
        properties.setProperty("tailColor", tailColor.getValue().toString());
        properties.setProperty("maxFPS", maxFps.getText());
        this.close();
    }
}
