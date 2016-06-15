package com.jack.wms;



import static com.jack.core.StdDevLib.*;
import com.jack.core.Description;
import com.jack.core.DeveloperError;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.jack.core.ImageryProvider;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;


/**
 * Created by Maxime on 04/05/2016.
 */
public class WMSImageryProvider  {

    private String serverURL;
    private String layers;
    private DefaultParameters defaultParameters;
    private URL templateURL;
    private String getMapTemplate;
    private String getMapUri;
    /***
     * Constructor to create a new WMS class
     * @param description
     */
    public WMSImageryProvider(Description description) {

        description = defaultValue(description, new Description());
        if(!defined(description.url)) {
            DeveloperError error = new DeveloperError("Error: URL is required");
        }
        if(!defined(description.layers)){
            DeveloperError error = new DeveloperError("Error: Layers is required");
        }

        this.serverURL = description.url;
        this.layers = description.layers;
        this.defaultParameters = new DefaultParameters();
        this.getMapTemplate = "/wms?" +
                "REQUEST=GetMap" +
                "&SERVICE=WMS" +
                "&VERSION=1.1.1" +
                "&LAYERS={layers}" +
                "&styles={styles}" +
                "&SRS=" +
                "&bbox={northen, west, southern, east}" +
                "&WIDTH=256" +
                "&HEIGHTt=256" +
                "&FORMAT={format}";
    }

    /***
     * Constructor to create a new WMS class
     * @param uri
     * @param layers
     */
    public WMSImageryProvider(String uri, String layers) {

        this.serverURL = uri;
        this.layers = layers;
        this.defaultParameters = new DefaultParameters();
        this.getMapTemplate = "?" +
                "SERVICE=WMS" +
                "&VERSION=1.1.1" +
                "&REQUEST=GetMap" +
                "&LAYERS={layers}" +
                "&SRS=EPSG:4326" +
                "&BBOX={minX},{minY},{maxX},{maxY}" +
                "&WIDTH=256" +
                "&HEIGHT=256" +
                "&FORMAT=image/png";

    }

    private void buildGetMapUri(){
        getMapUri = serverURL + getMapTemplate;
        getMapUri = getMapUri.replace("{format}", defaultParameters.getFormat());
        getMapUri = getMapUri.replace("{layers}", layers);
    }

    public void getMap() throws MalformedURLException, IOException{

        updateUri(0, 47, 5, 44);
        String path = "./tmp/";

        URL url = new URL(getMapUri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        InputStream imgStream = connection.getInputStream();

        File file = new File(path + "imgTest.png");

        //On crée le ficher dans le système de fichiers du système hôte
        if(!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream output = new FileOutputStream(file);

        int bytesRead = -1;
        byte[] buffer = new byte[4096];

        //Chaque byte lut en provenance du serveur Web est écrit dans le fichier
        while((bytesRead = imgStream.read(buffer)) != -1)
            output.write(buffer, 0, bytesRead);

        //On ferme les flux
        imgStream.close();
        output.close();

    }

    private void updateUri(int minX, int minY, int maxX, int maxY){
        buildGetMapUri();
        getMapUri = getMapUri.replace("{minX}", ""+minX);
        getMapUri = getMapUri.replace("{minY}", ""+minY);
        getMapUri = getMapUri.replace("{maxX}", ""+maxX);
        getMapUri = getMapUri.replace("{maxY}", ""+maxY);

        System.out.println(getMapUri);

    }






}
