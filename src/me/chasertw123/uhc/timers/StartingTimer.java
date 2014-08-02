package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;

import org.bukkit.scheduler.BukkitRunnable;

public class StartingTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	
	private int time = 60;
	
	public StartingTimer(Main plugin, Arena a) {
		this.plugin = plugin;
		this.a = a;
		
		this.runTaskTimer(this.plugin, 20L, 20L);
	}

	@Override
	public void run() {
		
		// Stuff here...
		
		time--;
	}
}
