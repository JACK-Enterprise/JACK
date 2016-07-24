package com.jack.wms;

import static com.jack.core.StdDevLib.*;
import static com.jack.core.JackMath.*;
import com.jack.core.Description;
import com.jack.core.DeveloperError;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import com.jack.engine.EmpriseCoord;
import com.jack.engine.GPSCoord;
import static java.lang.Math.PI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Maxime on 04/05/2016.
 */
public class WMSImageryProvider {

    private String serverURL;
    private String layers;
    private DefaultParameters defaultParameters;
    private URL templateURL;
    private String getMapTemplate;
    private String getMapUri;
    private LinkedList<Thread> threadList;
    
    private static final int THREAD_NUMBER_LIMIT = 25;
    
    /***
     * Constructor to create a new WMS class
     * @param description
     */
    public WMSImageryProvider(Description description) {
        threadList = new LinkedList<Thread>();
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
                "&WIDTH={res}" +
                "&HEIGHTt={res}" +
                "&FORMAT={format}";
    }

    /***
     * Constructor to create a new WMS class
     * @param uri
     * @param layers
     */
    public WMSImageryProvider(String uri, String layers) {

        threadList = new LinkedList<Thread>();
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
                "&WIDTH={res}" +
                "&HEIGHT={res}" +
                "&FORMAT=image/png";

    }

    public void getTiledMap(EmpriseCoord emprise, double fov, double radius){
        GPSCoord minCoord = emprise.getMinCoord();
        GPSCoord maxCoord = emprise.getMaxCoord();
        double stepX = 1;
        double stepY = 1;
        double minLg = Math.floor(minCoord.getLongitude() / stepX) * stepX;
        double minLat = Math.floor(minCoord.getLatitude() / stepY) * stepY;
        double maxLg = Math.ceil(maxCoord.getLongitude() / stepX) * stepX;
        double maxLat = Math.ceil(maxCoord.getLatitude() / stepY) * stepY;
        double w = stepX * 2 * PI * radius / 360;
        double h = stepY * 2 * PI * radius / 360;
        double nextI;
        double nextJ;
        int res = fov > 20 ? 32 : (fov > 10 ? 64 : (fov > 5 ? 128 : 256));

        for(double i = minLg ; i < maxLg ; i+=stepX)
        {
            nextI = i+1;
            
            if(nextI >= 180)
            {
                nextI -= 360;
            }
            else if(nextI <= -180)
            {
                nextI += 360;
            }
            for(double j = minLat ; j < maxLat ; j+=stepY)
            {
                try{
                    
                    nextJ = j+1;
                    if(nextJ > 90)
                    {
                        nextJ = 89;
                    }
                    else if(nextJ < -90)
                    {
                        nextJ = -89;
                    }

                    
                    EmpriseCoord tmp = new EmpriseCoord(i, j, nextI, nextJ);
                    updateUri(i, j, nextI, nextJ, res);
                    File f = new File("./tmp/" + i + "_" + j + "_" + res + ".png");
                    if(!f.exists())
                    {
                        getMap(tmp, i + "_" + j + "_" + res, res);
                    }
                    
                }
                catch (IOException e){
                    System.out.println("Error when getting map: " + e.getMessage());
                }
            }
        }
        
        for (Thread thread : threadList)
        {
            try {
                synchronized (thread) {
                    while (thread.isAlive()) {
                        thread.wait();
                    }
                    thread.interrupt();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(WMSImageryProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getMap(EmpriseCoord emprise, int res) throws MalformedURLException, IOException{

        updateUri(emprise, res);
        String folderPath = "./tmp";
        File file = new File(folderPath + "/wms.png");

        URL url = new URL(getMapUri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        InputStream imgStream = connection.getInputStream();

        if(!new File(folderPath).exists()) {
            new File(folderPath).mkdirs();
        }
        if(!file.exists())
            file.createNewFile();

        FileOutputStream output = new FileOutputStream(file);

        int bytesRead = -1;
        byte[] buffer = new byte[4096];

        while((bytesRead = imgStream.read(buffer)) != -1)
            output.write(buffer, 0, bytesRead);

        imgStream.close();
        output.close();

    }

    public void getMap(EmpriseCoord emprise, String id, int res) throws MalformedURLException, IOException{

        updateUri(emprise, res);
        String folderPath = "./tmp";
        
        HTTPRequest httpRequest = new HTTPRequest(getMapUri, id);
        Thread thread = new Thread(httpRequest);
        thread.start();
        threadList.add(thread);
    }

    private void buildGetMapUri(){
        getMapUri = serverURL + getMapTemplate;
        getMapUri = getMapUri.replace("{format}", defaultParameters.getFormat());
        getMapUri = getMapUri.replace("{layers}", layers);
    }

    private void updateUri(int minX, int minY, int maxX, int maxY, int res){
        buildGetMapUri();
        getMapUri = getMapUri.replace("{minX}", ""+minX);
        getMapUri = getMapUri.replace("{minY}", ""+minY);
        getMapUri = getMapUri.replace("{maxX}", ""+maxX);
        getMapUri = getMapUri.replace("{maxY}", ""+maxY);
        getMapUri = getMapUri.replace("{res}", ""+res);

    }
    
    private void updateUri(double minX, double minY, double maxX, double maxY, int res){
        buildGetMapUri();
        getMapUri = getMapUri.replace("{minX}", ""+minX);
        getMapUri = getMapUri.replace("{minY}", ""+minY);
        getMapUri = getMapUri.replace("{maxX}", ""+maxX);
        getMapUri = getMapUri.replace("{maxY}", ""+maxY);
        getMapUri = getMapUri.replace("{res}", ""+res);
    }

    private void updateUri(EmpriseCoord emprise, int res){
        GPSCoord minCoord = emprise.getMinCoord();
        GPSCoord maxCoord = emprise.getMaxCoord();

        buildGetMapUri();
        getMapUri = getMapUri.replace("{minX}", ""+minCoord.getLongitude());
        getMapUri = getMapUri.replace("{minY}", ""+minCoord.getLatitude());
        getMapUri = getMapUri.replace("{maxX}", ""+maxCoord.getLongitude());
        getMapUri = getMapUri.replace("{maxY}", ""+maxCoord.getLatitude());
        getMapUri = getMapUri.replace("{res}", ""+res);
    }

}
