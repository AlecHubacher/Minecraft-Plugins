package me.trumpet.autofillchests.commands;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.trumpet.autofillchests.Main;

public class AutoFillChestsCommands implements CommandExecutor{
	private Main plugin;
	
	public AutoFillChestsCommands(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("fillchests").setExecutor(this);
		
	}
	
	//Map<Material, Map<Integer, Integer>> itemList = new HashMap<Material, Map<Integer, Integer>>();
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//itemList.put(Material.ARROW, new HashMap() {{put(30,1);}});
		if(!(sender instanceof Player))
		{
			sender.sendMessage("Only players may execute!");
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("fillchests"))
		{
			for(Chunk chunk : plugin.getServer().getWorld("World").getLoadedChunks()){
				for(BlockState entities : chunk.getTileEntities()){
				if(entities instanceof Chest){
				Inventory inv = ((Chest) entities).getInventory();
				fillChests(inv,entities);
				}
				}
				}
			return true;
		}
		return false;
	}
	
	public void fillChests(Inventory inv,BlockState entities)
	{
		World world = plugin.getServer().getWorld("World");
		Location chestLoc = new Location(world,44.0,180.0,-310.0);
		inv.clear();
		Random itemnum = new Random();
		int tier = itemnum.nextInt((10-1)+1)+1;
		int items = itemnum.nextInt((8-3) +1) + 3;
		for(int i = 1; i < items+1; i++){
		Random slotnum = new Random();
		Random itemrand = new Random();
		int item = 1+itemrand.nextInt(60);
		int slot = slotnum.nextInt(inv.getSize());
		double xVal= entities.getLocation().getX() -  chestLoc.getX();
		double yVal= entities.getLocation().getY() - chestLoc.getY();
		double zVal = entities.getLocation().getZ() - chestLoc.getZ();
		if(item == 1 || item == 2 || item == 3){
		inv.setItem(slot, new ItemStack(Material.ENDER_PEARL, 1));
		}else if(item == 4 || item == 5 || item == 6){
		inv.setItem(slot, new ItemStack(Material.FEATHER, 2));
		}
		else if((item==7 || item==8 || item==9)&& (!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.ARROW, 4));
		}
		else if((item==12) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.STONE_SWORD, 1));
		}
		else if((item==13 || item==14 || item==15)&& (!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.COOKED_BEEF, 6));
		}
		else if((item==19 || item==20 || item==21) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.DIAMOND, 1));
		}
		else if((item==22 || item==23 || item==24)&&(!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.LEATHER_HELMET, 1));
		}
		else if((item==25 || item==26 || item==27)&&(!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.FISHING_ROD, 1));
		}
		else if((item==28 || item==29 || item==30)&&(!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		}
		else if((item==31 || item==32 || item==33)&&(!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.LEATHER_LEGGINGS, 1));
		}
		else if((item==34 || item==35 || item==36)&&(!(tier==1 || tier==2 || tier==3)))
		{
			inv.setItem(slot, new ItemStack(Material.LEATHER_BOOTS, 1));
		}
		else if((item==37 || item==38 || item==39) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.IRON_HELMET, 1));
		}
		else if((item==40 || item==41 || item==42) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.IRON_CHESTPLATE, 1));
		}
		else if((item==43 || item==44 || item==45) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.IRON_LEGGINGS, 1));
		}
		else if((item==46 || item==47 || item==48) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.IRON_BOOTS, 1));
		}

		else if((item==49 || item==50 || item==51) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		}

		else if((item==52 || item==53 || item==54) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		}

		else if((item==55 || item==56 || item==57) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		}

		else if((item==58 || item==59 || item==60) && (tier==1 || tier==2 || tier==3))
		{
			inv.setItem(slot, new ItemStack(Material.CHAINMAIL_HELMET, 1));
		}

		if((xVal==0)&&(zVal==-1)&&(yVal==0))
		{
			if(inv.contains(Material.STONE_SWORD))
			{
				continue;
			}
			Bukkit.broadcastMessage("we made it");
			inv.setItem(slot, new ItemStack(Material.STONE_SWORD));
		}

		}

	}
	}
