package me.chasertw123.uhc.worldgen;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.utils.SpreadPlayers;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CreateChunks extends BukkitRunnable {

	private Main plugin;

	public CreateChunks(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {

		Integer maxXZ = SpreadPlayers.maxXZ;
		final World w = Bukkit.getWorld("UHC_world");
		plugin.locs = plugin.getSp().getSpreadLocations(w, 40, -maxXZ, -maxXZ,
				maxXZ, maxXZ);
		Integer currentTaskDelay = 0;

		for (Location loc : plugin.locs) {
			final Chunk c = loc.getChunk();
			currentTaskDelay++;

			final int count = currentTaskDelay;
			new BukkitRunnable() {

				@Override
				public void run() {

					for (int xx = -5; xx <= 5; xx++)
						for (int zz = -5; zz <= 5; zz++) 
							w.getChunkAt(c.getX() + xx, c.getZ() + zz).load(true);

					System.out.println("* Generated batch " + count + " out of 40");
					if (count == 20) {
						System.err.println("! Cleaning loaded chunks from "
								+ w.getLoadedChunks().length);
						for (Chunk s : w.getLoadedChunks())
							s.unload(true, true);
						//System.gc();
						System.err.println("! Cleaned loaded chunks to "
								+ w.getLoadedChunks().length);
					}
				}
			}.runTaskLater(plugin, currentTaskDelay);
		}


		new BukkitRunnable() {

			@Override
			public void run() {
				System.err.println("! Cleaning loaded chunks from "
						+ w.getLoadedChunks().length);
				for (Chunk s : w.getLoadedChunks())
					s.unload(true, true);
				System.gc();
				System.err.println("! Cleaned loading chunks to "
						+ w.getLoadedChunks().length);
				plugin.getA().setGameState(GameState.LOBBY);
				System.out.println("\\** Generation done **/");
			}
		}.runTaskLater(plugin, currentTaskDelay + 1);

		System.out.println("/** Generating " + 4840 + " chunks spread over "
				+ currentTaskDelay + " batches **\\");
	}
}
