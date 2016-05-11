package com.jack.wms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maxime on 11/05/2016.
 */
public class GetCapabilities {
    private URL uri;
    private String url;

    public GetCapabilities(String url){
        this.url = url;
        try {
            this.uri = new URL(this.url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }


    public void getCapabilities() throws IOException{
       // buildGetCapabilitiesUrl();
        getXML();
    }

    private void buildGetCapabilitiesUrl(){
        String url = this.url + "?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities";
        System.out.println(url);
        // url.replace("{service}", defaultParameters.getService());
        //url.replace("{version}", defaultParameters.getVersion());

        try{
            uri = new URL(url);
        }
        catch (MalformedURLException e){

        }
    }

    private void getXML() throws IOException {

        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(xml));
        String sLine;


        String result = "";
        while ((sLine = reader.readLine()) != null) {

            result = result + sLine + '\n';
        }
        System.out.print(result);

        connection.disconnect();


    }
}
