package me.chasertw123.uhc.timers;

import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;

public class GameTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	
	private int time;
	
	public GameTimer(Main plugin, Arena a) {
		this.plugin = plugin;
		this.a = a;
		
		Random r = new Random();
		int i = r.nextInt(1);
		
		switch (i) {
		case 0:
			time = 60 *
			break;

		default:
			break;
		}
		
		this.runTaskTimer(this.plugin, 20L, 20L);
	}

	@Override
	public void run() {
		
		// Stuff here...
		
		time--;
	}
}
