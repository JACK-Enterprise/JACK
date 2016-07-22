package com.jack.configuration;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

/**
 * Created by maxim on 21/07/2016.
 */
public class IniManager {
    private String iniPath;
    private Wini ini;
    private WatchThread watchThread;
    private Thread thread;

    public IniManager(){
        iniPath = "./config.ini";

        if(!ifExist()){
            try{
                new File(iniPath).createNewFile();
                ini = new Wini(new File(iniPath));
                resetDefaultFile();
            }
            catch (IOException e){
                System.out.println("Error when creating new configuration file: " + e.getMessage());
            }
        }

        else {

            try {
                ini = new Wini(new File(iniPath));
            } catch (IOException e) {
                System.out.println("Error when opening the configuration file: " + e.getMessage());
            }
        }

    }

    private void resetDefaultFile(){
        try{
            Date now = new Date();

            ini.setComment("Configuration file for the JACK app" +
                    "\n#This file should not be modified directly outside the app " +
                    "\n#Any modification at your own risk " +
                    "\n#This file was structured by Maxime Pelte for JACK Enterprise" +
                    "\n#Last modified " + now.toString() + " by System:JACK:App");

            ini.put("user", "lang", "en-US");
            ini.put("data", "version", 1.0);
            ini.put("data", "site", "https://github.com/JACK-Enterprise/JACK");
            ini.put("imagery", "type", "WMS");
            ini.put("imagery", "server", "http://geoservices.brgm.fr/geologie");
            ini.put("imagery", "layers", "SCAN_F_GEOL250");
            ini.put("imagery", "port", "default");

            ini.store();

        }
        catch (IOException e){
            System.out.println("Error when reading the configuration file: " + e.getMessage());
        }
    }

    private boolean ifExist(){
        return new File(iniPath).exists();
    }

    public String getStringValue(String section, String key){
        return ini.get(section, key);
    }

    public double getDoubleValue(String section, String key){
        return ini.get(section, key, double.class);
    }
    
    public int getIntValue(String section, String key){
        return ini.get(section, key, int.class);
    }

    public boolean getBooleanValue(String section, String key){
        return ini.get(section, key, boolean.class);
    }

    public void putValue(String section, String key, String value){
        ini.put(section, key, value);
        try{
            ini.store();
        } catch (IOException e){
            System.out.println("Error when adding value: " + e.getMessage());
        }
    }

    public void putValue(String section, String key, int value){
        ini.put(section, key, value);
        try{
            ini.store();
        } catch (IOException e){
            System.out.println("Error when adding value: " + e.getMessage());
        }
    }

    public void putValue(String section, String key, double value){
        ini.put(section, key, value);
        try{
            ini.store();
        } catch (IOException e){
            System.out.println("Error when adding value: " + e.getMessage());
        }
    }

    public void putValue(String section, String key, boolean value){
        ini.put(section, key, value);
        try{
            ini.store();
        } catch (IOException e){
            System.out.println("Error when adding value: " + e.getMessage());
        }
    }

    public void replaceValue(String section, String key, String value){
        ini.remove(section, key);
        putValue(section, key, value);
    }

    public void replaceValue(String section, String key, int value){
        ini.remove(section, key);
        putValue(section, key, value);
    }

    public void replaceValue(String section, String key, double value){
        ini.remove(section, key);
        putValue(section, key, value);
    }

    public void replaceValue(String section, String key, boolean value){
        ini.remove(section, key);
        putValue(section, key, value);
    }

    public void removeValue(String section, String key){
        ini.remove(section, key);
    }

    public void watchModification(){
        watchThread = new WatchThread("./");
        thread = new Thread(watchThread);
        System.out.println("WatchThread is running!");
    }

    public void stopWatching() throws InterruptedException{
        watchThread.terminate();
        thread.join();
        System.out.println("WatchThread is stopping!");
    }
}


