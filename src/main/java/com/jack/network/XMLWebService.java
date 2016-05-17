package com.jack.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Maxime on 11/05/2016.
 */
public class XMLWebService {

    private URL url;

    public XMLWebService(URL url){
        this.url = url;
    }

    public XMLWebService(){

    }

    public StringBuilder getXMLFromHTTP() throws IOException {

        StringBuilder xmlString = new StringBuilder();
        String tmp;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        InputStream xml = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(xml));

        while((tmp = reader.readLine()) != null){

            xmlString.append(tmp + '\n');
        }

        connection.disconnect();

        return xmlString;
    }
}
