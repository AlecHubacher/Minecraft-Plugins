package me.trumpet.nickfeature;

import org.bukkit.plugin.java.JavaPlugin;
import me.trumpet.nickfeature.commands.NickCommand;
//import me.trumpet.randomweather.commands.RandomWeatherCommand;

public class Main extends JavaPlugin {
	public void onEnable()
	{
		System.out.println("Nick Plugin works!");
		new NickCommand(this);
	}
}
