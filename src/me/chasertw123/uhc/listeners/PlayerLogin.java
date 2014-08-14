package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLogin implements Listener {

	private Main plugin;
	
	public PlayerLogin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		
		Player p = e.getPlayer();
		
		Arena a = plugin.getA();
		
		if (plugin.canRejoin(p.getName()))
			plugin.sendMessage(p, "Succesfully rejoined this game!");
		else
			plugin.sendMessage(p, "You joined a " + a.getArenaType().toString().toLowerCase() + " game.");

		if ((a.getGameState() == GameState.INGAME || a.getGameState() == GameState.DEATHMATCH 
				|| a.getGameState() == GameState.ENDING || a.getGameState() == GameState.RESETING) && !plugin.canRejoin(p.getName())) {
			e.disallow(Result.KICK_OTHER, "UHC is currently in progress!");
			// p.kickPlayer("UHC is currently in progress!");
			return;
		}
	}
}
