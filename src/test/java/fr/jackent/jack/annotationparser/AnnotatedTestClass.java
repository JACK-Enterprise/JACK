/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import lombok.Getter;

/**
 *
 * @author Aurelien
 */
@AtTestAnnotation
public class AnnotatedTestClass {
        
        @AtTestAnnotation
        @Getter private boolean tester;
        
        public AnnotatedTestClass() {
            this.tester = false;
            
        }
        
        @AtTestAnnotation
        public void f(){
            
        }
    }
