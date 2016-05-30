package com.jack.plugins.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
@Slf4j
public class Plugin {
    @Getter private PluginBase plugin;
    @Getter private PluginDescription description;
    
    public Object run(Object ... args) {
        String name = description != null ? description.getName() : "null";
        if(plugin != null)
        {
            log.trace("Running \"" + name + "\" plugin");
            return plugin.run(args);
        }
        
        log.warn("Plugin \"" + name + "\" is not correctly set");
        return null;
    }
}
