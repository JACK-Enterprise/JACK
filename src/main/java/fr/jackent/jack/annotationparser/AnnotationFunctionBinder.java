/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom Annotation class that is runnable by AnnotationInterpreter class
 * 
 * @see AnnotationInterpreter
 * @author Aurelien
 */
@AllArgsConstructor
public abstract class AnnotationFunctionBinder implements Annotation {
    
    /**
     * Running function that is called on found annotations
     * 
     * @param element Reflected element that uses the given annotation
     */
    public void run(AnnotatedElement element) {
        
    }
    
    /**
     * 
     * @return annotation class 
     */
    Class<? extends Annotation> getType() {
        return this != null ? this.annotationType() : null;
    }
}
