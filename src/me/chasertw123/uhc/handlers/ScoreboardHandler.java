package me.chasertw123.uhc.handlers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.sql.SQLAPI.StatType;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHandler {

	private Main plugin;
	
	public ScoreboardHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	// Run this on start of the arena
	public void start() {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("Arena", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR); // Clear sidebar.
		
		for (Team t : Team.teamObjects) {
			org.bukkit.scoreboard.Team team = board.registerNewTeam(t.getName());
			
			for (String s : t.getMembers())
				team.addPlayer(Bukkit.getOfflinePlayer(s));
			
			team.setAllowFriendlyFire(false);
			team.setCanSeeFriendlyInvisibles(true);
			team.setPrefix(t.getPrefix());
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) 
			p.setScoreboard(board);
	}
	
	public Scoreboard getScoreboard(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective(p.getName(), "dummy");
		
		obj.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Ultra Harcore Stats");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		String pre = ChatColor.GREEN + "" + ChatColor.BOLD;
		String scorepre = ChatColor.WHITE + "";

		int kills = plugin.getAPI().getStat(StatType.KILLS, p), deaths = plugin.getAPI().getStat(StatType.DEATHS, p);
		
		obj.getScore(ChatColor.AQUA + "").setScore(10);
		obj.getScore(pre + "UHC Points").setScore(9);
		obj.getScore(this.first16Chars(ChatColor.DARK_PURPLE + scorepre + plugin.getAPI().getStat(StatType.POINTS, p))).setScore(8);
		obj.getScore(pre + "Kills").setScore(7);
		obj.getScore(this.first16Chars(ChatColor.BLACK + scorepre + kills)).setScore(6);
		obj.getScore(pre + "Deaths").setScore(5);
		obj.getScore(this.first16Chars(ChatColor.BLUE + scorepre + deaths)).setScore(4);
		obj.getScore(pre + "KDR").setScore(3);
		obj.getScore(this.first16Chars(ChatColor.DARK_AQUA + scorepre + Math.round(kills / Math.max(1D, deaths) * 100D) / 100D)).setScore(2);
		obj.getScore(pre + "Games Won").setScore(1);
		obj.getScore(this.first16Chars(ChatColor.DARK_GRAY + scorepre + plugin.getAPI().getStat(StatType.GAMES_WON, p))).setScore(0);

		
		return board;
	}
	
	private String first16Chars(String s) {
		// Used to prevent numbers that are higher then 16 chars (including color codes)
		return s.substring(0, Math.min(s.length(), 16));
	}
}
