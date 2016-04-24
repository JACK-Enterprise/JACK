/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.pluginmanager;

/**
 *
 * @author Aurelien
 */
public class PluginState {

    public static final PluginState CREATED = new PluginState("CREATED");
    public static final PluginState DISABLED = new PluginState("DISABLED");
    public static final PluginState STARTED = new PluginState("STARTED");
    public static final PluginState STOPPED = new PluginState("STOPPED");

    private String status;

    private PluginState(String status) {
            this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginState that = (PluginState) o;

        if (!status.equals(that.status)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
            return status;
    }

}
