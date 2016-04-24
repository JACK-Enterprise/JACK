/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.pluginmanager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aurelien
 */
public class PluginDescriptor {

    private String pluginId;
    private String pluginDescription;
    private String pluginClass;
    private Version version;
    private Expression requires;
    private String provider;
    private List<PluginDependency> dependencies;

    public PluginDescriptor() {
    	requires = gte("0.0.0"); // Any
        dependencies = new ArrayList<>();
    }

    /**
     * Returns the unique identifier of this plugin.
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * Returns the description of this plugin.
     */
    public String getPluginDescription() {
        return pluginDescription;
    }

    /**
     * Returns the name of the class that implements Plugin interface.
     */
    public String getPluginClass() {
        return pluginClass;
    }

    /**
     * Returns the version of this plugin.
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Returns the requires of this plugin.
     */
    public Expression getRequires() {
        return requires;
    }

    /**
     * Returns the provider name of this plugin.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Returns all dependencies declared by this plugin.
     * Returns an empty array if this plugin does not declare any require.
     */
    public List<PluginDependency> getDependencies() {
        return dependencies;
    }

    @Override
	public String toString() {
		return "PluginDescriptor [pluginId=" + pluginId + ", pluginClass="
				+ pluginClass + ", version=" + version + ", provider="
				+ provider + ", dependencies=" + dependencies
				+ "]";
	}

	void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

	void setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
    }

    void setPluginClass(String pluginClassName) {
        this.pluginClass = pluginClassName;
    }

    void setPluginVersion(Version version) {
        this.version = version;
    }

    void setProvider(String provider) {
        this.provider = provider;
    }

    void setRequires(String requires) {
        Parser<Expression> parser = ExpressionParser.newInstance();
        this.requires = parser.parse(requires);
    }

    void setRequires(Expression requires) {
        this.requires = requires;
    }

    void setDependencies(String dependencies) {
    	if (dependencies != null) {
    		dependencies = dependencies.trim();
    		if (dependencies.isEmpty()) {
    			this.dependencies = Collections.emptyList();
    		} else {
	    		this.dependencies = new ArrayList<>();
	    		String[] tokens = dependencies.split(",");
	    		for (String dependency : tokens) {
	    			dependency = dependency.trim();
	    			if (!dependency.isEmpty()) {
	    				this.dependencies.add(new PluginDependency(dependency));
	    			}
	    		}
	    		if (this.dependencies.isEmpty()) {
	    			this.dependencies = Collections.emptyList();
	    		}
    		}
    	} else {
    		this.dependencies = Collections.emptyList();
    	}
    }

}
