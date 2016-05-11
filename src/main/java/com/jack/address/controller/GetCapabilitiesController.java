package com.jack.address.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Maxime on 11/05/2016.
 */
public class GetCapabilitiesController {

    private Stage GetCapabilitiesStage;
    private boolean click = false;

    @FXML
    private TextField textUrl;


    @FXML
    private void initialize() {

    }

    @FXML
    private void handleCancelButton() {
        click = true;
        GetCapabilitiesStage.close();
    }

    @FXML
    private void handleSendButton() {
        System.out.println("URL : " + textUrl.getText());
    }

    public void setStage(Stage stage) {

        this.GetCapabilitiesStage = stage;

    }

    public boolean isClick() {
        return click;
    }

}
