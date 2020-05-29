package me.trumpet.mcsg;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
//import org.bukkit.GameMode;
//import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import me.trumpet.mcsg.commands.MCSGCommands;


public class Main extends JavaPlugin implements Listener{
	public static ArrayList<Stats> statsPlayers = new ArrayList<Stats>();
	public static ArrayList<UUID> playerIDs = new ArrayList<UUID>();
	public static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	public void onEnable()
	{
		instance=this;
		new MCSGCommands(this);
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		//Create lightning effect when player dies at their death location
		World w =  Bukkit.getServer().getWorld("world");
		w.strikeLightningEffect(e.getEntity().getPlayer().getLocation());
		e.getEntity().getPlayer().getInventory().clear();
		e.getEntity().getPlayer().getInventory().setArmorContents(new ItemStack[4]);
		
		//Add death to players stats
		for(int i=0;i<Main.playerIDs.size();++i)
		{
			if(e.getEntity().getPlayer().getUniqueId().compareTo(playerIDs.get(i))==0)
			{
				statsPlayers.get(i).increaseDeaths();
			}
		}
		
		//Get killer of player and increase killers kill amount
		if(e.getEntity().getKiller() instanceof Player)
		{
			for(int i=0;i<Main.playerIDs.size();++i)
			{
				if(e.getEntity().getKiller().getUniqueId().compareTo(playerIDs.get(i))==0)
				{
					statsPlayers.get(i).increasePlayersKilled();
				}
			}
		}
		
		
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(!(playerIDs.contains(e.getPlayer().getUniqueId())))
		{
			playerIDs.add(e.getPlayer().getUniqueId());
			statsPlayers.add(new Stats());
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		if(!(playerIDs.contains(e.getPlayer().getUniqueId())))
		{
			playerIDs.add(e.getPlayer().getUniqueId());
			statsPlayers.add(new Stats());
		}
	}
	/*@EventHandler
	public void cancelRespawn(EntityDamageEvent e)
	{
		Player p = (Player) e;
		Location pLoc = p.getLocation();
		if(e.getDamage()>=p.getHealth())
		{
			e.setCancelled(true);
			p.setHealth(20);
			p.teleport(pLoc);
			p.setGameMode(GameMode.SPECTATOR);
		}
	}*/
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks((Plugin)Main.getInstance());
	}
	
	Vector sprinting = new Vector();
	@EventHandler
	public void onMoveDuringPreGame(PlayerMoveEvent e)
	{
		//e.setCancelled(MCSGCommands.getpreGame());
		if(MCSGCommands.getpreGame())
		{
			Location newToLocation = e.getFrom().setDirection(e.getTo().getDirection());
			e.setTo(newToLocation);
		}
		//e.setCancelled(MCSGCommands.getpreGame());
		
		
		
	}
	
}
