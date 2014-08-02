package me.chasertw123.uhc.timers;

import java.util.Random;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.ArenaType;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	private boolean sixty = true, thirty = true, ten = true, five = true, one = true; 

	private int time;
	private boolean shortendDeadmatch = false;
	private long shortendDeadmatchStart = 0;

	public GameTimer(Main plugin, Arena a) {
		this.plugin = plugin;
		this.a = a;

		Random r = new Random();
		int i = r.nextInt(3);

		switch (i) {
		case 0:
			time = 60;
			sixty = false;
			break;

		case 1:
			time = 75;
			break;

		case 2:
			time = 90;
			break;

		default:
			time = a.getArenaType() == ArenaType.SOLO ? 60 : 90;
			break;

		}

		for (Player p : Bukkit.getOnlinePlayers())
			plugin.sendMessage(p, "This game has " + time + " minutes till deadmatch.");
		this.runTaskTimer(this.plugin, 200L, 200L);
	}

	@Override
	public void run() {
		long timeLeft = (shortendDeadmatch ? 60000 : time * 60000) - (System.currentTimeMillis() - (shortendDeadmatch ? shortendDeadmatchStart : a.getStartTime()));

		if (Team.teamObjects.size() >= 3 && timeLeft / 1000 >= time * 45D && timeLeft / 1000 >= 60) { 
			// timeLeft is in ms, we make it in sec, time is in minutes, we do it * 60 * .75 (shortend, * 45)
			shortendDeadmatch = true;
			shortendDeadmatchStart = System.currentTimeMillis();
			sixty = false;
			thirty = false;
			ten = false;
			five = false;
			one = false;
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				plugin.sendMessage(p, ChatColor.RED + "Deathmatch time has been shortend to 1 minute!");
				plugin.sendMessage(p, ChatColor.GOLD + "1" + ChatColor.RED + " minute remaining till deathmatch! Prepare yourself for final battle!");
			}
		}
		
		if (timeLeft <= 0) {
			new DeathmatchTimer(plugin);
			this.cancel();

		} else if (timeLeft / 1000 <= 60 * 60 && sixty) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "60" + ChatColor.RED + " minutes remaining till deathmatch!");
			sixty = false;

		} else if (timeLeft / 1000 <= 60 * 30 && thirty) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "30" + ChatColor.RED + " minutes remaining till deathmatch!");
			thirty = false;

		} else if (timeLeft / 1000 <= 60 * 10 && ten) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "10" + ChatColor.RED + " minutes remaining till deathmatch!");
			ten = false;

		} else if (timeLeft / 1000 <= 60 * 5 && five) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "5" + ChatColor.RED + " minutes remaining till deathmatch! Start to prepare your self!");
			five = false;

		} else if (timeLeft / 1000 <= 60 && one) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "1" + ChatColor.RED + " minute remaining till deathmatch! Prepare yourself for final battle!");
			one = false;

		} 
	}
}
