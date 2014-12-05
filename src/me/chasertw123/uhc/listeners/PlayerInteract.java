package me.chasertw123.uhc.listeners;

//import java.util.Arrays;

//import org.bukkit.Bukkit;
import me.chasertw123.uhc.Main;
//import me.chasertw123.uhc.sql.SQLAPI.StatType;

import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteract implements Listener {

	private Main plugin;
	
	public PlayerInteract(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if (plugin.getA().getGameState() == GameState.STARTING) {
			e.setCancelled(true);
			p.updateInventory();
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null 
				&& e.getClickedBlock().getState() != null && e.getClickedBlock().getState() instanceof Sign) {
			
			Sign sign = (Sign) e.getClickedBlock().getState();
			
			if (sign.getLine(0).contains(ChatColor.WHITE + "[" + ChatColor.AQUA + "UHCShop" + ChatColor.WHITE + "]")) {
			
				if (sign.getLine(1).isEmpty() || sign.getLine(3).isEmpty())
					return;
			
				e.setCancelled(true);
			
				char c = ChatColor.stripColor(sign.getLine(1)).charAt(0);

				Material material = Material.getMaterial(ChatColor.stripColor(sign.getLine(1) + sign.getLine(2)).substring(2).toUpperCase().replaceAll(" ", "_"));
			
				int amount = Integer.parseInt(String.valueOf(c)), cost = Integer.parseInt(ChatColor.stripColor(sign.getLine(3)).replaceAll("Cost: ", ""));
			
				plugin.sendMessage(p, ChatColor.RED + "When the plugin is complete you will lose money and recieve "
						+ "items once the game starts! You would have lost " + cost + " UHC points!");
			
				p.getInventory().addItem(new ItemStack(material, amount));
				p.updateInventory();
			}
			/*
			else if (sign.getLine(0).contains(ChatColor.WHITE + "[" + ChatColor.AQUA + "Stats" + ChatColor.WHITE + "]")) {
				
				if (sign.getLine(1).isEmpty() || sign.getLine(2).isEmpty() || sign.getLine(3).isEmpty())
					return;
				
				e.setCancelled(true);
				
				Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Your Ultra Hardcore Stats");
				
				int kills = plugin.getAPI().getStat(StatType.KILLS, p), deaths = plugin.getAPI().getStat(StatType.DEATHS, p),
						gamesPlayed = plugin.getAPI().getStat(StatType.GAMES_PLAYED, p),
						gamesWon = plugin.getAPI().getStat(StatType.GAMES_PLAYED, p), gamesLost = gamesPlayed - gamesWon;
				double kdr = Math.round(kills / Math.max(1D, deaths) * 100D) / 100D, wlr = Math.round(gamesWon / Math.max(1D, gamesPlayed) * 100D) / 100D;
				
				inv.addItem(this.createFancyItemStack(Material.BOW, 1, (short) 0, ChatColor.GREEN + "Kills", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have " + ChatColor.GOLD + kills + ChatColor.WHITE + "" + ChatColor.ITALIC + " kills!"));
				inv.addItem(this.createFancyItemStack(Material.BONE, 1, (short) 0, ChatColor.GREEN + "Deaths", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have " + ChatColor.GOLD + deaths + ChatColor.WHITE + "" + ChatColor.ITALIC + " deaths!"));
				inv.addItem(this.createFancyItemStack(Material.SKULL_ITEM, 1, (short) 3, ChatColor.GREEN + "Kill Death Ratio", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have a KDR of " + ChatColor.GOLD + kdr + ChatColor.WHITE + "" + ChatColor.ITALIC + "!"));
				inv.addItem(this.createFancyItemStack(Material.GOLD_NUGGET, 1, (short) 0, ChatColor.GREEN + "UHC Points", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have " + ChatColor.GOLD + plugin.getAPI().getStat(StatType.POINTS, p) + ChatColor.WHITE + "" + ChatColor.ITALIC + " UHC points!"));
				inv.addItem(this.createFancyItemStack(Material.EMERALD_BLOCK, 1, (short) 0, ChatColor.GREEN + "Games Won", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have won " + ChatColor.GOLD + gamesWon + ChatColor.WHITE + "" + ChatColor.ITALIC + " games!"));
				inv.addItem(this.createFancyItemStack(Material.COAL_BLOCK, 1, (short) 0, ChatColor.GREEN + "Games Lost", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have lost " + ChatColor.GOLD + gamesLost + ChatColor.WHITE + "" + ChatColor.ITALIC + " games!"));
				inv.addItem(this.createFancyItemStack(Material.WATCH, 1, (short) 0, ChatColor.GREEN + "Games Played", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have played " + ChatColor.GOLD + gamesPlayed + ChatColor.WHITE + "" + ChatColor.ITALIC + " games!"));
				inv.addItem(this.createFancyItemStack(Material.ENCHANTED_BOOK, 1, (short) 3, ChatColor.GREEN + "Win Loss Ratio", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have a WLR of " + ChatColor.GOLD + wlr + ChatColor.WHITE + "" + ChatColor.ITALIC + "!"));
				inv.addItem(this.createFancyItemStack(Material.GOLDEN_APPLE, 0, (short) 1, ChatColor.GREEN + "Golden Apples Ate", "", ChatColor.WHITE + "" + ChatColor.ITALIC + "You have ate " + ChatColor.GOLD + plugin.getAPI().getStat(StatType.APPLES_ATE, p) + ChatColor.WHITE + "" + ChatColor.ITALIC + " golden apples!"));
			
				p.openInventory(inv);
			}*/
		}
	}
	/*
	private ItemStack createFancyItemStack(Material material, int amount, short data, String displayName, String... lore) {
		
		ItemStack i = new ItemStack(material, amount, data);
		ItemMeta im = i.getItemMeta();
		
		im.setDisplayName(displayName);
		im.setLore(Arrays.asList(lore));
		
		i.setItemMeta(im);
		
		return i;
	}*/
}
