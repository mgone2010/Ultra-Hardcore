package me.chasertw123.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraft implements Listener {
	
	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {
		
		CraftingInventory ci = e.getInventory();
		
		if (ci.getResult() == new ItemStack(Material.GOLDEN_CARROT))	
			if (!ci.contains(new ItemStack(Material.GOLD_INGOT))) {
				ci.setResult(null);
				return;
			}
		
		else if (ci.getResult() == new ItemStack(Material.SPECKLED_MELON))
			if (!ci.contains(new ItemStack(Material.GOLD_BLOCK))) {
				ci.setResult(null);
				return;
			}
	}
}
