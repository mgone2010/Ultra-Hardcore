package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FrozenTimer extends BukkitRunnable {

	private Main plugin;
	private Arena a;
	
	private int time = 10;
	
	public FrozenTimer(Main plugin, Arena a) {
		this.plugin = plugin;
		this.a = a;
		
		for (String s : a.getPlayers())
			if (Bukkit.getPlayer(s) != null) {
				Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128));
				Bukkit.getPlayer(s).setWalkSpeed(0.0F);;
			}
		this.runTaskTimer(plugin, 20L, 20L);
	}
	
	@Override
	public void run() {
		
		time--;
		
		if (time <= 0) {
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				plugin.sendMessage(p, ChatColor.WHITE + "The frozen effect seems to fade!");
				p.setWalkSpeed(0.2F);
				p.removePotionEffect(PotionEffectType.JUMP);
			}
			
			a.setStartTime(System.currentTimeMillis());
			a.setGameState(GameState.INGAME);

			new GameTimer(plugin, a);
			
			this.cancel();
		}
	}

	
}
