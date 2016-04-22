package com.jack.address.view;

/**
 * Created by Maxime on 22/04/2016.
 */

import com.sun.javaws.Main;
import javafx.fxml.FXML;
import com.jack.address.MainApp;

/**
 * The controller class for the RootLayout
 */
public class MenuBarController {

    // Reference to the main application
    private MainApp mainApp;

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
}
