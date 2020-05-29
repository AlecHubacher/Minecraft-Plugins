package me.trumpet.deathswap.commands;

import me.trumpet.deathswap.Main;

//import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathSwapCommands implements CommandExecutor{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public DeathSwapCommands(Main plugin)
	{
		this.plugin=plugin;
		plugin.getCommand("deathSwap").setExecutor(this);
		plugin.getCommand("deathstop").setExecutor(this);
	}
	private int k=0;
	private int timeLeft=300;
	private int i=0;//might need to move this outside cuz it might reset each iteration
	//private int count=0;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players can execute this command!");
			return false;
		}
		if(cmd.getName().equalsIgnoreCase("deathswap"))
		{
			Bukkit.broadcastMessage("DeathSwap has started!");
			Location[] playersLoc = new Location[ Bukkit.getServer().getOnlinePlayers().size()];
			Player[] players = new Player[ Bukkit.getServer().getOnlinePlayers().size()];
			//Player p = (Player) sender;
			new BukkitRunnable()
			{
				@Override
				public void run() {
					
					timeLeft--;
					if(timeLeft<=30)
					{
						Bukkit.broadcastMessage("§cTime left until Swap: "+timeLeft);
					}
					if(timeLeft==0)
					{
						for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
							playersLoc[k]=p.getLocation();
							players[k]=p;
							k++;
						}
						k=0;
						Location temp = playersLoc[0];
						for(int j=0;j<playersLoc.length-1;++j)
						{
							if(playersLoc.length==1)
							{
								break;
							}
							playersLoc[i]=playersLoc[i+1];
							i++;
						}
						playersLoc[i]=temp;
						i=0;
						for(int j=0;j<players.length;++j)
						{
							players[j].teleport(playersLoc[j]);
							
						}
						timeLeft=300;
					}
					
					
					//count++;
				}
				
			}.runTaskTimer((Plugin) Main.getInstance() , 0, 20);
		}
		else if(cmd.getName().equalsIgnoreCase("deathstop"))
		{
			Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
			timeLeft=300;
		}
		
		
		return false;
	}

}
