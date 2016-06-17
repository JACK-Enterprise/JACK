
package com.jack.plugins.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that is in charge f loading Jar files and retrieve their components
 * @author Aurelien
 */
@Slf4j
public class JarLoader {
    
    private static final String MALFORMED_URL_MESSAGE = "Could not retrieve URL from file";
    private static final String FILE_NOT_FOUND_MESSAGE = "Could not find file";
    private static URLClassLoader loader = null;
    
    private String path;
    
    public JarLoader(String path) {
        this.path = path;
    }
    
    public URL getURL(File file) {
        URL url;
        try {
            url = file.toURL();
        } catch (MalformedURLException e) {
            final String message = MALFORMED_URL_MESSAGE + " \"" + this.path + "\"";
            log.warn(message);
            throw new IllegalArgumentException(message);
        }
        
        return url;
    }
    
    public Enumeration parse() throws IOException{
        File jarFile = new File(this.path);
        Enumeration enumeration;
			
        if( !jarFile.exists() ) {
            final String message = FILE_NOT_FOUND_MESSAGE + " \"" + this.path + "\"";
            log.warn(message);
            throw new IllegalArgumentException(message);
        }
			
        URL url = getURL(jarFile);

        JarFile jar = new JarFile(jarFile.getAbsolutePath());

        enumeration = jar.entries();
        
        return enumeration;
    }
    
    public URLClassLoader getClassLoader() {
        File jarFile = new File(this.path);
        Enumeration enumeration;
			
        if( !jarFile.exists() ) {
            final String message = FILE_NOT_FOUND_MESSAGE + " \"" + this.path + "\"";
            log.warn(message);
            throw new IllegalArgumentException(message);
        }
			
        URL url = getURL(jarFile);
        
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
         public Void run() {
             
            loader = new URLClassLoader(new URL[] {url});
            return null;
         }
     });
        URLClassLoader classLoader = loader; 
        
        return classLoader;
    }
}
