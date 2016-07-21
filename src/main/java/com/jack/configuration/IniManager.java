package com.jack.configuration;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by maxim on 21/07/2016.
 */
public class IniManager {
    String iniPath;
    Wini ini;

    public IniManager(){
        iniPath = "./config.ini";

        if(!ifExist()){
            try{
                new File(iniPath).createNewFile();
                resetDefaultFile();
            }
            catch (IOException e){
                System.out.println("Error when creating new configuration file: " + e.getMessage());
            }
        }

        try{
            ini = new Wini(new File(iniPath));
        }
        catch (IOException e){
            System.out.println("Error when opening the configuration file: " + e.getMessage());
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

            ini.put("user", "lang", "default");
            ini.put("data", "version", 1.0);
            ini.put("data", "site", "https://github.com/JACK-Enterprise/JACK");
            ini.put("imagery", "type", "defalut");
            ini.put("imagery", "server", "default");
            ini.put("imagery", "layers", "default");
            ini.put("imagery", "port", "default");
            ini.put("default", "lang", "en-US");
            ini.put("default", "service", "WMS");
            ini.put("default", "server", "http://geoservices.brgm.fr/geologie");
            ini.put("default", "layer", "SCAN_F_GEOL250");
            ini.put("default", "port", "none");

            ini.store();
        }
        catch (IOException e){
            System.out.println("Error when reading the configuration file: " + e.getMessage());
        }
    }

    private boolean ifExist(){
        if(new File(iniPath).exists())
            return true;
        else
            return false;
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
}


