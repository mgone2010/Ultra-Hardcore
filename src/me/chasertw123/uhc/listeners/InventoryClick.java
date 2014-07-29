package me.chasertw123.uhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (e.getInventory() == null)
			return;
		
		if (e.getInventory().getName().equals(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Your Ultra Hardcore Stats")) {
			
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == null || e.getCurrentItem().getType() == Material.AIR)
				return;
			
			e.setCancelled(true);
		}
	}
}
