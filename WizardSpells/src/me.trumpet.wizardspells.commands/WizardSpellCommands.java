package me.trumpet.wizardspells.commands;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
//import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import me.trumpet.wizardspells.Main;
//spell alohomaora opens locked doors
//spell wingardium leviosa makes something levitate
//bombarda makes an explosion
//there is a spell that sets stuff on fire
public class WizardSpellCommands implements CommandExecutor, Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public WizardSpellCommands(Main plugin)
	{
		this.plugin=plugin;  
		plugin.getCommand("expelliarmus").setExecutor(this);
		plugin.getCommand("spellstop").setExecutor(this);
		plugin.getCommand("avada-kedavra").setExecutor(this);
		plugin.getCommand("bombarda").setExecutor(this);
		plugin.getCommand("wingardium-leviosa").setExecutor(this);
		plugin.getCommand("incendio").setExecutor(this);
		plugin.getCommand("accio").setExecutor(this);
	}
	//private int k=0;
	
	Player player;
	private int counter;
	private HashMap<Player, Location> playerLocs = new HashMap<Player, Location>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players can execute this command!");
			return false;
		}
		World w =Bukkit.getServer().getWorld("World");
		player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("test2"))
		{
			
		}
		//World w = Bukkit.getServer().getWorld("World");
		if(cmd.getName().equalsIgnoreCase("expelliarmus"))
		{
			DustOptions dustOptions = new DustOptions(Color.fromRGB(255, 0, 0), 1);
			final Location start=player.getEyeLocation();
			final Vector increase=start.getDirection();
			//playerLocs.put(p, new Location(w,70,76,-275));
			//Location target = new Location(w,70,76,-275);
			new BukkitRunnable()
			{
				public void run() {
			        if (counter == 100) {
			            cancel();
			            counter=0;
			        } else {
			            Location point = start.add(increase);
			            w.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            player.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            counter++;
			            
			            for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
			            	if(player.getUniqueId().compareTo(p.getUniqueId())==0)
			            	{
			            		continue;
			            	}
							playerLocs.put(p, p.getLocation());
							if((Math.abs(playerLocs.get(p).getX()-point.getX())<2)&&
							(Math.abs(playerLocs.get(p).getY()-point.getY())<2)&&
							(Math.abs(playerLocs.get(p).getZ()-point.getZ())<2))
							{
								cancel();
								castDisarmSpell(p);
							}
						}
			        }
			    }
			}.runTaskTimer((Plugin)Main.getInstance(), 0, 0);
		}
		else if(cmd.getName().equalsIgnoreCase("SpellStop"))
		{
			Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
			counter=0;
		}
		else if(cmd.getName().equalsIgnoreCase("avada-kedavra"))
		{
			DustOptions dustOptions = new DustOptions(Color.fromRGB(0, 255, 34), 1);
			final Location start=player.getEyeLocation();
			final Vector increase=start.getDirection();
			//playerLocs.put(p, new Location(w,70,76,-275));
			//Location target = new Location(w,70,76,-275);
			new BukkitRunnable()
			{
				public void run() {
			        if (counter == 100) {
			            cancel();
			            counter=0;
			        } else {
			            Location point = start.add(increase);
			            w.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            player.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            counter++;
			            
			            for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
			            	if(player.getUniqueId().compareTo(p.getUniqueId())==0)
			            	{
			            		continue;
			            	}
							playerLocs.put(p, p.getLocation());
							if((Math.abs(playerLocs.get(p).getX()-point.getX())<2)&&
							(Math.abs(playerLocs.get(p).getY()-point.getY())<2)&&
							(Math.abs(playerLocs.get(p).getZ()-point.getZ())<2))
							{
								cancel();
								killingCurse(p);
								
							}
						}
			        }
			    }
			}.runTaskTimer((Plugin)Main.getInstance(), 0, 0);
		}
		else if(cmd.getName().equalsIgnoreCase("bombarda"))
		{
			DustOptions dustOptions = new DustOptions(Color.fromRGB(0, 0, 0), 1);
			final Location start=player.getEyeLocation();
			final Vector increase=start.getDirection();
			BlockIterator spellItr = new BlockIterator(start, 0, 100);
			//playerLocs.put(p, new Location(w,70,76,-275));
			//Location target = new Location(w,70,76,-275);
			new BukkitRunnable()
			{
				public void run() {
			        if (counter == 100) {
			            cancel();
			            counter=0;
			        } else {
			            Location point = start.add(increase);
			            w.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            player.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            counter++;
			            if(!(spellItr.next().getType().isAir()))
			            {
			            	cancel();
			            	w.createExplosion(spellItr.next().getLocation(), 4);
			            }
			            for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
			            	if(player.getUniqueId().compareTo(p.getUniqueId())==0)
			            	{
			            		continue;
			            	}
							playerLocs.put(p, p.getLocation());
							if((Math.abs(playerLocs.get(p).getX()-point.getX())<2)&&
							(Math.abs(playerLocs.get(p).getY()-point.getY())<2)&&
							(Math.abs(playerLocs.get(p).getZ()-point.getZ())<2))
							{
								cancel();
								makeExplosion(w, point);
								
							}
						}
			        }
			    }
			}.runTaskTimer((Plugin)Main.getInstance(), 0, 0);
		}
		else if(cmd.getName().equalsIgnoreCase("wingardium-leviosa"))
		{
			DustOptions dustOptions = new DustOptions(Color.fromRGB(255, 255, 255), 1);
			final Location start=player.getEyeLocation();
			final Vector increase=start.getDirection();
			//playerLocs.put(p, new Location(w,70,76,-275));
			//Location target = new Location(w,70,76,-275);
			new BukkitRunnable()
			{
				public void run() {
			        if (counter == 100) {
			            cancel();
			            counter=0;
			        } else {
			            Location point = start.add(increase);
			            w.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            player.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            counter++;
			            
			            for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
			            	if(player.getUniqueId().compareTo(p.getUniqueId())==0)
			            	{
			            		continue;
			            	}
							playerLocs.put(p, p.getLocation());
							if((Math.abs(playerLocs.get(p).getX()-point.getX())<2)&&
							(Math.abs(playerLocs.get(p).getY()-point.getY())<2)&&
							(Math.abs(playerLocs.get(p).getZ()-point.getZ())<2))
							{
								cancel();
								makeLevitate(p);
								
							}
						}
			        }
			    }
			}.runTaskTimer((Plugin)Main.getInstance(), 0, 0);
		}
		else if(cmd.getName().equalsIgnoreCase("incendio"))
		{
			DustOptions dustOptions = new DustOptions(Color.fromRGB(255, 162, 0), 1);
			final Location start=player.getEyeLocation();
			final Vector increase=start.getDirection();
			//playerLocs.put(p, new Location(w,70,76,-275));
			//Location target = new Location(w,70,76,-275);
			new BukkitRunnable()
			{
				public void run() {
			        if (counter == 100) {
			            cancel();
			            counter=0;
			        } else {
			            Location point = start.add(increase);
			            w.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            player.spawnParticle(Particle.REDSTONE,point, 1, dustOptions);
			            counter++;
			            
			            for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
			            	if(player.getUniqueId().compareTo(p.getUniqueId())==0)
			            	{
			            		continue;
			            	}
							playerLocs.put(p, p.getLocation());
							if((Math.abs(playerLocs.get(p).getX()-point.getX())<2)&&
							(Math.abs(playerLocs.get(p).getY()-point.getY())<2)&&
							(Math.abs(playerLocs.get(p).getZ()-point.getZ())<2))
							{
								cancel();
								makeFire(p);
								
							}
						}
			        }
			    }
			}.runTaskTimer((Plugin)Main.getInstance(), 0, 0);
		}
		else if(cmd.getName().equalsIgnoreCase("accio"))
		{
			String playerName = player.getName();
			Bukkit.dispatchCommand(player, "give "+playerName+" "+args[0]+ " 1");
			
		}
		
			
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void castDisarmSpell(Player p)
	{
		Random rand = new Random();
		int randomNum=rand.nextInt(4-0);
		World w =Bukkit.getServer().getWorld("World");
		Location dropItemLocation = new Location(w,p.getLocation().getX()+3+randomNum,p.getLocation().getY(),p.getLocation().getZ()+2+randomNum);
		try
		{
			w.dropItem(dropItemLocation, new ItemStack(p.getItemInHand().getType(),1));//maybe do new itemstack(getitem, 1)
			p.getInventory().remove(p.getItemInHand());
		} catch (IllegalArgumentException e)
		{
			
		}
	}
	
	private void killingCurse(Player p)
	{
		p.setHealth(0.0);
	}
	
	private void makeExplosion(World w, Location loc)
	{
		w.createExplosion(loc, 4);
	}
	
	private void makeLevitate(Player p)
	{
		p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 2));
	}
	
	private void makeFire(Player p)
	{
		p.setFireTicks(40);
	}
	
}
