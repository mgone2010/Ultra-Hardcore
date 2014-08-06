package me.chasertw123.uhc.timers;

import java.util.HashMap;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.teams.Team;

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

		if (time == 0 || Team.teamObjects.size() <= 1) {
			new EndingTimer(plugin);
			cancel();
		}else if (time % 60 == 0)
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, "Dead match ends in " + time + " seconds!");

		HashMap<String, Long> leaveTimes = plugin.getLt();

		for (String s : leaveTimes.keySet())
			if (leaveTimes.get(s) + 30000 > System.currentTimeMillis() && leaveTimes.get(s) != 0)
				continue;
			else {
				leaveTimes.remove(s);
				for (Team t : Team.teamObjects)
					t.removePlayer(s, true);
			}

		plugin.setLt(leaveTimes);

		time--;
	}

}
