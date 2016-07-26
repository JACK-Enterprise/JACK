/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurelien
 */
public class CustomClassLoader {
    
    private static final Class[] parameters = new Class[]{URL.class};

    public static void addFile(String s) {
       File f = new File(s);
       addFile(f);
    }//end method

    public static void addFile(File f) {
        try {
            addURL(f.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CustomClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end method


    public static void addURL(URL u) {

        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
           Method method = sysclass.getDeclaredMethod("addURL", parameters);
           method.setAccessible(true);
           method.invoke(sysloader, new Object[]{u});
        } catch (Throwable t) {
           t.printStackTrace();
        }//end try catch

    }//end method
  
}
