package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	
	private int time = 180;
	
	public LobbyTimer(Main plugin, Arena a) {
		this.plugin = plugin;
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
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200 , 4));
				
				if (plugin.getTm().getTeam(p) == null)
					plugin.getTm().autoTeam(p, a.getArenaType());
			}
			
			plugin.getSh().start();
			plugin.getSp().spreadPlayers(a, plugin.getLocs(), Bukkit.getWorld("UHC_world"));
			// Give Items
			// Freeze Them
			a.setStartTime(System.currentTimeMillis());
			a.setGameState(GameState.INGAME);
			
			this.cancel();
		}
		
		time--;
	}
}
