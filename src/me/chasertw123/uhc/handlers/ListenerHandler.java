package me.chasertw123.uhc.handlers;

import org.bukkit.plugin.PluginManager;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.listeners.InventoryClick;
import me.chasertw123.uhc.listeners.PlayerInteract;
import me.chasertw123.uhc.listeners.PlayerJoin;
import me.chasertw123.uhc.listeners.SignChange;

public class ListenerHandler {

	public ListenerHandler(Main plugin) {
		
		PluginManager pm = plugin.getServer().getPluginManager();
		
		pm.registerEvents(new InventoryClick(), plugin);
		pm.registerEvents(new PlayerInteract(plugin), plugin);
		pm.registerEvents(new PlayerJoin(plugin), plugin);
		pm.registerEvents(new SignChange(plugin), plugin);
	}
}
