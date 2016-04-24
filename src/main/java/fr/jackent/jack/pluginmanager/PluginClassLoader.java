/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.pluginmanager;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Aurelien
 */
@Slf4j
public class PluginClassLoader extends URLClassLoader {

	private static final String PLUGIN_PACKAGE_PREFIX = "ro.fortsoft.pf4j.";

	private PluginManager pluginManager;
	private PluginDescriptor pluginDescriptor;

	public PluginClassLoader(PluginManager pluginManager, PluginDescriptor pluginDescriptor, ClassLoader parent) {
		super(new URL[0], parent);

		this.pluginManager = pluginManager;
		this.pluginDescriptor = pluginDescriptor;
	}

	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}

    /**
     * This implementation of loadClass uses a child first delegation model rather than the standard parent first.
     * If the requested class cannot be found in this class loader, the parent class loader will be consulted
     * via the standard ClassLoader.loadClass(String) mechanism.
     */
	@Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(className)) {
            log.trace("Received request to load class '{}'", className);
            // if the class it's a part of the plugin engine use parent class loader
            if (className.startsWith(PLUGIN_PACKAGE_PREFIX)) {
                log.trace("Delegate the loading of class '{}' to parent", className);
                try {
                    return getClass().getClassLoader().loadClass(className);
                } catch (ClassNotFoundException e) {
                    // try next step
                    // TODO if I uncomment below lines (the correct approach) I received ClassNotFoundException for demo (ro.fortsoft.pf4j.demo)
                    //                log.error(e.getMessage(), e);
                    //                throw e;
                }
            }

            // second check whether it's already been loaded
            Class<?> clazz = findLoadedClass(className);
            if (clazz != null) {
                log.trace("Found loaded class '{}'", className);
                return clazz;
            }

            // nope, try to load locally
            try {
                clazz = findClass(className);
                log.trace("Found class '{}' in plugin classpath", className);
                return clazz;
            } catch (ClassNotFoundException e) {
                // try next step
            }

            // look in dependencies
            log.trace("Look in dependencies for class '{}'", className);
            List<PluginDependency> dependencies = pluginDescriptor.getDependencies();
            for (PluginDependency dependency : dependencies) {
                PluginClassLoader classLoader = pluginManager.getPluginClassLoader(dependency.getPluginId());
                try {
                    return classLoader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    // try next dependency
                }
            }

            log.trace("Couldn't find class '{}' in plugin classpath. Delegating to parent", className);

            // use the standard URLClassLoader (which follows normal parent delegation)
            return super.loadClass(className);
        }
    }

    /**
     * Load the named resource from this plugin. This implementation checks the plugin's classpath first
     * then delegates to the parent.
     *
     * @param name the name of the resource.
     * @return the URL to the resource, <code>null</code> if the resource was not found.
     */
    @Override
    public URL getResource(String name) {
        log.trace("Trying to find resource '{}' in plugin classpath", name);
        URL url = findResource(name);
        if (url != null) {
            log.trace("Found resource '{}' in plugin classpath", name);
            return url;
        }

        log.trace("Couldn't find resource '{}' in plugin classpath. Delegating to parent");

        return super.getResource(name);
    }

    @Override
    public URL findResource(String name) {
        return super.findResource(name);
    }

    /**
     * Release all resources acquired by this class loader.
     * The current implementation is incomplete.
     * For now, this instance can no longer be used to load
     * new classes or resources that are defined by this loader.
     */
    public void dispose() {
        try {
            close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}