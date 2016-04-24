/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.junit.Test;

/**
 *
 * @author Aurelien
 */
public class AnnotationInterpreterIT {
    
    public class TestAnnotation extends AnnotationFunctionBinder {

        @Override
        public Class<? extends Annotation> annotationType() {
            return AtTestAnnotation.class;
        }

        @Override
        public void run(AnnotatedElement element) {
            System.out.println("OK");
        }
    }
    
    @Test
    public void shouldParseAnnotation() {
        List<AnnotationFunctionBinder> functionBinders = new ArrayList<AnnotationFunctionBinder>();
        AnnotationParser parser = new AnnotationParser(AnnotatedTestClass.class);
        List<AnnotationBinder> binders = parser.parse();
        functionBinders.add(new TestAnnotation());
        
        AnnotationInterpreter interpreter = new AnnotationInterpreter(binders, functionBinders);
        
        interpreter.run();
    }
}
