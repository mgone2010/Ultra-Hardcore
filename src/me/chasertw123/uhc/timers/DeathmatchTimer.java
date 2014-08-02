package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathmatchTimer extends BukkitRunnable{

	private Main plugin;
	private int time = 300;

	public DeathmatchTimer(Main plugin) {
		this.plugin = plugin;

		this.runTaskTimer(this.plugin, 20L, 20L);
	}

	@Override
	public void run() {

		if (time == 0)
			new EndingTimer(plugin);
		else if (time % 60 == 0)
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, "Dead match ends in " + time + " seconds!");

		time--;
	}

}
