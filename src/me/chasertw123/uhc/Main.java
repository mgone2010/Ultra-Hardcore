package me.chasertw123.uhc;

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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;

public class Main extends JavaPlugin {

	private SQL sql = new SQL(this);
	private SQLAPI api = new SQLAPI(sql);
	private ServerConnector sc = new ServerConnector(this);
	private ScoreboardHandler sh = new ScoreboardHandler(this);
	private TeamManager tm =  new TeamManager(this);
	private SpreadPlayers sp =  new SpreadPlayers(this);
	private Arena a;
	
	public void onEnable() {
		
		a = new Arena();
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		new CommandHandler(this);
		new ListenerHandler(this);
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
}
