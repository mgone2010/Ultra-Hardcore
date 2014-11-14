package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

	private Main plugin;
	
	public BlockBreak(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (plugin.getA().getGameState() == GameState.STARTING)
			e.setCancelled(true);
	}
}
