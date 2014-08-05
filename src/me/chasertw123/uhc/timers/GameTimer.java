package me.chasertw123.uhc.timers;

import java.util.HashMap;
import java.util.Random;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.ArenaType;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	private boolean sixty = true, thirty = true, ten = true, five = true, one = true; 
	private HashMap<Long, ChestType> timeLeftsRandomChests = new HashMap<Long, ChestType>();
	private HashMap<Long, Location> chestLocation = new HashMap<Long, Location>();

	private enum ChestType { WAITING, WARNED, SPAWNED }

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

		int amountOfChests = r.nextInt(3) + 3; // 2 - 5 chests.
		for (int m = 0; m < amountOfChests; m++) {
			long randomTime = r.nextInt((int) ((time - 1) * 60)) + 10;
			System.out.println("chest at " + randomTime + " seconds left");
			timeLeftsRandomChests.put(randomTime, ChestType.WAITING);
			chestLocation.put(randomTime, getRandomLocation());
		}
		
		/* System.out.println("debug chest at " + (time - 2) * 60L);
		timeLeftsRandomChests.put((time - 2) * 60L, ChestType.WAITING);
		chestLocation.put((time - 2) * 60L, getRandomLocation()); */

		for (Player p : Bukkit.getOnlinePlayers())
			plugin.sendMessage(p, "This game has " + time + " minutes till deadmatch.");
		this.runTaskTimer(this.plugin, 100L, 100L); // Run timer every 5 seconds, uses more resources, but not much. Makes timings more precise
	}

	@Override
	public void run() {
		long timeLeft = (shortendDeadmatch ? 60000 : time * 60000) - (System.currentTimeMillis() - (shortendDeadmatch ? shortendDeadmatchStart : a.getStartTime()));

		if (Team.teamObjects.size() <= 3 && timeLeft / 1000 < time * 45D && timeLeft / 1000 >= 60) { 
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

		if (Team.teamObjects.size() <= 1) {
			new EndingTimer(plugin);
			this.cancel();
		}else if (timeLeft <= 0) {
			new DeathmatchTimer(plugin);
			for (Player p : Bukkit.getOnlinePlayers())
				p.teleport(a.getDeathmatch());
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

		if (shortendDeadmatch) 
			return;

		for (Long longvar : timeLeftsRandomChests.keySet()) {
			ChestType type = timeLeftsRandomChests.get(longvar);
			Location loc = chestLocation.get(longvar);
			// System.out.println(longvar + " : " + type.toString() + " Time left: " + timeLeft / 1000);
			if (timeLeft / 1000 <= longvar + 30 && type == ChestType.WAITING) {
				handleWarning(loc.getBlockX(), loc.getBlockZ());
				timeLeftsRandomChests.put(longvar, ChestType.WARNED);
			} 

			if (timeLeft / 1000 <= longvar && type == ChestType.WARNED) {
				spawnChest(loc.getBlockX(), loc.getBlockZ(), loc);
				timeLeftsRandomChests.put(longvar, ChestType.SPAWNED);
			} 
		}
	}

	private void handleWarning(Integer x, Integer z) {
		for (Player p : Bukkit.getOnlinePlayers())
			plugin.sendMessage(p, "Over 30 seconds a chest will spawn at X: " + x + " Z: " + z);
	}

	private void spawnChest(Integer x, Integer z, Location loc) {
		plugin.getRch().spawnChest(loc);

		for (Player p : Bukkit.getOnlinePlayers())
			plugin.sendMessage(p, "A chest spawned at X: " + x + " Z: " + z);
	}

	private Location getRandomLocation() {
		Random random = new Random(); 
		double x = random.nextDouble() * (999 + 999) - 999;
		double z = random.nextDouble() * (999 + 999) - 999;

		return new Location(Bukkit.getWorld("UHC_world"), x, 0, z);
	}
}
