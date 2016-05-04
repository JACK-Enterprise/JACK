package com.jack.wms;

import com.jack.core.Description;

/**
 * Created by Maxime on 04/05/2016.
 */
public class WMSImageryProvider {

    private String url;
    private String layers;
    private DefaultParameters defaultParameters;

    public WMSImageryProvider(Description description){
        this.url = description.url;
        this.layers = description.layers;
        this.defaultParameters = new DefaultParameters();
    }

    public WMSImageryProvider(String url, String layers){
        this.url = url;
        this.layers = layers;
        this.defaultParameters = new DefaultParameters();
    }

}
