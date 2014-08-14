package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener{

	private Main plugin;

	public AsyncPlayerChat(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled())
			return;

		e.setCancelled(true);
		plugin.getCh().handleChat(e, plugin);
	}
}
