package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKick implements Listener{

	private Main plugin;
	
	public PlayerKick(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerKick (PlayerKickEvent e) {
		plugin.getA().removePlayer(e.getPlayer());
		plugin.getTm().removePlayer(e.getPlayer());
	}
}
