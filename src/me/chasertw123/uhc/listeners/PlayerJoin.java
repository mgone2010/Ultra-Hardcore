package me.chasertw123.uhc.listeners;

import java.sql.PreparedStatement;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.sql.SQL;
import me.chasertw123.uhc.timers.LobbyTimer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	private Main plugin;
	
	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		Arena a = plugin.getA();

		if (a.getGameState() == GameState.INGAME || a.getGameState() == GameState.DEATHMATCH 
				|| a.getGameState() == GameState.ENDING)
			p.kickPlayer("UHC is currently in progress!");
		
		SQL sql = plugin.getSql();
		
		sql.openConnection();
		
		try {
			
			if (!sql.containsPlayer(p)) {
				
				PreparedStatement stmt = sql.getC().prepareStatement(
						"INSERT INTO `uhc` values(?,0,0,0,0,0,0);");
				
				stmt.setString(1, p.getName());
				stmt.execute();
				
				stmt.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sql.closeConnection();
		}
		
		a.addPlayer(p);
		
		if (a.getGameState() == GameState.LOBBY || a.getGameState() == GameState.STARTING)
			p.teleport(plugin.getA().getLobby());
		
		p.setScoreboard(plugin.getSh().getScoreboard(p));
		
		if (a.getPlayers().length >= a.getNeededPlayers() && a.getGameState() == GameState.LOBBY) {
			
			a.setGameState(GameState.STARTING);
			
			new LobbyTimer(plugin, a);
		}

		plugin.sendMessage(p, "You joined a " + a.getArenaType().toString().toLowerCase() + " game.");
		if (a.getArenaType().isAutoTeaming())
			plugin.getTm().autoTeam(p, a.getArenaType());
	}
}
