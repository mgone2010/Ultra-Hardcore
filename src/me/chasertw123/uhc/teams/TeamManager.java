package me.chasertw123.uhc.teams;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.ArenaType;

import org.bukkit.entity.Player;

public class TeamManager {

	private Main plugin;

	public TeamManager(Main plugin) {
		this.plugin = plugin;
	}

	public void addPlayer(Player p, Team t) {
		t.addPlayer(p.getName());
	}

	public void createTeam(Player p, String teamName) {
		new Team(p.getName(), teamName);
	}

	public void createTeam(Player p) {
		createTeam(p, p.getName());
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
				return;
			}

		plugin.getTm().createTeam(p);
	}
}
