package com.jack.engine;

import javafx.scene.Scene;

/**
 * Created by Maxime on 01/06/2016.
 */
public class Cursor {

    private Scene scene;

    public Cursor(Scene scene){
       this.scene = scene;
    }

    public void setCursorOnload(){
        scene.setCursor(javafx.scene.Cursor.WAIT);
    }

    public void setCursorOnDrag(){
        scene.setCursor(javafx.scene.Cursor.CLOSED_HAND);
    }

    public void setDefaultCursor(){
        scene.setCursor(javafx.scene.Cursor.DEFAULT);
    }

    public void setCursorPrecise(){
        scene.setCursor(javafx.scene.Cursor.CROSSHAIR);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
