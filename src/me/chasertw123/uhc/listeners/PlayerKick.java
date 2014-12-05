package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerKick implements Listener{

	private Main plugin;

	public PlayerKick(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerKick (PlayerKickEvent e) {
		plugin.setLeaveTime(e.getPlayer().getName(), System.currentTimeMillis());
		plugin.getA().removePlayer(e.getPlayer());
		plugin.getTm().removePlayer(e.getPlayer());

		new BukkitRunnable() {

			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0)
					plugin.getBms().updatePlayers(Bukkit.getOnlinePlayers().size(), Bukkit.getOnlinePlayers().iterator().next());
			}
		}.runTask(plugin);
	}
}
