package me.chasertw123.uhc.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.chasertw123.uhc.Main;

public class Arena {

	private Main plugin;
	
	private ArrayList<String> players = new ArrayList<String>();
	private GameState gameState = GameState.DISABLED;
	private int neededPlayers = 15, maxPlayers = 40, donatorSlots = 10;
	private Location Deathmatch = null, Lobby = null;
	
	private File file;
	private FileConfiguration config;
	private ArenaType type;
	public enum GameState{ LOBBY, STARTING, INGAME, DEATHMATCH, ENDING, RESETING, DISABLED; }
	
	public Arena() {
		
		this.tryCreateFile();
		
		if (!config.contains("arena.lobby") || !config.contains("arena.deathmatch"))
			return;
		
		this.Lobby = plugin.deserializeLoc(config.getString("arena.lobby"));
		this.Deathmatch = plugin.deserializeLoc(config.getString("arena.deathmatch"));
		
		if (this.Deathmatch == null || this.Lobby == null)
			return;
		
		plugin.getMultiverseCore().getMVWorldManager().addWorld("UHC_world", Environment.NORMAL,
				this.generateRandomSeed(20), WorldType.NORMAL, true, "");
		plugin.getMultiverseCore().getMVWorldManager().addWorld("UHC_world_nether", Environment.NETHER,
				this.generateRandomSeed(20), WorldType.NORMAL, true, "");
		
		ArrayList<ArenaType> arenaTypes = new ArrayList<ArenaType>();
		for (ArenaType at : ArenaType.values())
			arenaTypes.add(at);
		
		this.type = arenaTypes.get(new Random().nextInt(arenaTypes.size()));
		
		this.setGameState(GameState.LOBBY);
	}
	
	public void setLobby(Location l) {
		config.set("arena.lobby", plugin.serializeLoc(l));
		this.saveConfig();
		
		this.setLobby(l);
	}
	
	public boolean hasLobby() {
		if (config.contains("arena.lobby"))
			return true;
		
		return false;
	}
	
	public void setDeathmatch(Location l) {
		config.set("arena.deathmatch", plugin.serializeLoc(l));
		this.saveConfig();
		
		this.setDeathmatch(l);
	}
	
	public boolean hasDeathmatch() {
		if (config.contains("arena.deathmatch"))
			return true;
		
		return false;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

	private void tryCreateFile() {

		file = new File(plugin.getDataFolder(), "arena.yml");

		if (!file.exists())
			try {
				plugin.sendConsole("Creating arena.yml...");
				file.createNewFile();
			} catch (Exception e) {
				plugin.sendConsole("Creating arena.yml has failed!");
				e.printStackTrace();
			}

		config = YamlConfiguration.loadConfiguration(file);
	}

	public void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.sendConsole("Saving arena.yml has failed!");
			e.printStackTrace();
		}
	}
	
	private String generateRandomSeed(int size) {
		
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		
		StringBuilder sb = new StringBuilder(size);
		
		for (int i = 0; i < size; i++)
			sb.append(AB.charAt(random.nextInt(AB.length())));
		
		return sb.toString();
	}
	
	public boolean addPlayer(Player p) {
		return players.add(p.getName());
	}
	
	public boolean removePlayer(Player p) {
		return players.remove(p.getName());
	}
	
	public int getNeededPlayers() {
		return neededPlayers;
	}
	
	public Player[] getPlayers() {
		return players.toArray(new Player[players.size()]);
	}
	
	public boolean hasPlayer(Player p) {
		return players.contains(p.getName());
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Location getDeathmatch() {
		return Deathmatch;
	}

	public Location getLobby() {
		return Lobby;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getDonatorSlots() {
		return donatorSlots;
	}
	
	public ArenaType getArenaType() {
		return type;
	}
}
