package me.trumpet.timer.commands;

import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.trumpet.timer.Main;

public class TimerCommand implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public TimerCommand(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("timer").setExecutor(this);
		
	}
	
	int seconds=0;
	double timeElapsedNum=0.0;
	double timeElapsed=1.0;
	static int minutes=1;
	
	public static void setTime(int m)
	{
		minutes=m;
	}
	
	
	BossBar timer = Bukkit.createBossBar("§l"+minutes+":"+seconds, BarColor.BLUE, BarStyle.SOLID);
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players may execute!");
			return true;
		}
		Player p = (Player) sender;
		if(args[0].equalsIgnoreCase("start"))
		{
			if(args.length!=2)
			{
				p.sendMessage("Not enough arguments: /timer start <minutes>");
				return false;
			}
			minutes=Integer.parseInt(args[1]);
			timeElapsedNum=1.0/(minutes*60.0);
			timer.addPlayer(p);
			new BukkitRunnable()
			{

				@Override
				public void run() {
					if(seconds==0)
					{
						if(minutes==0)
						{
							Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
							timer.removeAll();
							timer.setProgress(1);
							timeElapsed=1;
							return;
						}
						minutes= minutes - 1;
						seconds=59;
						timer.setTitle(minutes+":"+seconds);
					}
					seconds = seconds - 1;
					timer.setTitle(minutes+":"+seconds);
					//timeElapsed-=0.00083333; this is for 20 minute timer
					timeElapsed-=timeElapsedNum;
					timer.setProgress(timeElapsed);
					
					
				}
				
			}.runTaskTimer((Plugin) Main.getInstance() , 0, 20);
			
		}
		else if(args[0].equalsIgnoreCase("stop"))
		{
			Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
			timer.removePlayer(p);
			timer.setProgress(1);
			minutes=1;
			seconds=0;
			timeElapsed=1;
			
		}
		
		
		
		
		
		return false;
	}
}