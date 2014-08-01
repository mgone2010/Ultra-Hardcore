package me.chasertw123.uhc;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.handlers.CommandHandler;
import me.chasertw123.uhc.handlers.ListenerHandler;
import me.chasertw123.uhc.handlers.ScoreboardHandler;
import me.chasertw123.uhc.sql.SQL;
import me.chasertw123.uhc.sql.SQLAPI;
import me.chasertw123.uhc.teams.TeamManager;
import me.chasertw123.uhc.utils.ServerConnector;
import me.chasertw123.uhc.utils.SpreadPlayers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.onarandombox.MultiverseCore.MultiverseCore;

public class Main extends JavaPlugin {

	private SQL sql = new SQL(this);
	private SQLAPI api = new SQLAPI(sql);
	private ServerConnector sc = new ServerConnector(this);
	private ScoreboardHandler sh = new ScoreboardHandler(this);
	private TeamManager tm =  new TeamManager(this);
	private SpreadPlayers sp =  new SpreadPlayers(this);
	private Arena a;
	private Location[] locs = new Location[40];

	public void onEnable() {

		a = new Arena();

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		new CommandHandler(this);
		new ListenerHandler(this);

		Integer maxXZ = SpreadPlayers.maxXZ;
		final World w = Bukkit.getWorld("UHC_world");
		locs = sp.getSpreadLocations(w, 40, -maxXZ, -maxXZ, maxXZ, maxXZ); 

		final Queue<Chunk> chunks = new LinkedBlockingDeque<Chunk>();

		for (Location loc : locs) {
			Chunk c = loc.getChunk();

			for (int x = -9; x <= 9; x++)
				for (int z = -9; z <= 9; z++) {

					Chunk nc = w.getChunkAt(c.getX() + x, c.getZ() + z);
					boolean shouldAdd = true;
					for (Chunk s : chunks)
						if (s.getX() == nc.getX() && s.getZ() == nc.getZ()) {
							shouldAdd = false;
							break;
						}

					if (shouldAdd)
						chunks.add(nc);
				}
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				if (chunks.isEmpty()) {
					for (Chunk s : w.getLoadedChunks())
						s.unload(true, true); // Clean up the tons of loaded chunks.
					cancel();
				}

				if (w.getLoadedChunks().length > 10000)
					for (Chunk s : w.getLoadedChunks())
						s.unload(true, true);

				chunks.poll().load(true);
			}
		}.runTaskTimer(this, 1, chunks.size() + 1);
	}

	public void onDisable() {

	}

	public MultiverseCore getMultiverseCore() {
		Plugin plugin = getServer().getPluginManager().getPlugin("MultiverseCore");

		if (plugin instanceof MultiverseCore) {
			return (MultiverseCore) plugin;
		}

		throw new RuntimeException("MultiVerse not found!");
	}

	public String serializeLoc(Location l){
		return l.getWorld().getName() + ", " + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getYaw() + ", " + l.getPitch();
	}

	public Location deserializeLoc(String s){
		String[] st = s.split(", ");
		return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), 
				Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
	}

	/************************************************
	 * 												*
	 *					Messages					*
	 * 												*
	 ************************************************/

	public String prefix = ChatColor.WHITE + "[" + ChatColor.AQUA + "" + ChatColor.ITALIC + "UHC" + ChatColor.WHITE + "] ";

	public void sendMessage(Player p, String message) {
		p.sendMessage(prefix + message); 
	}

	public void sendConsole(String message) {
		this.getLogger().info(message);
	}

	/************************************************
	 * 												*
	 *				Getters and Setters				*
	 * 												*
	 ************************************************/

	public SQL getSql() {
		return sql;
	}

	public SQLAPI getAPI() {
		return api;
	}

	public ServerConnector getSc() {
		return sc;
	}

	public ScoreboardHandler getSh() {
		return sh;
	}

	public Arena getA() {
		return a;
	}

	public TeamManager getTm() {
		return tm;
	}

	public SpreadPlayers getSp() {
		return sp;
	}

	public Location[] getLocs() {
		return locs;
	}
}
