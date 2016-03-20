package fr.jackent.jack.pluginManagement;

/**
 * Created by Aurelien on 02/21/16.
 */

public interface Pluggable {
    public void plug();
    public void unplug();
    public String getName();
}

