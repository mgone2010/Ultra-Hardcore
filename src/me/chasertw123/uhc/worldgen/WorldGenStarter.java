package me.chasertw123.uhc.worldgen;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

import me.chasertw123.uhc.Main;

import org.bukkit.Chunk;
import org.bukkit.World;

public class WorldGenStarter {
	private Queue<Chunk> chunks = new LinkedBlockingDeque<Chunk>();
	private World w;

	public WorldGenStarter(final Main plugin) {

		new CreateWorlds(plugin, this).runTask(plugin);
		new CreateChunks(plugin).runTaskLater(plugin, 20L);
	}


	public final String generateRandomSeed(int size) {

		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();

		StringBuilder sb = new StringBuilder(size);

		for (int i = 0; i < size; i++)
			sb.append(AB.charAt(random.nextInt(AB.length())));

		return sb.toString();
	}


	public Queue<Chunk> getChunks() {
		return chunks;
	}

	public World getWorld() {
		return w;
	}

	public void setWorld(World world) {
		w = world;
	}
}
