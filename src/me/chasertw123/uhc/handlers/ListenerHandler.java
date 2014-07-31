package me.chasertw123.uhc.handlers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.listeners.EntityRegainHealth;
import me.chasertw123.uhc.listeners.InventoryClick;
import me.chasertw123.uhc.listeners.PlayerDeath;
import me.chasertw123.uhc.listeners.PlayerInteract;
import me.chasertw123.uhc.listeners.PlayerJoin;
import me.chasertw123.uhc.listeners.PlayerKick;
import me.chasertw123.uhc.listeners.PlayerQuit;
import me.chasertw123.uhc.listeners.SignChange;

import org.bukkit.plugin.PluginManager;

public class ListenerHandler {

	public ListenerHandler(Main plugin) {

		PluginManager pm = plugin.getServer().getPluginManager();

		pm.registerEvents(new EntityRegainHealth(), plugin);
		pm.registerEvents(new InventoryClick(), plugin);

		pm.registerEvents(new PlayerInteract(plugin), plugin);
		pm.registerEvents(new PlayerDeath(plugin), plugin);
		pm.registerEvents(new PlayerJoin(plugin), plugin);
		pm.registerEvents(new PlayerKick(plugin), plugin);
		pm.registerEvents(new PlayerQuit(plugin), plugin);
		pm.registerEvents(new SignChange(plugin), plugin);
	}
}
