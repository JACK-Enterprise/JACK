package com.jack.address.controller;

/**
 * Created by Maxime on 22/04/2016.
 */

import com.sun.glass.ui.MenuBar;
import javafx.fxml.FXML;
import com.jack.address.MainApp;
import com.jack.wms.WMSImageryProvider;
import javafx.scene.control.TextArea;

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
        coordinates.setText("min lat : " + minLatitude);
    }
    
    public void initializeConsole(StringBuilder console) {
        consoleText.setText(console.toString());
    }
    
    public void refreshConsole(StringBuilder console) {
        new Thread( () -> {
            initializeConsole(console);
        } ).start();
    }
    
    public void refreshCoordinates() {
        new Thread( () -> {
            initializeCoordinates();
        } ).start();
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        initializeConsole( mainApp.getIni().getConsole() );
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
