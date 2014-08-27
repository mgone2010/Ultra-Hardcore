package me.chasertw123.uhc.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import me.chasertw123.uhc.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Arena {

	private Main plugin;

	private ArrayList<String> players = new ArrayList<String>();
	private GameState gameState = GameState.DISABLED;
	// private int neededPlayers = 15, maxPlayers = 40, donatorSlots = 10;
	private int neededPlayers = 2, maxPlayers = 40, donatorSlots = 10; // TODO: Debug only
	private long startTime = 0;
	private Location Lobby = null;

	private File file;
	private FileConfiguration config;
	private ArenaType type;
	public enum GameState{ LOBBY, STARTING, INGAME, DEATHMATCH, ENDING, RESETING, DISABLED; }

	public Arena(Main plugin) {

		this.plugin = plugin;
		this.tryCreateFile();

		ArrayList<ArenaType> arenaTypes = new ArrayList<ArenaType>();
		for (ArenaType at : ArenaType.values())
			arenaTypes.add(at);

		this.type = arenaTypes.get(new Random().nextInt(arenaTypes.size()));
		this.type = ArenaType.SOLO; // TODO: Debug only

		if (!config.contains("arena.lobby"))
			return;

		this.Lobby = plugin.deserializeLoc(config.getString("arena.lobby"));

		if (this.Lobby == null)
			return;

		this.setGameState(GameState.RESETING);
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

	public boolean addPlayer(Player p) {
		return players.add(p.getName());
	}

	public boolean removePlayer(Player p) {
		return players.remove(p.getName());
	}

	public int getNeededPlayers() {
		return neededPlayers;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public boolean hasPlayer(Player p) {
		return players.contains(p.getName());
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		for (Player p : Bukkit.getOnlinePlayers()) {
			plugin.getBms().updateState(gameState, p);
			break;
		}
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

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long time) {
		startTime = time;
	}
}
