package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

	private Main plugin;
	
	public PlayerDeath(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (plugin.getA().getGameState() == GameState.DEATHMATCH || plugin.getA().getGameState() == GameState.INGAME) {
			plugin.setLeaveTime(e.getEntity().getName(), Long.MAX_VALUE - 30001);
			e.setDeathMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "" + ChatColor.ITALIC + "UHC" + ChatColor.WHITE + "] " + e.getDeathMessage());
			e.getEntity().kickPlayer(e.getDeathMessage().replaceAll(e.getEntity().getName(), "You"));
		}
	}
}
