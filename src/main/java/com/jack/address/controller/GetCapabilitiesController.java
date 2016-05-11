package com.jack.address.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Maxime on 11/05/2016.
 */
public class GetCapabilitiesController {
    private Stage stage;
    private boolean click;

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleCancelButton(){
        click = true;
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean isClick(){
        return click;
    }


}
