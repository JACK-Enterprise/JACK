package com.jack.address.controller;

import com.jack.configuration.IniManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by maxim on 21/07/2016.
 */
public class SetServerImageryController {
    private Stage serverImageryStage;
    private boolean click = false;

    @FXML
    private TextField serverURL;
    @FXML
    private TextField layer;

    @FXML
    private void initialize() {
    }

    public void setServerImageryStage(Stage serverImageryStage) {
        this.serverImageryStage = serverImageryStage;
    }

    @FXML
    private void handleCancelButton() {
        click = true;
        serverImageryStage.close();
    }

    @FXML
    private void handleApplyButton(){
        IniManager ini = new IniManager();

        ini.removeValue("imagery", "server");
        ini.removeValue("imagery", "layers");

        ini.putValue("imagery", "server", serverURL.getText());
        ini.putValue("imagery", "layers", layer.getText());

        click = true;
        serverImageryStage.close();
    }

    public boolean isClick() {
        return click;
    }

}
