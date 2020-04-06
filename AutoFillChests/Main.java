package me.trumpet.autofillchests;

import org.bukkit.plugin.java.JavaPlugin
import me.trumpet.autofillchests.commands.*;

public class Main extends JavaPlugin{
	
	public void onEnable()
	{
		new AutoFillChestsCommands(this);
	}

}
