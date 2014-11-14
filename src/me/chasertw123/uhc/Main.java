package me.chasertw123.uhc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.handlers.ChatHandler;
import me.chasertw123.uhc.handlers.CommandHandler;
import me.chasertw123.uhc.handlers.ListenerHandler;
import me.chasertw123.uhc.handlers.RandomChestHandler;
import me.chasertw123.uhc.handlers.RecipeHandler;
import me.chasertw123.uhc.handlers.ScoreboardHandler;
import me.chasertw123.uhc.teams.TeamManager;
import me.chasertw123.uhc.utils.BungeecordMessangerSender;
import me.chasertw123.uhc.utils.ServerConnector;
import me.chasertw123.uhc.utils.SpreadPlayers;
import me.chasertw123.uhc.worldgen.WorldGenStarter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import com.onarandombox.MultiverseCore.MultiverseCore;
//import me.chasertw123.uhc.sql.SQL;
//import me.chasertw123.uhc.sql.SQLAPI;
//import me.chasertw123.uhc.sql.SQL;
//import me.chasertw123.uhc.sql.SQLAPI;

public class Main extends JavaPlugin {

	private HashMap<String, Long> leaveTimes = new HashMap<String, Long>();
	
	//private SQL sql = new SQL(this);
	//private SQLAPI api = new SQLAPI(sql);
	private ServerConnector sc = new ServerConnector(this);
	private ScoreboardHandler sh = new ScoreboardHandler(this);
	private TeamManager tm = new TeamManager(this);
	private SpreadPlayers sp = new SpreadPlayers(this);
	private BungeecordMessangerSender bms = new BungeecordMessangerSender(this);
	private ChatHandler ch = new ChatHandler();
	private RandomChestHandler rch = new RandomChestHandler();
	private Arena a;
	public Location[] locs = new Location[40];

	public void onEnable() {

		saveDefaultConfig();

		a = new Arena(this);

		new CommandHandler(this);
		new ListenerHandler(this);
		new RecipeHandler(this);

		new WorldGenStarter(this);

		List<Team> teams = new ArrayList<Team>();

		for (Team t : Bukkit.getScoreboardManager().getMainScoreboard().getTeams())
			teams.add(t);

		for (Team t : teams)
			t.unregister();
	}

	public void onDisable() {
		if (Bukkit.getWorld("UHC_world") != null)
			getMultiverseCore().getMVWorldManager().deleteWorld("UHC_world",
					true);
		if (Bukkit.getWorld("UHC_world_nether") != null)
			getMultiverseCore().getMVWorldManager().deleteWorld(
					"UHC_world_nether", true);
	}

	public MultiverseCore getMultiverseCore() {
		Plugin plugin = getServer().getPluginManager().getPlugin(
				"Multiverse-Core");

		if (plugin instanceof MultiverseCore) {
			return (MultiverseCore) plugin;
		}

		throw new RuntimeException("MultiVerse not found!");
	}

	public String serializeLoc(Location l) {
		return l.getWorld().getName() + ", " + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ", " + l.getYaw() + ", " + l.getPitch();
	}

	public Location deserializeLoc(String s) {
		String[] st = s.split(", ");
		return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), 
				Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
	}

	/************************************************
	 * 												*
	 *					Messages					*
	 * 												*
	 ************************************************/

	public String prefix = ChatColor.WHITE + "[" + ChatColor.AQUA + ""
			+ ChatColor.ITALIC + "UHC" + ChatColor.WHITE + "] ";

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
	/*
	public SQL getSql() {
		return sql;
	}

	public SQLAPI getAPI() {
		return api;
	}
	 */
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

	public String getServerName() {
		return getConfig().getString("server.current");
	}

	public BungeecordMessangerSender getBms() {
		return bms;
	}

	public ChatHandler getCh() {
		return ch;
	}

	public RandomChestHandler getRch() {
		return rch;
	}

	public void setLeaveTime(String name, Long time) {
		if (!leaveTimes.containsKey(name) && a.getGameState() == GameState.INGAME)
			leaveTimes.put(name, time);
	}

	public boolean canRejoin(String name) {
		if (leaveTimes.containsKey(name))
			if (leaveTimes.get(name) + 30000 > System.currentTimeMillis() && leaveTimes.get(name) != 0)
				return true;
			else
				return false;
		else
			return false;
	}

	public void setLt(HashMap<String, Long> leaveTimes2) {
		leaveTimes = leaveTimes2;
	}

	public HashMap<String, Long> getLt() {
		return leaveTimes;
	}
}
