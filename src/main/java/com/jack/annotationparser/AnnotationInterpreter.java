/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * Annotation interpreter that runs annotated code on given class(es)
 * 
 * @author Aurelien
 */
@AllArgsConstructor
public class AnnotationInterpreter {
    private List<AnnotationBinder> objectBinders;
    private List<AnnotationFunctionBinder> functionBinders;
    
    /**
     * Adds an AnnotationBinder object to current interpreter context
     * 
     * @see AnnotationBinder
     * @param binder 
     */
    public void addAnnotationBinder (AnnotationBinder binder) {
        objectBinders.add(binder);
    }
    
    /**
     * Adds an AnnotationFunctionBinder object to current interpreter context
     * 
     * @see AnnotationFunctionBinder
     * @param binder 
     */
    public void addAnnotationFunctionBinder (AnnotationFunctionBinder binder) {
        functionBinders.add(binder);
    }
    
    /**
     * Runs the interpreter with its current context
     */
    public void run() {
        if(objectBinders != null && functionBinders != null)
        {   
            for (AnnotationBinder objectBinder : objectBinders) {
                parseAndRunElement(objectBinder);
            }
        }
    }
    
    private void parseAndRunElement(AnnotationBinder objectBinder) {
        if(objectBinder != null) {
            List<Annotation> annotations = objectBinder.getAnnotations();
            AnnotatedElement targetElement = objectBinder.getElement();
            if(annotations != null) {
                for(Annotation annotation : annotations) {
                    findMatchingAnnotationAndRun(targetElement, annotation);
                }
            }
        }
    }
    
    private void findMatchingAnnotationAndRun(AnnotatedElement targetElement, 
                                                Annotation annotation) {
        if(annotation != null) {
            Class annotationType = annotation.annotationType();
            for(AnnotationFunctionBinder functionBinder : functionBinders) {
                runSingle(functionBinder, annotationType, targetElement, annotation);
            }
        }
    }
    
    public Object runSingle(AnnotationFunctionBinder functionBinder, 
                                        Class annotationType, 
                                        AnnotatedElement targetElement,
                                        Annotation annotation) {
        Object output = null;
        if(functionBinder != null) {
            Class functionAnnotationType = functionBinder.getType();

            if(annotationType != null && annotationType.equals(functionAnnotationType))
            {
                output = functionBinder.run(targetElement, annotation);
            }
        }
        
        return output;
    }
}
