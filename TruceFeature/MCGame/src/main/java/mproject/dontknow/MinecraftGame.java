package mproject.dontknow;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftGame extends JavaPlugin implements Listener {

    public static MinecraftGame instance;

    public static MinecraftGame getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable() {

        new TruceFeature(this, "Mr_Bari_99");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
