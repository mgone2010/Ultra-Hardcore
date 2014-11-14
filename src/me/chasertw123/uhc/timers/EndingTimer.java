package me.chasertw123.uhc.timers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingTimer extends BukkitRunnable{

	private Main plugin;

	private int time = 10;

	public EndingTimer(Main plugin) {
		this.plugin = plugin;
		if (Team.teamObjects.size() > 0)
			for (Player p : Bukkit.getOnlinePlayers()) {
				plugin.sendMessage(p, Team.teamObjects.get(0).getName() + " team won!");
				p.setHealth(p.getMaxHealth());
				p.setAllowFlight(true);
			}

		this.runTaskTimer(this.plugin, 20L, 20L);
	}

	@Override
	public void run() {

		if (time == 0 || Bukkit.getOnlinePlayers().length == 0) {
			
			plugin.getA().setGameState(GameState.RESETING);
			
			for (Player p : Bukkit.getOnlinePlayers())
				p.kickPlayer(ChatColor.RED + "Server is now restarting \n Thanks for playing");
				
			plugin.getMultiverseCore().deleteWorld("UHC_world");	
			plugin.getMultiverseCore().deleteWorld("UHC_world_nether");
			
			Bukkit.getServer().shutdown();
		} 
		
		else if (time % 10 == 0) 
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.RED + "Server restarting in " + ChatColor.GOLD + time + ChatColor.RED  + " seconds!");
		
		else if (time <= 3)
			for (Player p : Bukkit.getOnlinePlayers())
				plugin.sendMessage(p, ChatColor.RED + "Server restarting in " + ChatColor.GOLD + time + ChatColor.RED  + " seconds!");
			
		
		time--;
	}
}
