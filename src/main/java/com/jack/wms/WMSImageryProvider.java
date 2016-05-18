package com.jack.wms;

import static com.jack.core.StdDevLib.*;
import com.jack.core.Description;
import com.jack.core.DeveloperError;

import javax.management.Query;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * Created by Maxime on 04/05/2016.
 */
public class WMSImageryProvider {

    private String url;
    private String layers;
    private DefaultParameters defaultParameters;
    private URL templateURL;


    public WMSImageryProvider(Description description) {

        description = defaultValue(description, new Description());
        if(!defined(description.url)) {
            DeveloperError error = new DeveloperError("Error: URL is required");
        }
        if(!defined(description.layers)){
            DeveloperError error = new DeveloperError("Error: Layers is required");
        }

        this.url = description.url;
        this.layers = description.layers;
        this.defaultParameters = new DefaultParameters();
    }

    public WMSImageryProvider(String url, String layers) {

        this.url = url;
        this.layers = layers;
        this.defaultParameters = new DefaultParameters();

    }

    private void buildUrl() {



    }



}
