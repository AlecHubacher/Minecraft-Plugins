package me.trumpet.deathswap;

import org.bukkit.plugin.java.JavaPlugin;

import me.trumpet.deathswap.commands.DeathSwapCommands;

public class Main extends JavaPlugin{
public static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	public void onEnable()
	{
		instance=this;
		new DeathSwapCommands(this);
	}

}
