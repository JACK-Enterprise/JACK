package fr.jackent.jack.pluginManagement;

/**
 * Created by Aurelien on 02/21/16.
 */

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;
import java.util.jar.JarFile;

@Slf4j
public class PluginsLoader {

    @Getter @Setter private LinkedList<String> files;
    /**
     * Constructeur par défaut
     *
     */
    public PluginsLoader(){
        this.files = new LinkedList<String>();
    }

    /**
     * Constucteur initialisant le tableau de fichier à charger.
     * @param files Tableau de String contenant la liste des fichiers à charger.
     */
    public PluginsLoader(LinkedList<String> files) {
        this();
        this.files = files;
    }


    /**
     * Fonction de chargement de tout les plugins de type IntPlugins
     * @return Une collection de IntPlugins contenant les instances des plugins
     * @throws Exception si file = null ou file.length = 0
     */
    public ArrayList<Pluggable> exec() throws Exception {

        ArrayList<Pluggable> plugins = this.loadPlugins();

        for(Pluggable plugin : plugins){
            plugins.add(plugin.getClass().newInstance());
        }

        return plugins;
    }

    private ArrayList<Pluggable> loadPlugins() throws IllegalArgumentException, NullPointerException, FileNotFoundException, ClassNotFoundException {

        if(files == null || files.size() == 0 ){
            log.error("No argument files were given");
            throw new IllegalArgumentException("No argument files were given");
        }

        int filesCounter = files.size();
        ArrayList<Pluggable> plugins = new ArrayList<Pluggable>();

        for(int index = 0 ; index < filesCounter ; index ++ )
        {
            String filepath = this.files.removeFirst();

            plugins.addAll(loadJarFile(filepath));
        }
        return plugins;
    }

    private ArrayList<Pluggable> loadJarFile(String filepath) throws IllegalArgumentException {

        File pluginFile;
        try {
            pluginFile = loadFileByPath(filepath);
        }
        catch (FileNotFoundException e) {
            String exceptionMessage = "Given file is unreachable : \"" + filepath + "\".";
            log.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }


        URL pluginUrl = loadUrlByFile(pluginFile);

        URLClassLoader loader = new URLClassLoader(new URL[] {pluginUrl});

        JarFile pluginJar = loadJarFileByFile(pluginFile);

        return parseJarClasses(loader, pluginJar);
    }

    private File loadFileByPath(String filepath) throws FileNotFoundException {

        if(filepath == null)
        {
            log.error("Null filepath has been encountered while loading a plugin file");
            throw new NullPointerException("Null filepath has been encountered while loading a plugin file");
        }

        File file = new File(filepath);

        if(!file.exists() ) {
            log.error("No such plugin has been found : " + filepath);
            throw new FileNotFoundException("No such plugin has been found : " + filepath);
        }

        return file;
    }

    private URL loadUrlByFile(File pluginFile) {
        try
        {
           return pluginFile.toURI().toURL();
        }

        catch (MalformedURLException e)
        {
            String exceptionMessage = "Illegal file URL given for file : \"" + pluginFile.getAbsolutePath() + "\".";

            log.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private JarFile loadJarFileByFile(File pluginFile) {
        try
        {
            return new JarFile(pluginFile.getAbsolutePath());
        }

        catch (IOException e) {
            log.error("Given file is not a correct JAR archive : " + pluginFile.getAbsolutePath());
            throw new IllegalArgumentException("Given file is not a correct JAR archive : " + pluginFile.getAbsolutePath());
        }
    }

    private ArrayList<Pluggable> parseJarClasses(URLClassLoader loader, JarFile pluginJar) {
        Enumeration enumeration = pluginJar.entries();
        String jarClass = "";
        ArrayList<Pluggable> plugins = new ArrayList<Pluggable>();

        while(enumeration.hasMoreElements()) {
            jarClass = enumeration.nextElement().toString();
            try {
                plugins.addAll(parseJarClass(loader, jarClass));
            }
            catch (ClassNotFoundException e) {
                log.warn("Could not load \"" + jarClass + "\" class.");
            }
        }
        return plugins;
    }

    private ArrayList<Pluggable> parseJarClass(URLClassLoader loader, String pluginName) throws ClassNotFoundException {
        Class tmpClass = null;
        ArrayList<Pluggable> plugins = new ArrayList<Pluggable>();

        if(pluginName.length() > 6 && pluginName.substring(pluginName.length()-6).compareTo(".class") == 0) {

            pluginName = pluginName.substring(0,pluginName.length()-6);
            pluginName = pluginName.replace('/','.');
            System.out.println("Class name : " + pluginName);

            tmpClass = Class.forName(pluginName, false, loader);

            for(int i = 0 ; i < tmpClass.getInterfaces().length; i ++ )
            {
                if(tmpClass.getInterfaces()[i].getName().equals("fr.jackent.jack.pluginManagement.Pluggable")) {
                    plugins.add(Pluggable.class.cast(tmpClass));
                }
            }
        }

        return plugins;
    }


}
