package com.jack.address.controller;


import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Maxime on 06/05/2016.
 */
public class GetCapabilitiesController {
    private Stage stage;
    private boolean click;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private  void initialize(){

    }
    @FXML
    private void handleCancel(){
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isClick(){
        return this.click;
    }



}
