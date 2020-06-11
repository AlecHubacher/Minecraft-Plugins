package mproject.dontknow;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dontknow extends JavaPlugin implements Listener {

    public static Dontknow instance;

    public static Dontknow getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable() {

        new TruceFeature(this);
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
