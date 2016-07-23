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
public class HTTPRequestThread implements Runnable {

    protected volatile String uri;
    protected volatile int id;

    public HTTPRequestThread(String uri, int id){
        this.uri = uri;
        this.id = id;

    }

    public void run(){
        HTTPRequest request = new HTTPRequest();
        request.openConnection(uri);
        request.writeFile("./tmp", "/"+id+".png");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }

    }
}
