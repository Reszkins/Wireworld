package GUI.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    String appConfig = System.getProperty("user.dir") + "\\app.properties";

    Properties appProperties;
    public PropertiesManager() {
        appProperties = new Properties();
    }
    public String getProperty(String property) {
        try {
            appProperties.load(new FileInputStream(appConfig));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProperties.getProperty(property);
    }

    public void setProperty(String name, String value) {
        appProperties.setProperty(name, value);
        try {
            appProperties.store(new FileOutputStream(appConfig), "store properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
