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

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					plugin.getBms().updatePlayers(Bukkit.getOnlinePlayers().length, p);
					plugin.getBms().updateState(plugin.getA().getGameState(), p);
				}
			}
		}.runTask(plugin);

		Player p = e.getPlayer();

		Arena a = plugin.getA();

		if (plugin.canRejoin(p.getName())) {
			plugin.sendMessage(p, "Succesfully rejoined this game!");
			for (Team t : Team.teamObjects) {
				if (t.getAllMembers().contains(p.getName()))
					t.addPlayer(p.getName());
			}
			return;
		} else {
			plugin.sendMessage(p, "You joined a " + a.getArenaType().toString().toLowerCase() + " game.");
			p.getInventory().clear();
			p.getInventory().setArmorContents(new ItemStack[p.getInventory().getArmorContents().length]);
			p.getEnderChest().clear();
		}

		if (a.getGameState() == GameState.RESETING)
			p.kickPlayer("Game is starting up. Please wait a minute.");
		
		if ((a.getGameState() == GameState.INGAME || a.getGameState() == GameState.DEATHMATCH 
				|| a.getGameState() == GameState.ENDING) && !plugin.canRejoin(p.getName())) {
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

		if (a.getGameState() == GameState.LOBBY || a.getGameState() == GameState.STARTING) {
			p.teleport(plugin.getA().getLobby());

			// p.setScoreboard(plugin.getSh().getScoreboard(p));
		}

		if (a.getPlayers().size() >= a.getNeededPlayers() && a.getGameState() == GameState.LOBBY) {
			
			for (Player pl : Bukkit.getOnlinePlayers())
				plugin.sendMessage(pl, "Game is starting in 180 seconds");

			a.setGameState(GameState.STARTING);

			new LobbyTimer(plugin, a);
		} else if (a.getPlayers().size() < a.getNeededPlayers() && a.getGameState() == GameState.LOBBY)
			for (Player pl : Bukkit.getOnlinePlayers())
				plugin.sendMessage(pl, "Game will start when " + (a.getNeededPlayers() - a.getPlayers().size()) + " more players have joined");

		if (a.getArenaType().isAutoTeaming())
			plugin.getTm().autoTeam(p, a.getArenaType());
	}
}
