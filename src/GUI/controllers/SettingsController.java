package GUI.controllers;

import GUI.utils.PropertiesManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
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

    private PropertiesManager properties;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        properties = new PropertiesManager();
        Double initialValue = Double.parseDouble(properties.getProperty("borderSize"));
        borderSize.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 0.5, initialValue,.05));
        showGridlines.setSelected(Boolean.parseBoolean(properties.getProperty("showGridLines")));
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
        this.close();
    }
}
