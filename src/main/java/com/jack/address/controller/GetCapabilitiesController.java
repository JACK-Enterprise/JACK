package com.jack.address.controller;


import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Maxime on 06/05/2016.
 */
public class GetCapabilitiesController {

    private Stage stage;
    private boolean click;
    private boolean cancelClick;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCancel(){
        cancelClick = true;
        this.stage.close();
    }



    public boolean isClick(){
        return this.click;
    }

    public boolean isCancelClick(){
        return this.cancelClick;
    }



}
