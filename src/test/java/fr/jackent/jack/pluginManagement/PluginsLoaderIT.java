package fr.jackent.jack.pluginManagement;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aurelien on 03/05/16.
 */
public class PluginsLoaderIT {

    @Test
    public void shouldParsePlugin() throws Exception {

        PluginsLoader loader = new PluginsLoader("mainClass-Id");

        ArrayList<Pluggable> plugins = (ArrayList<Pluggable>) loader.load("D:\\Git\\JACK\\src\\test\\ressources");

        assertTrue(plugins.get(0).getClass().getName().equals("TestingPlugin"));
    }
}
