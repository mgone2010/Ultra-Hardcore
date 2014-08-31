package me.chasertw123.uhc.worldgen;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

import me.chasertw123.uhc.Main;

import org.bukkit.Chunk;
import org.bukkit.World;

public class WorldGenStarter {
	private Queue<Chunk> chunks = new LinkedBlockingDeque<Chunk>();
	private ArrayList<String> seeds = new ArrayList<String>();
	private World w;

	public int seed = 0;

	public WorldGenStarter(final Main plugin) {

		try {
			File file = new File(plugin.getDataFolder(), "seeds.txt");
			file.createNewFile();

			FileInputStream fstream = new FileInputStream(file);
			DataInputStream datainputstream = new DataInputStream(fstream);
			BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(datainputstream));
			String line;

			while ((line = bufferdReader.readLine()) != null) 
				seeds.add(line);

			bufferdReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (seeds.size() == 0) {
			System.out.println("No seeds were loaded, using a random seed.");
			seeds.add("" + (new Random()).nextLong());
		}

		new CreateWorlds(plugin, this).runTask(plugin);
		new CreateChunks(plugin).runTaskLater(plugin, 20L);
	}


	public final String getRandomSeed() {
		Random r = new Random();
		seed = r.nextInt(seeds.size());
		return seeds.get(seed);
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
