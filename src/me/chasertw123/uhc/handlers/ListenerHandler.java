package me.chasertw123.uhc.handlers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.listeners.AsyncPlayerChat;
import me.chasertw123.uhc.listeners.BlockBreak;
import me.chasertw123.uhc.listeners.BlockPlace;
import me.chasertw123.uhc.listeners.ChunkPopulate;
import me.chasertw123.uhc.listeners.EntityDamageByEntity;
import me.chasertw123.uhc.listeners.EntityDeath;
import me.chasertw123.uhc.listeners.EntityRegainHealth;
import me.chasertw123.uhc.listeners.InventoryClick;
import me.chasertw123.uhc.listeners.PlayerDeath;
import me.chasertw123.uhc.listeners.PlayerInteract;
import me.chasertw123.uhc.listeners.PlayerJoin;
import me.chasertw123.uhc.listeners.PlayerKick;
import me.chasertw123.uhc.listeners.PlayerLogin;
import me.chasertw123.uhc.listeners.PlayerQuit;
import me.chasertw123.uhc.listeners.PlayerTeleport;
import me.chasertw123.uhc.listeners.PrepareItemCraft;
import me.chasertw123.uhc.listeners.SignChange;

import org.bukkit.plugin.PluginManager;

public class ListenerHandler {

	public ListenerHandler(Main plugin) {

		PluginManager pm = plugin.getServer().getPluginManager();

		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");

		pm.registerEvents(new AsyncPlayerChat(plugin), plugin);
		pm.registerEvents(new BlockBreak(plugin), plugin);
		pm.registerEvents(new BlockPlace(plugin), plugin);
		pm.registerEvents(new ChunkPopulate(plugin), plugin);
		pm.registerEvents(new EntityDamageByEntity(plugin), plugin);
		pm.registerEvents(new EntityDeath(), plugin);
		pm.registerEvents(new EntityRegainHealth(), plugin);
		pm.registerEvents(new InventoryClick(), plugin);
		pm.registerEvents(new PlayerInteract(plugin), plugin);
		pm.registerEvents(new PlayerJoin(plugin), plugin);
		pm.registerEvents(new PlayerDeath(plugin), plugin);
		pm.registerEvents(new PlayerKick(plugin), plugin);
		pm.registerEvents(new PlayerLogin(plugin), plugin);
		pm.registerEvents(new PlayerQuit(plugin), plugin);
		pm.registerEvents(new PlayerTeleport(plugin), plugin);
		pm.registerEvents(new PrepareItemCraft(), plugin);
		pm.registerEvents(new SignChange(plugin), plugin);
	}
}
