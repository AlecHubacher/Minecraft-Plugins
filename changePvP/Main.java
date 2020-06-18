package me.trumpet.changepvp;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{

    @Override
    public void onEnable()
    {
        getConfig().addDefault( Attribute.GENERIC_ATTACK_SPEED.name(), 1000);
        getConfig().addDefault(Attribute.GENERIC_ATTACK_DAMAGE.name(), 2);
        getConfig().options().copyDefaults( true );
        saveConfig();

        getServer().getPluginManager().registerEvents( this, this );
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        for ( String key : getConfig().getKeys( false ) )
        {
            AttributeInstance instance = event.getPlayer().getAttribute( Attribute.valueOf( key ) );
            if ( instance != null )
            {
                instance.setBaseValue( getConfig().getDouble( key, instance.getBaseValue() ) );
            }
        }
    }
}