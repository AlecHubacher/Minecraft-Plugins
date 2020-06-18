package mproject.dontknow;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class TruceFeature implements CommandExecutor {

    public static Dontknow plugin;
    public static Player soloPlayer;
    boolean canSwap = true;
    boolean canTP = true;
    boolean isRequestSwap = false;

    ItemStack sword = new ItemStack(Material.STONE_SWORD);
    //private List<Player> playerList = Bukkit.getServer().getWorld("World").getPlayers();
    //private List<Player> playerList = Bukkit.getServer().getOnlinePlayers();
    //private List<Player> playerList;
    public static ArrayList<Player> playerList = new ArrayList<>();

    //private Collection<Player> playerList = plugin.getServer().getOnlinePlayers();
    //int playerAmount = plugin.getServer().getWorld("World").getPlayers().size();


    public TruceFeature(Dontknow plugin) {
        this.plugin = plugin;
        plugin.getCommand("Truce").setExecutor(this);
        plugin.getCommand("Setsolo").setExecutor(this);
        plugin.getCommand("Findplayer").setExecutor(this);
    }

    /*
        ArrayList<String> p1 = new ArrayList<>(2);
        ArrayList<String> p2 = new ArrayList<>(2);
        ArrayList<String> p3 = new ArrayList<>(2);
     */
    public static HashMap<Player, ArrayList<Player>> truceMap = new HashMap<>();
    public static HashMap<Player, ArrayList<Boolean>> cooldownMap = new HashMap<>();
    /*
        Material teleporter = Material.MUSIC_DISC_13;
        Material swapPlaces = Material.MUSIC_DISC_11;
      */
    public static ItemStack teleporterReceiver = new ItemStack(Material.MUSIC_DISC_13);
    public static ItemStack swapPlacesReceiver = new ItemStack(Material.EMERALD);
/*
    public static ItemStack teleporterSender = new ItemStack(Material.MUSIC_DISC_13);
    public static ItemStack swapPlacesSender = new ItemStack(Material.EMERALD);
*/
    public static ItemStack teleporterSender = new ItemStack(Material.STICK);
    public static ItemStack swapPlacesSender = new ItemStack(Material.DIAMOND);

    public static ItemStack compass = new ItemStack(Material.COMPASS);
    /*
    public void makePlayerList()
    {
        for(Player p : Bukkit.getOnlinePlayers()getServer().getOnlinePlayers())
        {
            playerList.add(p);
            System.out.println("Each player: " + p.getName() + " within foreach loop creating playerList");
        }
    }

     */


    ArrayList<Player> temp = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String label, String[] args) {
        //ItemMeta swordmeta = sword.getItemMeta();

        // make this outside of the onCommand method, otherwise it will be called every time a command is passed through
        /*
        for (Player p: p) {

        }
         */
        Player sender = (Player) send;
        for (Player p : Bukkit.getOnlinePlayers()/*getServer().getOnlinePlayers()*/) {
            if (playerList.contains(p)) {
                break;
            } else {
                playerList.add(p);
                p.sendMessage("Each player: " + p.getName() + " within foreach loop creating playerList");
            }
        }
        if(truceMap.size() == 0 && cooldownMap.size() == 0)
        {
            for (int i = 0; i < playerList.size(); i++)
            {
                truceMap.put(playerList.get(i), new ArrayList<>());
                cooldownMap.put(playerList.get(i), new ArrayList<>());

            }
        }
        if (cmd.getName().equalsIgnoreCase("Setsolo")) {
            if (soloPlayer == null) {
                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).getName().equalsIgnoreCase(args[0])) {
                        soloPlayer = playerList.get(i);
                        //playerList.remove(playerList.get(i));
                        Bukkit.broadcastMessage("This is the soloPlayer: " + soloPlayer);
                    }
                }
            }
        }
            //sender.sendMessage("Entering truce if");
        else if (cmd.getName().equalsIgnoreCase("Truce")) {
            /*
            if (!truceMap.containsKey(sender)) {
                for (int z = 0; z < playerList.size(); z++) {
                    truceMap.put(playerList.get(z), new ArrayList<>());
                    cooldownMap.put(playerList.get(z), new ArrayList<>());
                }
            }

             */
            for (int i = 0; i < playerList.size(); i++) {
                if (args.length == 0) {
                    sender.sendMessage("You must enter the name of the player you wish to truce with");
                }
            /*
            else if(args[0].equalsIgnoreCase(soloPlayer.getName()))
            {
                sender.sendMessage("You can't truce with the solo player");
            }

             */
                //sender.sendMessage("inside playerlist for loop");
                else if (playerList.get(i).getName().equalsIgnoreCase(args[0]))
                {
                    if ((!truceMap.get(sender).contains(playerList.get(i))))
                    {
                        //int pNum = playerList.indexOf(args[0]);
                        Player receiver = playerList.get(i);
                        //Player receiver = playerList.get(pNum);

                        if (args.length < 2) {
                            receiver.sendMessage(ChatColor.RED + sender.getName() + ChatColor.WHITE + " wishes to truce with you");
                            receiver.sendMessage(ChatColor.GREEN + "/truce " + ChatColor.RED + sender.getName() + ChatColor.GREEN + " accept to accept their request");
                            receiver.sendMessage(ChatColor.GREEN + "/truce " + ChatColor.RED + sender.getName() + ChatColor.GREEN + " deny to deny their request");
                        /*
                        ArrayList<Player> arrayList = truceMap.get(sender);
                        arrayList.add(receiver);
                        truceMap.put(sender, arrayList);
                         */
                            truceMap.get(sender).add(receiver);

                            cooldownMap.get(sender).add(canTP);
                            cooldownMap.get(sender).add(canSwap);
                            cooldownMap.get(sender).add(isRequestSwap);

                        } else if (args.length >= 2) {
                            if (args[1].equalsIgnoreCase("accept") /*&& (!(truceMap.get(sender).equals(null))) && (!(truceMap.get(receiver).equals(null)))*/) {
                        /*
                        ArrayList<Player> arrayList = truceMap.get(sender);
                        arrayList.add(receiver);
                        truceMap.put(sender, arrayList);
                        truceMap.get(receiver).add(sender);
                         */
                                truceMap.get(sender).add(receiver);

                                cooldownMap.get(sender).add(canTP);
                                cooldownMap.get(sender).add(canSwap);
                                cooldownMap.get(sender).add(isRequestSwap);
/*
                                cooldownMap.get(receiver).add(canTP);
                                cooldownMap.get(receiver).add(canSwap);
                                cooldownMap.get(receiver).add(isRequestSwap);

 */

                                sender.sendMessage("You are now in a truce with " + receiver);
                                receiver.sendMessage("You are now in a truce with " + sender);

                                Inventory senderInv = sender.getInventory();
                                Inventory receiverInv = receiver.getInventory();
/*
                        ItemStack tpItem = new ItemStack(teleporter);
                        ItemStack swapItem = new ItemStack(swapPlaces);

*/


                                ItemMeta tpReceiverItemMeta = teleporterReceiver.getItemMeta();
                                ItemMeta swapReceiverItemMeta = swapPlacesReceiver.getItemMeta();

                                ItemMeta tpSenderItemMeta = teleporterSender.getItemMeta();
                                ItemMeta swapSendertItemMeta = swapPlacesSender.getItemMeta();

                                tpReceiverItemMeta.setDisplayName(ChatColor.AQUA + sender.getName() + " teleport");
                                teleporterReceiver.setItemMeta(tpReceiverItemMeta);

                                swapReceiverItemMeta.setDisplayName(ChatColor.AQUA + sender.getName() + " swap");
                                swapPlacesReceiver.setItemMeta(swapReceiverItemMeta);

                                tpSenderItemMeta.setDisplayName(ChatColor.AQUA + receiver.getName() + " teleport");
                                teleporterSender.setItemMeta(tpSenderItemMeta);

                                swapSendertItemMeta.setDisplayName(ChatColor.AQUA + receiver.getName() + " swap");
                                swapPlacesSender.setItemMeta(swapSendertItemMeta);

                                senderInv.addItem(teleporterSender);
                                senderInv.addItem(swapPlacesSender);
                                receiverInv.addItem(teleporterReceiver);
                                receiverInv.addItem(swapPlacesReceiver);

                            } else if (args[1].equalsIgnoreCase("deny")) {
                                truceMap.get(receiver).remove(sender);

                                cooldownMap.get(receiver).remove(canTP);
                                cooldownMap.get(receiver).remove(canSwap);
                                cooldownMap.get(receiver).remove(isRequestSwap);
                                //truceMap.get(receiver).remove(sender);

                                sender.sendMessage("You will not be in a truce with " + receiver);
                                receiver.sendMessage("You will not be in a truce with " + sender);
                            }
                        }
                    }
                    else if (truceMap.get(sender).contains(playerList.get(i))) {
                        sender.sendMessage("Either you are already in a truce with this player");
                    }
                }
                else
                {
                    sender.sendMessage("This is an invalid name");
                }
            }
        }
        else if(cmd.getName().equalsIgnoreCase("Findplayer"))
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
                        p.setCompassTarget(soloPlayer.getLocation());
                    }

                }

            }.runTaskTimer((Plugin) Dontknow.getInstance(),0,1);

        }

            return false;
        }
        //return false;

    }