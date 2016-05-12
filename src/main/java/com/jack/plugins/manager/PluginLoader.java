package com.jack.plugins.manager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * Plugin Loader class that is in charge of loading plugins from Jar files
 * @author Aurelien
 */
@Slf4j
public class PluginLoader {

	private String path;
	
	public PluginLoader(String path){
            this.path = path;
	}
	
	public PluginBase load() {
		
            ArrayList<Class> classes = this.initializeLoader();
            PluginBase plugin = null;

            /** TO DO : PARSE PLUGIN **/
            
            return plugin;	
	}
	
	private ArrayList<Class> initializeLoader(){
            
            JarLoader loader = new JarLoader(path);
            Enumeration enumeration;
            try {
                enumeration = loader.parse();
            } catch (IOException ex) {
                log.error(path + " file failed to be loaded. Aborting...");
                return null;
            }
            URLClassLoader classLoader = loader.getClassLoader();
            ArrayList<Class> classes = new ArrayList<Class>();
            
            while(enumeration.hasMoreElements()){

                String tmp = enumeration.nextElement().toString();

                if(tmp.length() > 6 && tmp.substring(tmp.length()-6).compareTo(".class") == 0) {

                    tmp = tmp.substring(0,tmp.length()-6);
                    tmp = tmp.replaceAll("/",".");
                    try {
                        Class tmpClass = Class.forName(tmp ,true,classLoader);
                        if(tmpClass != null) {
                            classes.add(tmpClass);
                        }
                    } catch(ClassNotFoundException e) {
                        log.warn("Class " + tmp + "could not be loaded !");
                    }
                }
            }
            return classes;
	}
	
	
}
