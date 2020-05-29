package me.trumpet.wizardspells;

import org.bukkit.plugin.java.JavaPlugin;

import me.trumpet.wizardspells.commands.WizardSpellCommands;

public class Main extends JavaPlugin{
	public static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	public void onEnable()
	{
		instance=this;
		new WizardSpellCommands(this);
	}
}
