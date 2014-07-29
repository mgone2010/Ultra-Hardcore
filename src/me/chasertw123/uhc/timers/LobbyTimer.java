package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.confuser.barapi.BarAPI;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTimer extends BukkitRunnable {

	//private Main plugin;
	private Arena a;
	
	private int time = 180;
	
	public LobbyTimer(Main plugin, Arena a) {
		//this.plugin = plugin;
		this.a = a;
	}

	@Override
	public void run() {
		
		for (Player p : a.getPlayers()) {
			
			BarAPI.setMessage(p, ChatColor.WHITE + "" + ChatColor.BOLD + "Ultra Hardcore starts in " + ChatColor.RED 
					+ "" + ChatColor.BOLD + time + ChatColor.WHITE + "" + ChatColor.BOLD + " seconds!");
			
			if (time <= 5)
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		}
		
		if (time == 0) {
			
			for (Player p : a.getPlayers()) {
				
				if (BarAPI.hasBar(p))
					BarAPI.removeBar(p);
				
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
			}
			
			// Teleport Players
			// Give Items
			// Freeze Them
			
			this.cancel();
		}
		
		time--;
	}
}
