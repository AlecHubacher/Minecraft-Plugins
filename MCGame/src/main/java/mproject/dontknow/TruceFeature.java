package mproject.dontknow;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class TruceFeature implements CommandExecutor
{

    public static Dontknow plugin;
    public static Player soloPlayer;
    boolean canSwap = true;
    boolean canTP = true;
    //private List<Player> playerList = Bukkit.getServer().getWorld("World").getPlayers();
    //private List<Player> playerList = Bukkit.getServer().getOnlinePlayers();
    //private List<Player> playerList;
    public static ArrayList<Player> playerList = new ArrayList<>();

    //private Collection<Player> playerList = plugin.getServer().getOnlinePlayers();
    //int playerAmount = plugin.getServer().getWorld("World").getPlayers().size();


    public TruceFeature(Dontknow plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("Truce").setExecutor(this);
        plugin.getCommand("Setsolo").setExecutor(this);
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

    public static ItemStack teleporterSender = new ItemStack(Material.MUSIC_DISC_13);
    public static ItemStack swapPlacesSender = new ItemStack(Material.EMERALD);
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
        // make this outside of the onCommand method, otherwise it will be called every time a command is passed through
        /*
        for (Player p: p) {

        }
         */
        Player sender = (Player) send;
            for(Player p : Bukkit.getOnlinePlayers()/*getServer().getOnlinePlayers()*/)
            {
                if(playerList.contains(p))
                {
                    break;
                }
                else {
                    playerList.add(p);
                    p.sendMessage("Each player: " + p.getName() + " within foreach loop creating playerList");
                }
            }
/*
        if(cmd.getName().equalsIgnoreCase("Setsolo"))
        {
            if(soloPlayer == null)
            {
                for(int i = 0; i < playerList.size(); i++)
                {
                    if(playerList.get(i).getName().equalsIgnoreCase(args[0]))
                    {
                        soloPlayer = playerList.get(i);
                        //playerList.remove(playerList.get(i));
                        Bukkit.broadcastMessage("This is the soloPlayer: " + soloPlayer);
                    }
                    else
                    {
                        Bukkit.broadcastMessage(playerList.get(i).getName() + " has been added to the truceMap");
                        truceMap.put(playerList.get(i), new ArrayList<>());
                        cooldownMap.put(playerList.get(i), new ArrayList<>());
                    }
                }
                playerList.remove(soloPlayer);
            }
            else
            {
                sender.sendMessage("The solo player is already " + soloPlayer.getName());
            }
        }
 */
        sender.sendMessage("Entering truce if");
        if(cmd.getName().equalsIgnoreCase("Truce"))
        {
            if(!truceMap.containsKey(sender))
            {
                for(int z = 0; z < playerList.size(); z++) {
                    truceMap.put(playerList.get(z), new ArrayList<>());
                    cooldownMap.put(playerList.get(z), new ArrayList<>());
                }
            }
            sender.sendMessage("inside if");
            int playerListSize = playerList.size();
            sender.sendMessage("hi: " + playerListSize);
            for(int i = 0; i < playerList.size(); i++)
            {
                if(args.length == 0)
                {
                    sender.sendMessage("You must enter the name of the player you wish to truce with");
                }
                /*
                else if(args[0].equalsIgnoreCase(soloPlayer.getName()))
                {
                    sender.sendMessage("You can't truce with the solo player");
                }

                 */
                //sender.sendMessage("inside playerlist for loop");
                else if(playerList.get(i).getName().equalsIgnoreCase(args[0]) && (!truceMap.get(sender).contains(playerList.get(i))))
                {
                    sender.sendMessage("inside truce player name check if");
                    //int pNum = playerList.indexOf(args[0]);
                    Player receiver = playerList.get(i);
                    //Player receiver = playerList.get(pNum);

                    if(args.length < 2)
                    {
                        sender.sendMessage("inside if args.length < 2");
                        receiver.sendMessage(sender.getName() + " wishes to truce with you");
                        receiver.sendMessage("/truce " + sender.getName() + " accept to accept their request");
                        receiver.sendMessage("/truce " + sender.getName() + " deny to deny their request");
                        ArrayList<Player> arrayList = truceMap.get(sender);
                        arrayList.add(receiver);
                        truceMap.put(sender, arrayList);

                    }
                    else if(args.length >= 2)
                    {
                        if(args[1].equalsIgnoreCase("accept") /*&& (!(truceMap.get(sender).equals(null))) && (!(truceMap.get(receiver).equals(null)))*/)
                        {
                            /*
                            ArrayList<Player> arrayList = truceMap.get(sender);
                            arrayList.add(receiver);
                            truceMap.put(sender, arrayList);
                            truceMap.get(receiver).add(sender);
                             */
                            truceMap.get(sender).add(receiver);

                            cooldownMap.get(sender).add(canTP);
                            cooldownMap.get(sender).add(canSwap);

                            cooldownMap.get(receiver).add(canTP);
                            cooldownMap.get(receiver).add(canSwap);

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
                            tpReceiverItemMeta.setLocalizedName("Localized name is teleporter");
                            teleporterReceiver.setItemMeta(tpReceiverItemMeta);

                            swapReceiverItemMeta.setDisplayName(ChatColor.AQUA + sender.getName() + " swap");
                            swapReceiverItemMeta.setLocalizedName("localized name is swap");
                            swapPlacesReceiver.setItemMeta(swapReceiverItemMeta);

                            tpSenderItemMeta.setDisplayName(ChatColor.AQUA + receiver.getName() + " teleport");
                            tpSenderItemMeta.setLocalizedName("Localized name is teleporter");
                            teleporterSender.setItemMeta(tpSenderItemMeta);

                            swapSendertItemMeta.setDisplayName(ChatColor.AQUA + receiver.getName() + " swap");
                            swapSendertItemMeta.setLocalizedName("localized name is swap");
                            swapPlacesSender.setItemMeta(swapSendertItemMeta);

                            senderInv.addItem(teleporterSender);
                            senderInv.addItem(swapPlacesSender);
                            receiverInv.addItem(teleporterReceiver);
                            receiverInv.addItem(swapPlacesReceiver);

                        }
                        else if(args[1].equalsIgnoreCase("deny"))
                        {
                            truceMap.get(sender).remove(receiver);
                            //truceMap.get(receiver).remove(sender);

                            sender.sendMessage("You will not be in a truce with " + receiver);
                            receiver.sendMessage("You will not be in a truce with " + sender);
                        }
                    }
                }
                else if (truceMap.get(sender).contains(playerList.get(i)))
                {
                    sender.sendMessage("Either you are already in a truce with this player or the name is invalid");
                }
            }
        }



        return false;
    }
/*
    @EventHandler
    public void truceSwap(PlayerInteractEvent e)
    {
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        Player p = e.getPlayer();
        p.sendMessage("Entering if statement");
        if(item.equals(swapPlaces) && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR)
        {
            p.sendMessage("Inside if!!!!");
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
                    Bukkit.getServer().getScheduler().cancelTasks((Plugin) Dontknow.getInstance());
                    count = 0;
                }
                else if(item.equals(swapPlaces) && isSwap == true && other.getAction() == Action.RIGHT_CLICK_AIR || other.getAction() == Action.RIGHT_CLICK_AIR)
                {
                    Location truceLocation = truceMap.get(p).get(0).getLocation();
                    Location pLocation = p.getLocation();
                    p.teleport(truceLocation);
                    truceMap.get(p).get(0).getPlayer().teleport(pLocation);
                }
            }
        }.runTaskTimer((Plugin) Dontknow.getInstance(), 0, 100);
    }

 */

}
