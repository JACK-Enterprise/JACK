package com.jack.address.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.net.URI;


/**
 * Created by Maxime on 26/04/2016.
 */
public class AboutController {

    private Stage aboutStage;
    private boolean clicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){

    }

    /**
     * Sets the stage of this window
     * @param aboutStage
     */
    public void setAboutStage(Stage aboutStage){
        this.aboutStage = aboutStage;
    }

    /**
     * Returns the boolean
     * @return Returns a boolean
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * Called when the user clicks on the window
     */
    @FXML
    private void handleClick(){
        clicked = true;
        aboutStage.close();
    }

    @FXML
    private void handleLink() throws Exception{
        URI uri = new URI("https://github.com/JACK-Enterprise");
        java.awt.Desktop.getDesktop().browse(uri);
    }
}
