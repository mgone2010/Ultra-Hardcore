package me.chasertw123.uhc.teams;

import java.util.ArrayList;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.ArenaType;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.entity.Player;

public class TeamManager {

	private Main plugin;

	public TeamManager(Main plugin) {
		this.plugin = plugin;
	}

	public void addPlayer(Player p, Team t) {
		t.addPlayer(p.getName());
	}

	public Team createTeam(Player p, String teamName) {
		return new Team(p.getName(), teamName);
	}

	public void createTeam(Player p) {
		createTeam(p, p.getName() + "'s");
	}

	public Team getTeam(Player p) {
		for (Team t : Team.teamObjects) {
			if (t.getCreator() == p.getName())
				return t;
			else if (t.getMembers().contains(p.getName()))
				return t;
		}

		return null;
	}

	public void autoTeam(Player p, ArenaType at) {
		for (Team t : Team.teamObjects)
			if (t.getMembers().size() < at.getPlayersPerTeam()) {
				t.addPlayer(p.getName());
				plugin.sendMessage(p, "You got automatically put in the " + t.getName() + " team.");
				return;
			}

		plugin.sendMessage(p, "You got automatically in a new team.");
		plugin.getTm().createTeam(p);
	}

	@SuppressWarnings("unchecked")
	public void removePlayer(Player player) {
		for (Team t : (ArrayList<Team>) Team.teamObjects.clone()) {
			if (t.getMembers().contains(player.getName())) 
				t.removePlayer(player.getName(), plugin.getA().getGameState() == GameState.LOBBY 
				|| plugin.getA().getGameState() == GameState.STARTING);
			t.removeInvite(player.getName());
		}
	}
}
