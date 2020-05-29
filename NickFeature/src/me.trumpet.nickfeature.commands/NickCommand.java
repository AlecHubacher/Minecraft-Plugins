package me.trumpet.nickfeature.commands;




import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trumpet.nickfeature.Main;

public class NickCommand implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public NickCommand(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("nick").setExecutor(this);
		plugin.getCommand("unNick").setExecutor(this);
		
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	

        /*if(cmd.getName().equalsIgnoreCase("nick")) {
            Player p = (Player) sender;
            int length = args.length;
          
            if (length == 2) {
             
                //If syntax is correct
             
            } else {
                p.sendMessage("Incorrect Syntax: /nick <name>");
            }
         
            return true;
         
        }
        */
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players may execute!");
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage("hello how are you");
		String origName=p.getName();
		if(cmd.getName().equalsIgnoreCase("nick"))
		{
			
			p.setDisplayName(args[0]);
			p.setPlayerListName(args[0]);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("unNick"))
		{
			p.setDisplayName(origName);
			p.setPlayerListName(origName);
			return true;
		}
		
		return false;
	}
}
