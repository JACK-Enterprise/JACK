package com.jack.core;

/**
 * Created by Maxime on 17/05/2016.
 */
public interface ImageryProvider {

    int minimumLevel();
    int maximumLevel();
    int tileHeight();
    int tileWidth();
    Rectangle rectangle();

    void loadImage(String url);


}
