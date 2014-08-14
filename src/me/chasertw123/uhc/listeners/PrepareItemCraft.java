package me.chasertw123.uhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraft implements Listener {

	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {

		CraftingInventory ci = e.getInventory();

		if (ci.getResult().getType() == Material.GOLDEN_CARROT)	
			if (!ci.contains(new ItemStack(Material.GOLD_INGOT))) {
				ci.setResult(null);
				for (HumanEntity he : e.getViewers())
					if (he instanceof Player)
						((Player) he).sendMessage(ChatColor.RED + "You need 8 golden ingots instead of nuggets to craft this!");
				return;
			}

		if (ci.getResult().getType() == Material.SPECKLED_MELON)
			if (!ci.contains(new ItemStack(Material.GOLD_BLOCK))) {
				ci.setResult(null);
				for (HumanEntity he : e.getViewers())
					if (he instanceof Player)
						((Player) he).sendMessage(ChatColor.RED + "You need 1 golden block instead of the nuggets to craft this!");
				return;
			}
	}
}
