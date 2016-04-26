package com.jack.address.view;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import com.jack.address.MainApp;

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
    private  void initialize(){

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
}
