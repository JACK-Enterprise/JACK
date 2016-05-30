/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import com.jack.annotationparser.AnnotationFunctionBinder;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

/**
 *
 * @author Aurelien
 */
public class PluginDescriptorBinder extends AnnotationFunctionBinder {
        
    @Override
    public Class<? extends Annotation> annotationType() {
        return PluginDescriptor.class;
    }

    @Override
    public Object run(AnnotatedElement element, Annotation annotation) {
        PluginDescription descriptor = null;
        
        if(annotation != null)
        {
            PluginDescriptor parsedAnno = (PluginDescriptor)annotation;
            descriptor = new PluginDescription(parsedAnno.author(),
                                                parsedAnno.version(),
                                                parsedAnno.name(),
                                                parsedAnno.description());
        }
        
        return descriptor;
    }
}