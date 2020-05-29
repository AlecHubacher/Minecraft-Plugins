package me.trumpet.mcsg.commands;


//import me.trumpet.mcsg.Stats;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import me.trumpet.mcsg.Main;

@SuppressWarnings("deprecation")
public class MCSGCommands implements CommandExecutor{
	static boolean preGame=false;
	
	public static  boolean getpreGame()
	{
		return preGame;
	}
	@SuppressWarnings("unused")
	private Main plugin;
	
	public MCSGCommands(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("game").setExecutor(this);
		plugin.getCommand("givePotion").setExecutor(this);
		plugin.getCommand("stats").setExecutor(this);
		
	}
	
	int seconds=0;
	double timeElapsedNum=0.0;
	double timeElapsed=1.0;
	static int minutes=1;
	static int timeTilStart=10;
	public static void setTime(int m)
	{
		minutes=m;
	}
	
	
	BossBar timer = Bukkit.createBossBar("§l"+minutes+":"+seconds, BarColor.BLUE, BarStyle.SOLID);
	//@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		World w =  Bukkit.getServer().getWorld("World");
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players may execute!");
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("givePotion"))
		{
			Potion pot = new Potion(PotionType.JUMP,2);
			ItemStack item = pot.toItemStack(1);
			p.getInventory().addItem(new ItemStack(item));
		}
		//sending stats to player
		p.sendMessage("1");
		if(cmd.getName().equalsIgnoreCase("stats"))
		{
			p.sendMessage("2");
			if(args.length>=1)
			{
				p.sendMessage("Incorrect number of arguments: /stats <player>");
			}
			else
			{
				for(int i=0;i<Main.playerIDs.size();++i)
				{
					if(p.getUniqueId().compareTo(Main.playerIDs.get(i))==0)
					{
						p.sendMessage(Main.statsPlayers.get(i).getStats(p));
					}
				}
				return true;
			}
			return false;
		}
		boolean isInt=false;
		label: try
		{
			if(args.length==0||args[0].equalsIgnoreCase("stop"))
			{
				break label;
			}
			Integer.parseInt(args[1]);
			isInt=true;
		} catch(NumberFormatException ex)
		{
			isInt=false;
		}
		if(cmd.getName().equalsIgnoreCase("game"))
		{
			if(args[0].equalsIgnoreCase("start")&& (args[0].equalsIgnoreCase("start"))&&(isInt))
			{
				if(args.length!=2)
				{
					p.sendMessage("Not enough arguments: /game start");
					return false;
				}
				//Add game played to players stats
				
			    for(int i=0;i<Main.playerIDs.size();++i)
				{
					if(p.getUniqueId().compareTo(Main.playerIDs.get(i))==0)
					{
						Main.statsPlayers.get(i).increaseGamesPlayed();
					}
				}
				
				

				preGame=true;
				minutes=Integer.parseInt(args[1]);
				timeElapsedNum=1.0/(minutes*60.0);
				timer.addPlayer(p);
				
				//teleport players to podium
				ArrayList<Location> spawnLocations = new ArrayList<Location>();
				spawnLocations.add(new Location(w,44,178,-302));
				spawnLocations.add(new Location(w,52,178,-310));
				spawnLocations.add(new Location(w,44,178,-318));
				spawnLocations.add(new Location(w,36,178,-310));
				int max=3;
				for(Player player : Bukkit.getOnlinePlayers())
				{
					Random rand = new Random();
					int randomNum=rand.nextInt(max-0);
					for(Player pl : Bukkit.getOnlinePlayers())
					{
						//Remove Location if player is standing on podium
						if(spawnLocations.get(randomNum)==pl.getLocation().getBlock().getLocation())
						{
							spawnLocations.remove(randomNum);
							max--;
							randomNum=rand.nextInt(max-0);
						}
					}
					//teleport player to podium
					player.teleport(spawnLocations.get(randomNum));
				}
				//PreGame
				new BukkitRunnable()
				{
					@Override
					public void run() {
						if(timeTilStart==0)
						{
							preGame=false;
							timeTilStart=10;
							Bukkit.broadcastMessage("§3The games have begun!");
							for(Player player : Bukkit.getOnlinePlayers())
							{
								player.setWalkSpeed(0.2f);
							}
							this.cancel();
						}
						else
						{
							Bukkit.broadcastMessage("§7[§e"+timeTilStart+"§7] §cseconds until the games begin!");
						}
						timeTilStart--;
						
					}
					
				}.runTaskTimer((Plugin) Main.getInstance(), 0, 20);
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
							timer.setTitle(minutes+":"+seconds);
							minutes= minutes - 1;
							seconds=59;
						}
						timer.setTitle(minutes+":"+seconds);
						seconds = seconds - 1;
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
				for(Player player : Bukkit.getOnlinePlayers())
				{
					player.setWalkSpeed(.2f);
				}
			}
			
			return false;
			
		}
		else
		{
			p.sendMessage("Incorrect Syntax: /game start <Integer>(minutes)");
		}
		return false;
	}
}
