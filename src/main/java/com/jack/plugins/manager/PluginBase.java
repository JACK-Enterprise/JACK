package com.jack.plugins.manager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Plugin abstract class that defines what is a plugin
 * 
 * @author Aurelien
 */
@NoArgsConstructor
@Slf4j
public abstract class PluginBase {
    
    public Object run(Object ... args) {
        log.error("\"Run\" method is not yet implemented for plugin");
        
        return null;
    }
}
