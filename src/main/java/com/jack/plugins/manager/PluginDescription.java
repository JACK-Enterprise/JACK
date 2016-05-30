/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.plugins.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
public class PluginDescription {
    @Getter private String author;
    @Getter private String version;
    @Getter private String name;
    @Getter private String description;
}
