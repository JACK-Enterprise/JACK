/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.pluginmanager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aurelien
 */
public class PluginClassPath {

    private static final String DEFAULT_CLASSES_DIRECTORY = "classes";
    private static final String DEFAULT_LIB_DIRECTORY = "lib";

    protected List<String> classesDirectories;
    protected List<String> libDirectories;

    public PluginClassPath() {
            classesDirectories = new ArrayList<>();
            libDirectories = new ArrayList<>();

            addResources();
    }

    public List<String> getClassesDirectories() {
            return classesDirectories;
    }

    public void setClassesDirectories(List<String> classesDirectories) {
            this.classesDirectories = classesDirectories;
    }

    public List<String> getLibDirectories() {
            return libDirectories;
    }

    public void setLibDirectories(List<String> libDirectories) {
            this.libDirectories = libDirectories;
    }

    protected void addResources() {
            classesDirectories.add(DEFAULT_CLASSES_DIRECTORY);
            libDirectories.add(DEFAULT_LIB_DIRECTORY);
    }

}
