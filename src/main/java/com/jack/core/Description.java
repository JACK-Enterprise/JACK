package com.jack.core;

/**
 * Created by Maxime on 04/05/2016.
 */
public class Description {

    public String url;
    public String layers;

    /**
     * Constructor for the class Description
     * Takes url & layers for params to use in ImageryProvider or TerrainProvider
     * @param url
     * @param layers
     */
    public Description(String url, String layers){
        this.url = url;
        this.layers = layers;
    }

    public Description(){
    }
}
