package com.jack.wms;

import com.jack.core.Description;
import com.sun.deploy.net.HttpRequest;
import com.sun.xml.internal.txw2.Document;
import jdk.internal.org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Maxime on 04/05/2016.
 */
public class WMSImageryProvider {

    private String url;
    private String layers;
    private DefaultParameters defaultParameters;
    private URL templateURL;
    private URL getCapabilities;

    public WMSImageryProvider(Description description) {
        this.url = description.url;
        this.layers = description.layers;
        this.defaultParameters = new DefaultParameters();
    }

    public WMSImageryProvider(String url, String layers) {
        this.url = url;
        this.layers = layers;
        this.defaultParameters = new DefaultParameters();
        buildGetCapabilitiesUrl();

    }

    private void buildGetCapabilitiesUrl(){
        String url = this.url + "?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities";
        System.out.println(url);
       // url.replace("{service}", defaultParameters.getService());
        //url.replace("{version}", defaultParameters.getVersion());

        try{
            getCapabilities = new URL(url);
        }
        catch (MalformedURLException e){

        }
    }

    public void GetCapabilities() throws IOException{

        HttpURLConnection connection = (HttpURLConnection)getCapabilities.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(xml));
        String sLine;

        System.out.print(xml);

        String result = "";
        while((sLine = reader.readLine()) != null){

            result = result + sLine + '\n';
        }
     //   System.out.print(result);

        connection.disconnect();


    }
}
