package com.jack.network;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maxime on 11/05/2016.
 */
public class GetCapabilities {
    private URL url;
    private String uri;

    public GetCapabilities(String url){
        this.uri = url;
        try {
            this.url = new URL(this.uri);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }


    public void getCapabilities() throws IOException{


    }

    public void buildUrl(String service, String version){
        String model = "?SERVICE={service}&VERSION=[version]&REQUEST=GetCapabilities";
        String uri = this.uri + model;
        uri.replace("{service}", service);
        uri.replace("{version}", version);

        try{
            url = new URL(uri);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    /* BuildUrl from description
     TODO : Faire la methode et ameliorer la classe description
    public void buildUrl(Description description){
        String model = "?SERVICE={service}&VERSION=[version]&REQUEST=GetCapabilities";
        String uri = this.uri + model;
        uri.replace("{service}", );
        uri.replace("{version}", version);

        try{
            url = new URL(uri);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    */

    private void buildWMSUrl(){
        String uri = this.uri + "?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities";
        System.out.println(uri);
        // url.replace("{service}", defaultParameters.getService());
        //url.replace("{version}", defaultParameters.getVersion());

        try{
            url = new URL(uri);
        }
        catch (MalformedURLException e){

        }
    }


}
