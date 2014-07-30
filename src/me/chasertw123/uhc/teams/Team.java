package me.chasertw123.uhc.teams;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Team {

	private String creator;
	private ArrayList<String> members;
	private String teamName;
	private String prefix;
	public static ArrayList<Team> teamObjects = new ArrayList<Team>();
	private org.bukkit.scoreboard.Team team = null;
	@SuppressWarnings("serial")
	public static ArrayList<String> teamColors = new ArrayList<String>(){{
		// 10 normal colors, nothing special
		add(ChatColor.GREEN + ""); add(ChatColor.AQUA + "");
		add(ChatColor.RED + ""); add(ChatColor.BLUE + ""); add(ChatColor.DARK_AQUA + ""); add(ChatColor.DARK_GREEN + ""); 
		add(ChatColor.GOLD + ""); add(ChatColor.GRAY + ""); add(ChatColor.LIGHT_PURPLE + ""); add(ChatColor.WHITE + "");

		// 10 normal colors in bold
		add(ChatColor.GREEN + "" + ChatColor.BOLD); add(ChatColor.AQUA + "" + ChatColor.BOLD); add(ChatColor.RED + "" + ChatColor.BOLD); 
		add(ChatColor.BLUE + "" + ChatColor.BOLD); add(ChatColor.DARK_AQUA + "" + ChatColor.BOLD); add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD); 
		add(ChatColor.GOLD + "" + ChatColor.BOLD); add(ChatColor.GRAY + "" + ChatColor.BOLD); add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD);
		add(ChatColor.WHITE + "" + ChatColor.BOLD);

		// 10 normal colors in italic
		add(ChatColor.GREEN + "" + ChatColor.ITALIC); add(ChatColor.AQUA + "" + ChatColor.ITALIC); add(ChatColor.RED + "" + ChatColor.ITALIC); 
		add(ChatColor.BLUE + "" + ChatColor.ITALIC); add(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC); add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC); 
		add(ChatColor.GOLD + "" + ChatColor.ITALIC); add(ChatColor.GRAY + "" + ChatColor.ITALIC); add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC); 
		add(ChatColor.WHITE + "" + ChatColor.ITALIC);

		// Last 10 of the 40 total slots, these *should* only be given to donators (in a Solo arena)
		// 10 normal colors in italic bold
		add(ChatColor.GREEN + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); add(ChatColor.AQUA + "" + ChatColor.ITALIC + "" + ChatColor.BOLD);
		add(ChatColor.RED + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); add(ChatColor.BLUE + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); 
		add(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); add(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); 
		add(ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); add(ChatColor.GRAY + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); 
		add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "" + ChatColor.BOLD); add(ChatColor.WHITE + "" + ChatColor.ITALIC + "" + ChatColor.BOLD);
	}};

	@SuppressWarnings("unchecked")
	public Team(String creator, String teamName) {
		this.creator = creator;
		this.members.add(creator);
		this.teamName = teamName;

		ArrayList<String> prefixes = (ArrayList<String>) teamColors.clone();

		for (Team t : teamObjects)
			prefixes.remove(t.getPrefix());

		prefix = prefixes.get(0);
		teamObjects.add(this);
	}
	
	@SuppressWarnings("deprecation")
	public void sendMessage(String message) {
		for (String s : members) 
			if (Bukkit.getPlayerExact(s) != null)
				Bukkit.getPlayerExact(s).sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "" + ChatColor.ITALIC + "UHC" + ChatColor.WHITE + "] " + message);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void addPlayer(String m) {
		sendMessage(m + " joined your team.");
		members.add(m);
	}

	public String getName() {
		return teamName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void removePlayer(String name) {
		members.remove(name);
		sendMessage(name + " left your team.");
		if (this.team != null)
			team.removePlayer(Bukkit.getOfflinePlayer(name));
	}
	
	public void setTeam(org.bukkit.scoreboard.Team t) {
		this.team = t;
	}
}
