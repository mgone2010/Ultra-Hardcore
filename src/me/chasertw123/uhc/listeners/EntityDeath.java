package me.chasertw123.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeath implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDeathEvent e) {
		for (ItemStack is : e.getDrops())
			if (is.getType() == Material.GHAST_TEAR)
				is.setType(Material.GOLD_INGOT);
	}
}
