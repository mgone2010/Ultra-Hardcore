package me.chasertw123.uhc.listeners;

import java.util.ArrayList;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

	private Main plugin;

	public PlayerDeath(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		if (plugin.getA().getGameState() == GameState.DEATHMATCH || plugin.getA().getGameState() == GameState.INGAME 
				|| plugin.getA().getGameState() == GameState.ENDING) {
			
			plugin.setLeaveTime(e.getEntity().getName(), 0L);
			
			for (Team t : (ArrayList<Team>) Team.teamObjects.clone())
				t.removePlayer(e.getEntity().getName(), true);
			
			e.setDeathMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "" + ChatColor.ITALIC + "UHC" + ChatColor.WHITE + "] " + e.getDeathMessage());
			e.getEntity().kickPlayer(e.getDeathMessage().replaceAll(e.getEntity().getName(), "You").replaceAll("was", "were"));
		}
	}
}
