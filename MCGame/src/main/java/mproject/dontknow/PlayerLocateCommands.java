package mproject.dontknow;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class PlayerLocateCommands implements CommandExecutor, Listener {
    @SuppressWarnings("unused")
    private Dontknow plugin;


    public PlayerLocateCommands(Dontknow plugin) throws IOException
    {
        this.plugin=plugin;
        plugin.getCommand("findPlayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("Only players can execute this command!");
            return false;
        }

        if(cmd.getName().equalsIgnoreCase("findPlayer"))
        {
            for(Player p : Bukkit.getServer().getOnlinePlayers())
            {
                p.getInventory().addItem(new ItemStack(Material.COMPASS,1));
            }

            new BukkitRunnable()
            {

                @Override
                public void run() {
                    for(Player p : Bukkit.getServer().getOnlinePlayers())
                    {
                        p.setCompassTarget(TruceFeature.soloPlayer.getLocation());
                    }

                }

            }.runTaskTimer((Plugin) Dontknow.getInstance(),0,5);

        }

        return true;


    }

}