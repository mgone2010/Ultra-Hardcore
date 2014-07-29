package me.chasertw123.uhc.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.chasertw123.uhc.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SQL {

	private Connection c;
	private Main plugin;

	public SQL(Main plugin) {
		this.plugin = plugin;
		
		this.openConnection();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			Statement stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS uhc "
					+ "(username VARCHAR(16) not NULL, " + " kills INT(64), "
					+ " deaths INT(64), " + " points INT(64), "
					+ " apples_ate INT(64), " + " games_played INT(64), " 
					+ " games_won INT(64), " + " PRIMARY KEY ( username ))";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
	}
	
	public synchronized void openConnection() {

		FileConfiguration config = plugin.getConfig();

		try {
			c = DriverManager.getConnection(
					"jdbc:mysql://" + config.getString("sql.ip") + ":" + config.getString("sql.port") + "/"
							+ config.getString("sql.database"), config.getString("sql.username"), config.getString("sql.password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void closeConnection() {

		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized boolean containsPlayer(Player p) {

		try {
			
			PreparedStatement sql = c.prepareStatement("SELECT * FROM `uhc` WHERE username=?;");
			sql.setString(1, p.getName());

			ResultSet result = sql.executeQuery();
			boolean hasPlayer = result.next();

			sql.close();
			result.close();

			return hasPlayer;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Connection getC() {
		return c;
	}
}
