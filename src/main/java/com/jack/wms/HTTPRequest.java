package com.jack.wms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by maxim on 22/07/2016.
 */
public class HTTPRequest implements Runnable {

    protected volatile String uri;
    protected volatile String id;
    private URL url;

    public HTTPRequest(String uri, String id){
        this.uri = uri;
        this.id = id;
        try {
            url = new URL(uri);
        }
        catch (MalformedURLException e){

        }
    }

    public synchronized void run(){
        try {

            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream imgStream = connection.getInputStream();

            String folderPath = "./tmp";
            File file = new File(folderPath + "/"+id+".png");
            //On crée le ficher dans le système de fichiers du système hôte
            if(!new File(folderPath).exists()) {
                new File(folderPath).mkdirs();
            }
            if(!file.exists())
                file.createNewFile();

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
        catch (IOException e){

        }
        
        this.notifyAll();
    }
}
