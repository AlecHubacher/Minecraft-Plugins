package me.trumpet.ranksystem.commands;



import org.bukkit.ChatColor;
//import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import java.lang.Math;
//import java.lang.Character;
//import org.bukkit.entity.Weather;

import me.trumpet.ranksystem.Main;





public class RankSystemCommands implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public RankSystemCommands(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("rank").setExecutor(this);
		
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players may execute!");
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage("It workssssssasdf!");
		if(args[0].equalsIgnoreCase("mod"))
		{
			p.sendMessage("we got here boysd");
			p.setDisplayName(ChatColor.RED+p.getName());
			p.setPlayerListName(ChatColor.RED+p.getName());
			return true;
		}
		else if(args[0].equalsIgnoreCase("admin"))
		{
			p.setDisplayName(ChatColor.DARK_RED+p.getName());
			p.setPlayerListName(ChatColor.DARK_RED+p.getName());
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("rank ninja"))
		{
			//use player canSee function so he becomes invisible 
		}
		
		
		
		
		return false;
	}
}
