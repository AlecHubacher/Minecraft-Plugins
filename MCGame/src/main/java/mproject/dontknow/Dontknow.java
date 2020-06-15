package mproject.dontknow;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Dontknow extends JavaPlugin implements Listener {

    public static Dontknow instance;

    public static Dontknow getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        new TruceFeature(this);
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    boolean isSwap = false;
    int count = 0;
    int track = 0;
    int swapTrack = 0;
    int tpTrack = 0;
    Player dontSwapMultiple;

    //add a cooldown of some sort
    @EventHandler
    public void truceTeleport(PlayerInteractEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        if(isSwap && (!dontSwapMultiple.equals(p)) && (item.equals(TruceFeature.swapPlacesReceiver) || item.equals(TruceFeature.swapPlacesSender)) && TruceFeature.truceMap.get(p).contains(dontSwapMultiple) && !(e.getAction() == Action.LEFT_CLICK_AIR) && !(e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            p.sendMessage("inside else if checking for other person clicking swap");
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for(int i = 0; i < TruceFeature.truceMap.get(p).size(); i++)
            {
                if(TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName))
                {
                    TruceFeature.cooldownMap.get(dontSwapMultiple).set(i + i + 1, false);
                    swapTrack = 0;
                    int inum = i;
                    Location truceLocation = TruceFeature.truceMap.get(p).get(i).getLocation();
                    Location pLocation = p.getLocation();
                    p.teleport(truceLocation);
                    TruceFeature.truceMap.get(p).get(i).getPlayer().teleport(pLocation);
                    isSwap = false;

                    new BukkitRunnable()
                    {

                        @Override
                        public void run() {
                            swapTrack++;
                            if(swapTrack == 2)
                            {
                                swapTrack = 0;
                                TruceFeature.cooldownMap.get(dontSwapMultiple).set(inum + inum + 1, true);
                                Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                            }
                        }
                    }.runTaskTimer(getInstance(), 0, 40);
                }
            }
        }
        else if(isSwap && p == dontSwapMultiple)
        {
            p.sendMessage("You must wait for a response");
        }
        else if((item.equals(TruceFeature.swapPlacesReceiver) || item.equals(TruceFeature.swapPlacesSender)) && isSwap == false && !(e.getAction() == Action.LEFT_CLICK_AIR) && !(e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            dontSwapMultiple = p;
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for(int i = 0; i < TruceFeature.truceMap.get(p).size(); i++)
            {
                if(TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName) && TruceFeature.cooldownMap.get(p).get(i + i + 1))
                {
                    //TruceFeature.cooldownMap.get(p).set(i + i + 1, false);
                    track = 0;
                    Player otherPlayer = TruceFeature.truceMap.get(p).get(i).getPlayer();
                    otherPlayer.sendMessage(p + " wants to swap places. Right click the swap item to do so.");
                    otherPlayer.sendMessage("If you don't want to swap places, ignore this");
                    isSwap = true;
                }
            }
            //Player otherPlayer = TruceFeature.truceMap.get(p).get(0).getPlayer();

            new BukkitRunnable()
            {

                @Override
                public void run() {
                    track++;
                    if(track == 2)
                    {
                        track = 0;
                        isSwap = false;
                        Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                    }
                }
            }.runTaskTimer(getInstance(), 0, 100);
        }
        //p.sendMessage("entering if");
        else if((item.equals(TruceFeature.teleporterReceiver) || item.equals(TruceFeature.teleporterSender)) && !(e.getAction() == Action.LEFT_CLICK_AIR) && !(e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for(int i = 0; i < TruceFeature.truceMap.get(p).size(); i++)
            {
                if(TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName) && TruceFeature.cooldownMap.get(p).get(i + i))
                {
                    //make cooldown for this
                    int in = i;
                    TruceFeature.cooldownMap.get(p).set(i + i, false);
                    tpTrack = 0;
                    Location tpLocation = TruceFeature.truceMap.get(p).get(i).getLocation();
                    p.teleport(tpLocation);

                    new BukkitRunnable()
                    {

                        @Override
                        public void run() {
                            tpTrack++;
                            if(tpTrack == 2)
                            {
                                tpTrack = 0;
                                TruceFeature.cooldownMap.get(p).set(in + in, true);
                                Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                            }
                        }
                    }.runTaskTimer(getInstance(), 0, 40);
                }
            }
        }

    }
}
