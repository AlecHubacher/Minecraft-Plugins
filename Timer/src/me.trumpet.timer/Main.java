package me.trumpet.timer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.trumpet.timer.commands.TimerCommand;

public class Main extends JavaPlugin{
	public static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	public void onEnable()
	{
		instance=this;
		new TimerCommand(this);
	}

	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
	}
}
