package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

	private Main plugin;

	public BlockPlace(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace (BlockPlaceEvent e) {
		Location loc = e.getBlock().getLocation();

		if (Math.abs(loc.getBlockX()) + loc.getBlockY() >= 1245 || 
				Math.abs(loc.getBlockZ()) + loc.getBlockY() >= 1245) {
			e.setCancelled(true);
			plugin.sendMessage(e.getPlayer(), ChatColor.RED + "You cannot build high near the bedrock wall!");
		}
	}
}
