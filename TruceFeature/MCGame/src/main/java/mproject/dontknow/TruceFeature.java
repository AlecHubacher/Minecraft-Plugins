package mproject.dontknow;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TruceFeature implements CommandExecutor
{

    private MinecraftGame plugin;
    private String soloPlayer;
    private List<Player> playerList = (List<Player>) plugin.getServer().getWorld("World").getPlayers();
    //int playerAmount = plugin.getServer().getWorld("World").getPlayers().size();


    public TruceFeature(MinecraftGame plugin, String soloPlayer)
    {
        this.plugin = plugin;
        plugin.getCommand("Truce").setExecutor(this);

        this.soloPlayer = soloPlayer;
    }

    ArrayList<String> p1 = new ArrayList<>(2);
    ArrayList<String> p2 = new ArrayList<>(2);
    ArrayList<String> p3 = new ArrayList<>(2);

    HashMap<Player, ArrayList<Player>> truceMap = new HashMap<>();

    Material teleporter = Material.MUSIC_DISC_13;
    Material swapPlaces = Material.MUSIC_DISC_11;




    @Override
    public boolean onCommand(CommandSender send, Command cmd, String label, String[] args) {
        // make this outside of the onCommand method, otherwise it will be called every time a command is passed through
        for(int i = 0; i < playerList.size(); i++)
        {
            if(playerList.get(i).toString().equalsIgnoreCase(soloPlayer))
            {
                playerList.remove(playerList.get(i));
            }
            else
            {
                truceMap.put(playerList.get(i), new ArrayList<>());
            }
        }

        Player sender = (Player) send;
        if(cmd.getName().equalsIgnoreCase("Truce"))
        {
            if(playerList.contains(args[0]))
            {
                int pNum = playerList.indexOf(args[0]);
                Player receiver = playerList.get(pNum);

                if(args.length < 2)
                {
                    receiver.sendMessage(sender.getName() + " wishes to truce with you");
                    receiver.sendMessage("/truce " + sender.getName() + " accept to accept their request");
                    receiver.sendMessage("/truce " + sender.getName() + " deny to deny their request");
                }
                else if(args.length == 2)
                {
                    if(args[1].equalsIgnoreCase("accept") && (!(truceMap.get(sender).contains(receiver))) && (!(truceMap.get(receiver).contains(sender))))
                    {
                        truceMap.get(sender).add(receiver);
                        truceMap.get(receiver).add(sender);
                        sender.sendMessage("You are now in a truce with " + receiver);
                        receiver.sendMessage("You are now in a truce with " + sender);

                        Inventory senderInv = sender.getInventory();
                        Inventory receiverInv = receiver.getInventory();

                        ItemStack tpItem = new ItemStack(teleporter);
                        ItemStack swapItem = new ItemStack(swapPlaces);

                        ItemMeta tpItemMeta = tpItem.getItemMeta();
                        ItemMeta swapItemMeta = swapItem.getItemMeta();

                        tpItemMeta.setDisplayName(ChatColor.AQUA + "teleporter");
                        swapItemMeta.setDisplayName(ChatColor.AQUA + "swap");

                        senderInv.addItem(tpItem);
                        senderInv.addItem(swapItem);
                        receiverInv.addItem(tpItem);
                        receiverInv.addItem(swapItem);

                    }
                    else if(args[1].equalsIgnoreCase("deny"))
                    {
                        sender.sendMessage("You will not be in a truce with " + receiver);
                        receiver.sendMessage("You will not be in a truce with " + sender);
                    }
                }
            }
            else
            {
                sender.sendMessage("You must enter a valid player name to truce with them");
            }
        }



        return false;
    }

    //add a cooldown of some sort
    @EventHandler
    public void truceTeleport(PlayerInteractEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        if(item.equals(teleporter) && e.getAction() == Action.RIGHT_CLICK_AIR)
        {
            Location tpLocation = truceMap.get(p).get(0).getLocation();
            p.teleport(tpLocation);
        }
    }

    boolean isSwap = false;

    @EventHandler
    public void truceSwap(PlayerInteractEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        if(item.equals(swapPlaces) && e.getAction() == Action.RIGHT_CLICK_AIR)
        {
            Player otherPlayer = truceMap.get(p).get(0).getPlayer();
            otherPlayer.sendMessage(p + " wants to swap places. Right click the swap item to do so.");
            otherPlayer.sendMessage("If you don't want to swap places, left click the swap item");
            isSwap = true;
        }
    }

    int count = 0;

    @EventHandler
    public void confirmSwap(PlayerInteractEvent other)
    {
        ItemStack item = other.getPlayer().getInventory().getItemInMainHand();
        Player p = other.getPlayer();


        new BukkitRunnable()
        {

            @Override
            public void run() {
                count++;
                if(count == 2)
                {
                    Bukkit.getServer().getScheduler().cancelTasks((Plugin) MinecraftGame.getInstance());
                    count = 0;
                }
                else if(item.equals(swapPlaces) && other.getAction() == Action.RIGHT_CLICK_AIR && isSwap == true)
                {
                    Location truceLocation = truceMap.get(p).get(0).getLocation();
                    Location pLocation = p.getLocation();
                    p.teleport(truceLocation);
                    truceMap.get(p).get(0).getPlayer().teleport(pLocation);
                }
            }
        }.runTaskTimer((Plugin) MinecraftGame.getInstance(), 0, 100);
    }

}
