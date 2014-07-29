package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener {

	private Main plugin;
	
	public SignChange(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		
		Player p = e.getPlayer();
		
		if (e.getLine(0).equalsIgnoreCase("[UHCShop]")) {
		
			if (!p.hasPermission("uhc.sign.create")) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
				e.getBlock().breakNaturally();
				return;
			}
		
			if (e.getLine(1).isEmpty() || e.getLine(2).isEmpty() || e.getLine(3).isEmpty()) {
				plugin.sendMessage(p, ChatColor.RED + "A sign must contain all three parameters!");
				e.getBlock().breakNaturally();
				return;
			}
		
			if (!this.isInt(e.getLine(1))) {
				plugin.sendMessage(p, ChatColor.GOLD + e.getLine(2) + ChatColor.RED + " is not a id!");
				e.getBlock().breakNaturally();
				return;
			}
		
			int id = Integer.parseInt(e.getLine(1));
		
			if (!this.isInt(e.getLine(2))) {
				plugin.sendMessage(p, ChatColor.GOLD + e.getLine(2) + ChatColor.RED + " is not an amount!");
				e.getBlock().breakNaturally();
				return;
			}
		
			int amount = Integer.parseInt(e.getLine(2));
		
			if (!this.isInt(e.getLine(3))) {
				plugin.sendMessage(p, ChatColor.GOLD + e.getLine(2) + ChatColor.RED + " is not a price!");
				e.getBlock().breakNaturally();
				return;
			}
		
			int price = Integer.parseInt(e.getLine(3));
		
			String fullWord = "" + ChatColor.GREEN + amount + " " + this.capitalizeFirstLetter(Material.getMaterial(id)
					.toString().toLowerCase().replaceAll("_", " "));
			
			e.setLine(1, fullWord);
			e.setLine(2, "");
			
			if (fullWord.length() > 15) {
				
				e.setLine(2, ChatColor.GREEN + fullWord.substring(15));
			}
			
			e.setLine(0, ChatColor.WHITE + "[" + ChatColor.AQUA + "UHCShop" + ChatColor.WHITE + "]");
			e.setLine(3, ChatColor.RED + "Cost: " + price);
		
			plugin.sendMessage(p, ChatColor.GREEN + "UHC Shop sign created!");
		
			return;
		}
		
		else if (e.getLine(0).equalsIgnoreCase("[UHCStats]")) {
			
			if (!p.hasPermission("uhc.sign.create")) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
				e.getBlock().breakNaturally();
				return;
			}
			
			e.setLine(0, ChatColor.WHITE + "[" + ChatColor.AQUA + "Stats" + ChatColor.WHITE + "]");
			e.setLine(1, ChatColor.RED + "-=-=-=-=-=-=-");
			e.setLine(2, ChatColor.GREEN + "Click here to");
			e.setLine(3, ChatColor.GREEN + "see yours!");
			
			plugin.sendMessage(p, ChatColor.GREEN + "UHC Stats sign created!");
			
			return;
		}
	}
	
	private boolean isInt(String s) {
		
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private String capitalizeFirstLetter(String original){
	    if(original.length() == 0)
	        return original;
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
}
