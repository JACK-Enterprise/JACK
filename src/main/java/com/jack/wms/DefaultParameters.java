package com.jack.wms;

/**
 * Created by Maxime on 04/05/2016.
 */
public class DefaultParameters {

     private String service;
     private String version;
     private String request;
     private String style;
     private String format;

    public DefaultParameters(){
        service = "WMS";
        version = "1.1.1";
        request = "GetMap";
        style = "";
        format = "image/jpeg";
    }

    public String getService() {
        return service;
    }

    public String getVersion() {
        return version;
    }

    public String getRequest() {
        return request;
    }

    public String getStyle() {
        return style;
    }

    public String getFormat() {
        return format;
    }
}
