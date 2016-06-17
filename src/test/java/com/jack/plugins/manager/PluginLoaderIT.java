/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import com.jack.annotationparser.AnnotationFunctionBinder;
import java.nio.file.Paths;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Aurelien
 */
public class PluginLoaderIT {
    
    private static String PLUGIN_PATH = "src/test/resources/testPlugin.jar";
    private static int NB_PLUGINS = 1;
    @Test
    public void shouldParsePlugin() throws Exception {
        PluginLoader loader = new PluginLoader (PLUGIN_PATH, new ArrayList<AnnotationFunctionBinder>());
        
        ArrayList<Plugin> plugins = loader.load();
        
        assertEquals(NB_PLUGINS, plugins.size());
        
        PluginDescription descr = plugins.get(0).getDescription();
        assertEquals("author", descr.getAuthor());
        assertEquals("name", descr.getName());
        assertEquals("version", descr.getVersion());
        assertEquals("description", descr.getDescription());
    }
}
