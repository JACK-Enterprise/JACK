/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.annotationparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * Factory that parses a ClassTree object by 
 * 
 * @see ClassTree
 * @author Aurelien
 */
@AllArgsConstructor
public class ClassTreeFactory {
    
    private Class target;
    
    public ClassTree parse() {
        List<Class> parentClasses = getParentClasses();
        ClassTreeFactory parentFactory;
        ClassTree parentTree;
        List<ClassTree> parentTreeList = new ArrayList<ClassTree>();
        
        if(target != null) {

            for(final Class parentClass : parentClasses) {
                parentFactory = new ClassTreeFactory(parentClass);
                parentTree = parentFactory.parse();
                if(parentTree != null) {
                    parentTreeList.add(parentTree);
                }
            }
            
            return new ClassTree(parentTreeList, this.target);
        }
        
        return null;
    }
    
    public List<Class> getParentClasses() {
        List<Class> parentClasses = new ArrayList<Class>();
        
        if(this.target != null) {
            Class superClass = this.target.getSuperclass();
            List <Class> interfaces;
            if(superClass != null) {
                parentClasses.add(superClass);
            }
            
            interfaces = getParentInterfaces();
            
            if(interfaces != null) {
                parentClasses.addAll(interfaces);
            }
        }
        
        return parentClasses;
    }
    
    private List<Class> getParentInterfaces() {
        Class[] interfaces = null;
        if(this.target != null) {
            interfaces = this.target.getInterfaces();
        }
        
        return interfaces != null ? Arrays.asList(interfaces) : null;
    }
}
