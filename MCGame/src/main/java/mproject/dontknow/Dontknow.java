package mproject.dontknow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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


    //boolean isRequestSwap = false;
    int count = 0;
    int track = 0;
    int swapTrack = 0;
    int tpTrack = 0;
    Player dontSwapMultiple;

    //add a cooldown of some sort
/*
    @EventHandler
    public void findTarget(PlayerItemHeldEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        if(item.equals(TruceFeature.compass))
        {
            Location loc = TruceFeature.soloPlayer.getLocation();
            p.setCompassTarget(loc);

        }
    }

 */

    @EventHandler
    public void truceTeleport(PlayerInteractEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        if((item.equals(TruceFeature.swapPlacesReceiver) || item.equals(TruceFeature.swapPlacesSender)) && !(e.getAction() == Action.RIGHT_CLICK_AIR) && !(e.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for (int i = 0; i < TruceFeature.truceMap.get(p).size(); i++) {
                if (TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName)) {
                    Player check = TruceFeature.truceMap.get(p).get(i).getPlayer();
                    int cori = TruceFeature.truceMap.get(check).indexOf(p);
                    if (TruceFeature.cooldownMap.get(check).get((cori * 3) + 2) == true && TruceFeature.cooldownMap.get(check).get((cori * 3) + 1) == true)
                    {

                        TruceFeature.cooldownMap.get(check).set((cori * 3) + 1, false);
                        TruceFeature.cooldownMap.get(check).set((cori * 3) + 2, false);
                        //TruceFeature.cooldownMap.get(check).set(cori + cori + cori + 2, false);
                        swapTrack = 0;
                        int corint = cori;
                        Location truceLocation = TruceFeature.truceMap.get(p).get(i).getLocation();
                        Location pLocation = p.getLocation();
                        p.teleport(truceLocation);
                        TruceFeature.truceMap.get(p).get(i).getPlayer().teleport(pLocation);

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                swapTrack++;
                                if (swapTrack == 2) {
                                    swapTrack = 0;
                                    TruceFeature.cooldownMap.get(check).set((cori * 3) + 1, true);
                                    //TruceFeature.cooldownMap.get(check).set(corint + corint + corint + 2, true);
                                    Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                                }
                            }
                        }.runTaskTimer(getInstance(), 0, 40);
                    }
                    else if (TruceFeature.cooldownMap.get(check).get((cori * 3)+ 2) == false)
                    {
                        p.sendMessage("This person has not requested a swap");
                    }
                    else if (TruceFeature.cooldownMap.get(check).get((cori * 3) + 1) == false)
                    {
                        p.sendMessage("This person must wait their cooldown");
                    }
                }
            }
        }
        else if((item.equals(TruceFeature.swapPlacesReceiver) || item.equals(TruceFeature.swapPlacesSender)) && !(e.getAction() == Action.LEFT_CLICK_AIR) && !(e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            dontSwapMultiple = p;
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for(int i = 0; i < TruceFeature.truceMap.get(p).size(); i++)
            {
                p.sendMessage( String.valueOf(TruceFeature.cooldownMap.get(p).size()));
                if(TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName))
                {
                    if (TruceFeature.cooldownMap.get(p).get((3 * i) + 1))
                    {
                        if (TruceFeature.cooldownMap.get(p).get((i * 3) + 2) == false)
                        {
                            //TruceFeature.cooldownMap.get(p).set(i + i + 1, false);
                            TruceFeature.cooldownMap.get(p).set((i * 3) + 2, true);
                            track = 0;
                            Player otherPlayer = TruceFeature.truceMap.get(p).get(i).getPlayer();
                            otherPlayer.sendMessage(ChatColor.BLUE + p.getName() + " wants to swap places. Left click the swap item to do so.");
                            otherPlayer.sendMessage("If you don't want to swap places, ignore this");

                            Player check = TruceFeature.truceMap.get(p).get(i).getPlayer();

                            int inum = TruceFeature.truceMap.get(p).indexOf(check);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    track++;
                                    if (track == 2) {
                                        track = 0;
                                        TruceFeature.cooldownMap.get(p).set((inum * 3) + 2, false);
                                        Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                                    }
                                }
                            }.runTaskTimer(getInstance(), 0, 100);
                        }
                        else {
                            p.sendMessage("You must wait for the person to respond");
                        }
                    }
                    else {
                        p.sendMessage("You must wait for YOUR cooldown to end");
                    }
                }
                else
                {
                    p.sendMessage("This player name is invalid");
                }
            }
            //Player otherPlayer = TruceFeature.truceMap.get(p).get(0).getPlayer();
        }
        //p.sendMessage("entering if");
        else if((item.equals(TruceFeature.teleporterReceiver) || item.equals(TruceFeature.teleporterSender)) && !(e.getAction() == Action.LEFT_CLICK_AIR) && !(e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            String[] name = item.getItemMeta().getDisplayName().split(" ");
            String pName = name[0].substring(2);
            for(int i = 0; i < TruceFeature.truceMap.get(p).size(); i++)
            {
                if(TruceFeature.truceMap.get(p).get(i).getDisplayName().equalsIgnoreCase(pName))
                {
                    if (TruceFeature.cooldownMap.get(p).get(3 * i))
                    {
                        //make cooldown for this
                        TruceFeature.cooldownMap.get(p).set(3 * i, false);
                        int in = i;
                        tpTrack = 0;
                        Location tpLocation = TruceFeature.truceMap.get(p).get(i).getLocation();
                        p.teleport(tpLocation);

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                tpTrack++;
                                if (tpTrack == 2) {
                                    tpTrack = 0;
                                    TruceFeature.cooldownMap.get(p).set((in * 3), true);
                                    Bukkit.getServer().getScheduler().cancelTasks(getInstance());
                                }
                            }
                        }.runTaskTimer(getInstance(), 0, 40);
                    }
                    else
                    {
                        p.sendMessage("Wait for your canTP cooldown to end");
                    }
                }
                else
                {
                    p.sendMessage("This is an invalid player name");
                }
            }
        }

    }
}
