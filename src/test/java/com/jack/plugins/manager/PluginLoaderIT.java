/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import java.nio.file.Paths;
import org.junit.Test;

/**
 *
 * @author Aurelien
 */
public class PluginLoaderIT {
    
    private static String PLUGIN_PATH = "src/test/resources/testPlugin.jar";
    @Test
    public void shouldParsePlugin() throws Exception {
        PluginLoader loader = new PluginLoader(PLUGIN_PATH);
        
        loader.load();
    }
}
