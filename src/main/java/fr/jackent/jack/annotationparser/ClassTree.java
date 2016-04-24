/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Entity class that stores dependency tree of a given class
 * @author Aurelien
 */
@AllArgsConstructor
public class ClassTree {
    @Getter private List<ClassTree> parents;
    @Getter private Class node;
}
