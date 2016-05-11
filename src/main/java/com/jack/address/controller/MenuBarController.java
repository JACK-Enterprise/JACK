package com.jack.address.controller;

/**
 * Created by Maxime on 22/04/2016.
 */

import com.sun.glass.ui.MenuBar;
import javafx.fxml.FXML;
import com.jack.address.MainApp;

/**
 * The controller class for the MenuBar
 */
public class MenuBarController {

    // Reference to the main application
    private MainApp mainApp;

    @FXML
    private MenuBar menuBar;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MenuBarController(){
    }

    /**
     * Initialize the controller class.
     * This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        /* NOW IS EMPTY */
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
}
