package fr.jackent.jack.pluginManagement;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aurelien on 03/05/16.
 */
public class PluginsLoaderIT {

    @Test
    public void shouldParsePlugin() throws Exception {
        LinkedList<String> files = new LinkedList<String>();
        files.add("D:\\Programming\\ESGI\\Java\\JACK\\src\\test\\ressources\\testingPlugin.jar");

        PluginsLoader loader = new PluginsLoader(files);

        ArrayList<Pluggable> plugins = loader.exec();

        assertTrue(plugins.get(0).getClass().getName().equals("TestingPlugin"));
    }
}
