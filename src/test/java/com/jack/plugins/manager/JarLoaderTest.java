/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;
import java.net.URLClassLoader;
import java.util.Enumeration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Aurelien
 */
public class JarLoaderTest {

    private static final String[] EXPECTED_CLASSES = new String[] {
                "com/jack/plugins/manager/PluginBase.class",
                "com/jack/plugins/TestPlugin.class"
            };
    private static String PLUGIN_PATH = "src/test/resources/testPlugin.jar";
    
    @Test
    public void shouldParseJarFile() throws Exception{
        JarLoader jarLoader = new JarLoader(PLUGIN_PATH);
        
        Enumeration res = jarLoader.parse();
        int counter = 0;
        
        while(res.hasMoreElements()){

            String tmp = res.nextElement().toString();
            
            for(int i = 0 ; i < EXPECTED_CLASSES.length ; i++)
            {
                if(tmp.equals(EXPECTED_CLASSES[i])) {
                    counter++;
                    break;
                }
            }
        }
        assertEquals(EXPECTED_CLASSES.length, counter);
    }
    
    @Test
    public void shouldGetClassLoader() throws Exception {
        JarLoader jarLoader = new JarLoader(PLUGIN_PATH);
        URLClassLoader classLoader = jarLoader.getClassLoader(); 
       
        assertTrue(classLoader != null);
    }
}
