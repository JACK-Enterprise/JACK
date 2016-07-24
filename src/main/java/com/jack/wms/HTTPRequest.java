package com.jack.wms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maxim on 23/07/2016.
 */
public class HTTPRequest {


    protected volatile InputStream imgStream;

    public void openConnection(String uri){
        try {
            URL url = new URL(uri);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                imgStream = connection.getInputStream();
            }
            catch (IOException e){
            }
        }
        catch (MalformedURLException e){

        }
    }

    public void writeFile(String folderPath, String fileName){
        synchronized (this){
            File file = new File(folderPath + fileName);
            //On crée le ficher dans le système de fichiers du système hôte
            if(!new File(folderPath).exists()) {
                new File(folderPath).mkdirs();
            }
            try {
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


        }
    }

}
