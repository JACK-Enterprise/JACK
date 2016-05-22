/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.annotationparser;

import com.jack.annotationparser.AnnotationInterpreter;
import com.jack.annotationparser.AnnotationParser;
import com.jack.annotationparser.AnnotationBinder;
import com.jack.annotationparser.AnnotationFunctionBinder;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Aurelien
 */
public class AnnotationInterpreterIT {
    
    @Test
    public void shouldParseAnnotation() {
        List<AnnotationFunctionBinder> functionBinders = new ArrayList<AnnotationFunctionBinder>();
        AnnotationParser parser = new AnnotationParser(AnnotatedTestClass.class);
        List<AnnotationBinder> binders = parser.parse();
        functionBinders.add(new TestAnnotation());
        
        AnnotationInterpreter interpreter = new AnnotationInterpreter(binders, functionBinders);
        
        interpreter.run();
        
        assertTrue(AnnotationTester.classTester);
        assertTrue(AnnotationTester.methodTester);
        assertTrue(AnnotationTester.fieldTester);
        
    }
}
