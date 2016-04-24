package fr.jackent.jack.pluginManagement;

/**
 * Created by Aurelien on 02/21/16.
 */

public class Plugin {
    public void plug()
	{
		System.out.println("plugged");
	}
	
    public void unplug()
	{
		System.out.println("unplugged");
	}
	
    public String getName()
	{
		System.out.println("This is my plugin");
		
		return "Plugin";
	}
}

