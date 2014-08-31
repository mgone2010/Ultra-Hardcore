package me.chasertw123.uhc.worldgen;

import me.chasertw123.uhc.Main;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.scheduler.BukkitRunnable;

public class CreateWorlds extends BukkitRunnable {

	private Main plugin;
	private WorldGenStarter wgc;

	public CreateWorlds(Main plugin, WorldGenStarter wgc) {
		this.plugin = plugin;
		this.wgc = wgc;
	}
	
	@Override
	public void run() {
		if (Bukkit.getWorld("UHC_world") != null)
			plugin.getMultiverseCore().getMVWorldManager().deleteWorld("UHC_world", true);
		if (Bukkit.getWorld("UHC_world_nether") != null)
			plugin.getMultiverseCore().getMVWorldManager().deleteWorld("UHC_world_nether", true);
		
		plugin.getMultiverseCore().getMVWorldManager().addWorld("UHC_world", Environment.NORMAL,
				wgc.getRandomSeed(), WorldType.NORMAL, true, null);
		plugin.getMultiverseCore().getMVWorldManager().addWorld("UHC_world_nether", Environment.NETHER,
				wgc.getRandomSeed(), WorldType.NORMAL, true, null);
	}
}
