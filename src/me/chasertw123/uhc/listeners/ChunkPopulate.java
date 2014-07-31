package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkPopulate implements Listener{
	
	public Main plugin;
	
	public ChunkPopulate(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChunkPopulate (ChunkPopulateEvent e) {
		final Chunk c = e.getChunk();
		if (!c.getWorld().getName().contains("UHC_world"))
			return;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				if(c.getWorld().getEnvironment() == Environment.NORMAL) {
					if (c.getX() == 62 || c.getZ() == 62) 
						for (int j = 0; j < 16; j++)
							for (int k = 0; k < 256; k++) {
								if (c.getX() == 62)
									c.getBlock(9, k, j).setType(Material.BEDROCK);
								if (c.getZ() == 62)
									c.getBlock(j, k, 9).setType(Material.BEDROCK);

							}

					if (c.getX() == -63 || c.getZ() == -63) 
						for (int j = 0; j < 16; j++)
							for (int k = 0; k < 256; k++) {
								if (c.getX() == -63)
									c.getBlock(6, k, j).setType(Material.BEDROCK);
								if (c.getZ() == -63)
									c.getBlock(j, k, 6).setType(Material.BEDROCK);
							}

				} else {
					if (c.getX() == 7 || c.getZ() == 7) 
						for (int j = 0; j < 16; j++)
							for (int k = 0; k < 256; k++) {
								if (c.getX() == 7)
									c.getBlock(14, k, j).setType(Material.BEDROCK);
								if (c.getZ() == 7)
									c.getBlock(j, k, 14).setType(Material.BEDROCK);
							}


					if (c.getX() == -8 || c.getZ() == -8) 
						for (int j = 0; j < 16; j++)
							for (int k = 0; k < 256; k++) {
								if (c.getX() == -8)
									c.getBlock(1, k, j).setType(Material.BEDROCK);
								if (c.getZ() == -8)
									c.getBlock(j, k, 1).setType(Material.BEDROCK);
							}
				}

			}
		}.runTask(plugin);

	}
}
