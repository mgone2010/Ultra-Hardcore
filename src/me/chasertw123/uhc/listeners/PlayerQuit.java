package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener{

	private Main plugin;
	
	public PlayerQuit(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent e) {
		plugin.getA().removePlayer(e.getPlayer());
		plugin.getTm().removePlayer(e.getPlayer());
	}
}