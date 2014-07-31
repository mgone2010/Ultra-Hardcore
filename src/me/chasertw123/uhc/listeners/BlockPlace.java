package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

	private Main plugin;

	public BlockPlace (Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!e.getBlock().getWorld().getName().contains("UHC_world"))
			return;

		if (e.getBlock().getLocation().getY() > 200 || plugin.getA().getGameState() == GameState.LOBBY || 
				plugin.getA().getGameState() == GameState.STARTING)
			e.setCancelled(true);
	}
}
