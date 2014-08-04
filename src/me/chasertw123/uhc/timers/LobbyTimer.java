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
		
		this.runTaskTimer(plugin, 20L, 20L);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {

		for (String s : a.getPlayers()) {
			if (Bukkit.getPlayerExact(s) == null)
				return;

			Player p = Bukkit.getPlayerExact(s);
			p.setLevel(time);

			BarAPI.setMessage(p, ChatColor.WHITE + "" + ChatColor.BOLD + "Ultra Hardcore starts in " + ChatColor.RED 
					+ "" + ChatColor.BOLD + time + ChatColor.WHITE + "" + ChatColor.BOLD + " seconds!");

			if (time == 10 || time == 30)
				if (plugin.getTm().getTeam(p) != null)
					if (plugin.getTm().getTeam(p).getMembers().size() < a.getArenaType().getPlayersPerTeam() / 2)
						plugin.sendMessage(p, "The game is about to start and your team has less then half of the maximum allowed players" +
								", this might be unfair for you, we recommend disbanding/leaving your team.");

			if (time <= 5) 
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		}

		if (time == 0) {

			for (String s : a.getPlayers()) {
				if (Bukkit.getPlayerExact(s) == null)
					return;

				Player p = Bukkit.getPlayerExact(s);

				if (BarAPI.hasBar(p))
					BarAPI.removeBar(p);

				p.setLevel(0);
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
			
			new GameTimer(plugin, a);

			this.cancel();
		}

		time--;
	}
}
