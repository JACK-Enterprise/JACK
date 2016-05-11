package com.jack.address.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Maxime on 11/05/2016.
 */
public class GetCapabilitiesController {

    private Stage GetCapabilitiesStage;
    private boolean click = false;


    @FXML
    private void initialize(){

    }

    @FXML
    private void handleCancelButton(){
        click = true;
        GetCapabilitiesStage.close();
    }


    public void setStage(Stage stage){

        this.GetCapabilitiesStage = stage;

    }

    public boolean isClick(){
        return click;
    }


}
