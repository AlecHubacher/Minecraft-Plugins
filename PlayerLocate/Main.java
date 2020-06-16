package me.trumpet.playerlocate;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import me.trumpet.playerlocate.commands.PlayerLocateCommands;

public class Main extends JavaPlugin{
	
	public static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	
	public void onEnable()
	{
		
		instance=this;
		try {
			new PlayerLocateCommands(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
