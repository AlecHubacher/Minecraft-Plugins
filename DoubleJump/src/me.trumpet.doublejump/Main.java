package me.trumpet.doublejump;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
//import org.bukkit.Statistic;
//import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
//import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.util.Vector;

//import me.trumpet.doublejump.events.DoubleJumpEvent;

public class Main extends JavaPlugin implements Listener{

	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this,this);
	}
	
	@EventHandler
	public void doubleJump(PlayerToggleFlightEvent e)
	{
		Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) { return; }
        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(1)); 
		
	}
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE && p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR && !p.isFlying()) {
            p.setAllowFlight(true);
        }
    }
}
