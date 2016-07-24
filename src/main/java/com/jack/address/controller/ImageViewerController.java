package com.jack.address.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Created by maxim on 15/06/2016.
 */
public class ImageViewerController {

    private Stage imageStage;
    private boolean clicked = false;

    @FXML
    private ImageView imageContainer;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){

    }

    /**
     * Sets the stage of this window
     * @param imageStage
     */
    public void setImageStage(Stage imageStage){
        this.imageStage = imageStage;
    }

    /**
     * Returns the boolean
     * @return
     */
    public boolean isClicked(){return clicked;}

    @FXML
    private void handleButton(){

        String path = "file:./tmp/wms.png";
        Image img = new Image(path);
        imageContainer.setImage(img);
        
    }
}
