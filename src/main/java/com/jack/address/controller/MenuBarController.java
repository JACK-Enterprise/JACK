package com.jack.address.controller;

/**
 * Created by Maxime on 22/04/2016.
 */

import com.sun.glass.ui.MenuBar;
import javafx.fxml.FXML;
import com.jack.address.MainApp;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Map;

/**
 * The controller class for the MenuBar
 */
public class MenuBarController {

    // Reference to the main application
    private MainApp mainApp;
    private static MenuBarController instance;

    @FXML
    private MenuBar menuBar;
    
    @FXML
    public TextArea coordinates;
    
    @FXML
    public TextArea consoleText;

    @FXML
    public VBox legendsList;
    
    public static double longitude = 0;
    public static double latitude = 0;
    
    public static double minLongitude = 0;
    public static double minLatitude = 0;
    
    public static double maxLongitude = 0;
    public static double maxLatitude = 0;

    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MenuBarController(){
        instance = this;
    }

    /**
     * Initialize the controller class.
     * This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        /* NOW IS EMPTY */
        instance = this;
        initializeCoordinates();
    }

    public static MenuBarController getInstance() {
        return instance;
    }
    
    public void initializeCoordinates() {
        coordinates.setText("min lat : " + minLatitude + "\nmin long : " + minLongitude + "\n");
    }
    
    public void initializeConsole(StringBuilder console) {
        consoleText.setText(console.toString());
    }
    
    public void refreshConsole(final StringBuilder console) {
        Platform.runLater(() -> {
            initializeConsole(console);
        });
    }
    
    public void refreshCoordinates() {
        Platform.runLater(this::initializeCoordinates);
    }

    public void initializeLegends(Map<String, String> legends){
        for (Map.Entry<String, String> entry : legends.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            HBox box = new HBox();
            ObservableList<Node> list = box.getChildren();
            javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(0, 1, Color.RED);
            Text t = new Text(value);
            list.add(r);
            list.add(t);
            legendsList.getChildren().add(box);
        }
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        initializeConsole( mainApp.getIni().getConsole() );
        initializeLegends( mainApp.getIni().getLegends() );
    }


    @FXML
    private void handleSettings(){
        boolean okClicked = mainApp.showSettingsWindow();
    }

    @FXML
    private void handleAbout(){
        boolean clicked = mainApp.showAboutWindow();
    }

    @FXML
    private void handleExit(){
        mainApp.cancelRequest();
    }

    @FXML
    private void handleGetCapabilities(){
        mainApp.showCapabilitiesWindow();
    }

    @FXML
    private void handleImageViewer(){mainApp.showImageViewerWindow();}

    @FXML
    private void handleServerImagery(){mainApp.showSetServerImagery();}
}
