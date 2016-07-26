package com.jack.plugins.manager;

import lombok.NoArgsConstructor;

/**
 * Plugin abstract class that defines what is a plugin
 * 
 * @author Aurelien
 */
@NoArgsConstructor
public abstract class PluginBase {
    
    public Object run(Object ... args) {
        System.out.println("\"Run\" method is not yet implemented for plugin");
        
        return null;
    }
}
