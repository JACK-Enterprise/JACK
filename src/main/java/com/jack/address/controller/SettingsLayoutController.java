package com.jack.address.controller;

/**
 * Created by Maxime on 22/04/2016.
 */

import javafx.fxml.FXML;
import javafx.stage.Stage;



public class SettingsLayoutController {

    private Stage settingsStage;
    private boolean okClicked = false;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){

    }

    /**
     * Sets the stage of this window
     *
     * @param settingsStage
     */
    public void setSettingsStage(Stage settingsStage){
        this.settingsStage = settingsStage;
    }


    /**
     * Returns true if the user clicked OK, false otherwise
     * @return Returns a boolean
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     * If the user has changed some settings
     * it automatically saves the new settings before closing the window
     */
    @FXML
    private void handleOk(){
        /* IMPORTANT NOTE
        When the user click ok it close the window
        When the user click apply it saves the new settings
        Apply doesn't close the window
        Ok close the windows & and it does an automatic apply !!!!
         */

        // TODO Applying the apply here !
        okClicked = true;
        settingsStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel(){
        settingsStage.close();
    }

}
