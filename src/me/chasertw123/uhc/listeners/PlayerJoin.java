package me.chasertw123.uhc.listeners;

//import java.sql.PreparedStatement;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.teams.Team;
import me.chasertw123.uhc.timers.LobbyTimer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
//import me.chasertw123.uhc.sql.SQL;

public class PlayerJoin implements Listener {

	private Main plugin;
	
	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		Arena a = plugin.getA();
		
		if (plugin.canRejoin(p.getName())) {
			plugin.sendMessage(p, "Succesfully rejoined this game!");
			for (Team t : Team.teamObjects) {
				if (t.getAllMembers().contains(p.getName()))
					t.addPlayer(p.getName());
			}
			// plugin.getSh().start(false);
			return;
		} else {
			plugin.sendMessage(p, "You joined a " + a.getArenaType().toString().toLowerCase() + " game.");
			p.getInventory().clear();
			p.getInventory().setArmorContents(new ItemStack[p.getInventory().getArmorContents().length]);
			p.getEnderChest().clear();
		}

		if ((a.getGameState() == GameState.INGAME || a.getGameState() == GameState.DEATHMATCH 
				|| a.getGameState() == GameState.ENDING || a.getGameState() == GameState.RESETING) && !plugin.canRejoin(p.getName())) {
			//e.disallow(Result.KICK_OTHER, "UHC is currently in progress!");
			p.kickPlayer("UHC is currently in progress!");
			return;
		}
		/*
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
		*/
		a.addPlayer(p);
		
		if (a.getGameState() == GameState.LOBBY || a.getGameState() == GameState.STARTING)
			p.teleport(plugin.getA().getLobby());
		
		// p.setScoreboard(plugin.getSh().getScoreboard(p));
		
		if (a.getPlayers().size() >= a.getNeededPlayers() && a.getGameState() == GameState.LOBBY) {
			
			a.setGameState(GameState.STARTING);
			
			new LobbyTimer(plugin, a);
		}

		if (a.getArenaType().isAutoTeaming())
			plugin.getTm().autoTeam(p, a.getArenaType());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0)
					plugin.getBms().updatePlayers(Bukkit.getOnlinePlayers().length, Bukkit.getOnlinePlayers()[0]);
			}
		}.runTask(plugin);
	}
}
