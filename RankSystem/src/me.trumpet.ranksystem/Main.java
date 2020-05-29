package me.trumpet.ranksystem;

import org.bukkit.plugin.java.JavaPlugin;

import me.trumpet.ranksystem.commands.*;
public class Main extends JavaPlugin{
	
	public void onEnable()
	{
		new RankSystemCommands(this);
	}

}
