package com.jack.plugins.manager;

import com.jack.annotationparser.AnnotationBinder;
import com.jack.annotationparser.AnnotationFunctionBinder;
import com.jack.annotationparser.AnnotationInterpreter;
import com.jack.annotationparser.AnnotationParser;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

/**
 * Plugin Loader class that is in charge of loading plugins from Jar files
 * @author Aurelien
 */
@Slf4j
@AllArgsConstructor
public class PluginLoader {

	@Getter private String path;
        private List<AnnotationFunctionBinder> functionBinders;
	
	public ArrayList<Plugin> load() {
		
            ArrayList<Class> classes = this.initializeLoader();
            ArrayList<Plugin> plugins = new ArrayList<Plugin>();

            classes.stream().forEach((Class object) -> {
                Class c = (Class) object;
                PluginDescription description = getDescription(c);
                
                if(description != null)
                {
                    parseAnnotations(c);
                    if(PluginBase.class.isAssignableFrom(c))
                    {
                        try {
                            PluginBase base = (PluginBase) c.newInstance();
                            plugins.add(new Plugin(base, description));
                        } catch (InstantiationException | IllegalAccessException e) {
                            log.warn("Could not instantiate class " + c);
                        }
                    }
                }
            });
            
            return plugins;
	}
	
	private ArrayList<Class> initializeLoader(){
            
            JarLoader loader = new JarLoader(path);
            Enumeration enumeration;
            try {
                enumeration = loader.parse();
            } catch (IOException ex) {
                log.error(path + " file failed to be loaded. Aborting...");
                return null;
            }
            URLClassLoader classLoader = loader.getClassLoader();
            ArrayList<Class> classes = new ArrayList<Class>();
            
            while(enumeration.hasMoreElements()){

                String tmp = enumeration.nextElement().toString();

                if(tmp.length() > 6 && tmp.substring(tmp.length()-6).compareTo(".class") == 0) {

                    tmp = tmp.substring(0,tmp.length()-6);
                    tmp = tmp.replaceAll("/",".");
                    try {
                        Class tmpClass = Class.forName(tmp ,true,classLoader);
                        if(tmpClass != null) {
                            classes.add(tmpClass);
                        }
                    } catch(ClassNotFoundException | java.lang.IncompatibleClassChangeError | java.lang.NoClassDefFoundError e) {
                        //log.warn("Class " + tmp + "could not be loaded !");
                    }
                }
            }
            return classes;
	}
	
        private void parseAnnotations(Class c) {
            AnnotationParser parser = new AnnotationParser(c);
            List<AnnotationBinder> binders = parser.parse();

            AnnotationInterpreter interpreter = new AnnotationInterpreter(binders, functionBinders);

            interpreter.run();
        }
        
        private PluginDescription getDescription(Class c) {
            Annotation descriptor = c.getAnnotation(PluginDescriptor.class);
            PluginDescriptorBinder descriptorBinder = new PluginDescriptorBinder();
            PluginDescription description = null;
            if(descriptor != null)
            {
                description = (PluginDescription) descriptorBinder.run(c, descriptor);
            }
            
            return description;
            
        }
	
}
