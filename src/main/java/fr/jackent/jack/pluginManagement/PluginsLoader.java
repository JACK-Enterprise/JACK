package fr.jackent.jack.pluginManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@AllArgsConstructor
public class PluginsLoader {

    @Getter @Setter private String mainClassId;
    private static ClassLoader classLoader;

    public List<Pluggable> load(String folderPath){
        List<Pluggable> plugins = new ArrayList<Pluggable>();
        final List<URL> urls = new ArrayList<URL>();

        List<String> classes = getPluginsByFolder(urls, folderPath);

        AccessController.doPrivileged(new PrivilegedAction<Object>(){
            @Override
            public Object run() {
                classLoader = new URLClassLoader(
                        urls.toArray(new URL[urls.size()]),
                        PluginsLoader.class.getClassLoader());

                return null;
            }
        });

        for(String c : classes){
            try {
                Class<?> moduleClass = Class.forName(c, true, classLoader);

                if(Pluggable.class.isAssignableFrom(moduleClass)){
                    Class<Pluggable> castedClass = (Class<Pluggable>)moduleClass;

                    Pluggable module = castedClass.newInstance();

                    plugins.add(module);
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return plugins;
    }

    private List<String> getPluginsByFolder(List<URL> urls, String folderPath) {

        List<String> classes = new ArrayList<String>();

        File[] files = new File(folderPath).listFiles(new ModuleFilter());

        for(File f : files){
            JarFile jarFile = null;

            try {
                jarFile = new JarFile(f);

                Manifest manifest = jarFile.getManifest();

                String moduleClassName = manifest.getMainAttributes().getValue(mainClassId);

                classes.add(moduleClassName);

                urls.add(f.toURI().toURL());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(jarFile != null){
                    try {
                        jarFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return classes;
    }

    private static class ModuleFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".jar");
        }
    }
}