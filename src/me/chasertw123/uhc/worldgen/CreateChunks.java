package me.chasertw123.uhc.worldgen;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.utils.SpreadPlayers;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CreateChunks extends BukkitRunnable{

	private Main plugin;

	public CreateChunks(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {				

		Integer maxXZ = SpreadPlayers.maxXZ;
		final World w = Bukkit.getWorld("UHC_world");
		plugin.locs = plugin.getSp().getSpreadLocations(w, 40, -maxXZ, -maxXZ, maxXZ, maxXZ); 
		Integer currentTaskDelay = 0;

		for (Location loc : plugin.locs) {
			final Chunk c = loc.getChunk();

			for (int xx = -5; xx <= 5; xx++)
				for (int zz = -5; zz <= 5; zz++) {
					currentTaskDelay++;
					
					final int x = xx;
					final int z = zz;
					
					new BukkitRunnable() {

						@Override
						public void run() {
							w.getChunkAt(c.getX() + x, c.getZ() + z).load(true);
							
							if (w.getLoadedChunks().length > 2000) {
								for (Chunk s : w.getLoadedChunks())
									s.unload(true, true);
								Bukkit.broadcastMessage("Over 2k chunks loaded, reduced to " + w.getLoadedChunks().length);
								System.gc();
							}
						}
					}.runTaskLater(plugin, currentTaskDelay);
				}
			
		}	
		System.out.println("/** Generating " + currentTaskDelay + " chunks spread over " + currentTaskDelay / 20 + " seconds **\\");
	}
}
