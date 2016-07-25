/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Aurelien
 */
public class CustomClassLoader extends URLClassLoader {
    
    public CustomClassLoader(URL[] urls) {
        super(urls);
    }
     
    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
  
}
