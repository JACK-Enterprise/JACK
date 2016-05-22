package com.jack.plugins.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
public class Plugin {
    @Getter private PluginBase plugin;
    @Getter private PluginDescription description;
}
