/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.annotationparser;

import com.jack.annotationparser.AnnotationFunctionBinder;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Aurelien
 */
public class TestAnnotation extends AnnotationFunctionBinder {
        
        @Override
        public Class<? extends Annotation> annotationType() {
            return AtTestAnnotation.class;
        }

        @Override
        public void run(AnnotatedElement element) {
            if(element.getClass().equals(Class.class)) {
                AnnotationTester.classTester = true;
            }
            
            else if(element.getClass().equals(Method.class)) {
                AnnotationTester.methodTester = true;
            }
            
            else if(element.getClass().equals(Field.class)) {
                AnnotationTester.fieldTester = true;
            }
        }
    }