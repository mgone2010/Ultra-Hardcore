package me.chasertw123.uhc.timers;

import java.util.Random;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.ArenaType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	private boolean sixty = true, thirty = true, ten = true, five = true, one = true; 

	private int time;

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

		if (a.getStartTime() + (time * 60 * 1000) >= System.currentTimeMillis()) {
			new DeathmatchTimer(plugin);
			this.cancel();

		} else if (a.getStartTime() + (60 * 60000) >= System.currentTimeMillis() && sixty) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "60" + ChatColor.RED + " minutes remaining till deathmatch!");
			sixty = false;

		} else if (a.getStartTime() + (60 * 30000) >= System.currentTimeMillis() && thirty) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "30" + ChatColor.RED + " minutes remaining till deathmatch!");
			thirty = false;

		} else if (a.getStartTime() + (60 * 10000) >= System.currentTimeMillis() && ten) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "10" + ChatColor.RED + " minutes remaining till deathmatch!");
			ten = false;

		} else if (a.getStartTime() + (60 * 5000) >= System.currentTimeMillis() && five) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "5" + ChatColor.RED + " minutes remaining till deathmatch! Start to prepare your self!");
			five = false;

		} else if (a.getStartTime() + (60 * 1000) >= System.currentTimeMillis() && one) {
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.GOLD + "1" + ChatColor.RED + " minute remaining till deathmatch! Prepare yourself for final battle!");
			one = false;

		} 
	}
}
