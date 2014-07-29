package me.chasertw123.uhc.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SQLAPI {

	private SQL sql;
	
	public SQLAPI(SQL sql) {
		this.sql = sql;
	}
	
	public int getStat(StatType stat, Player p) {
		
		int i = 0;
		
		sql.openConnection();
		
		if (!sql.containsPlayer(p))
			return 0;

		try {
			
			PreparedStatement current = sql.getC().prepareStatement(
					"SELECT " + stat.getSqlName() + " FROM `uhc` WHERE username=?;");

			current.setString(1, p.getName());

			ResultSet result = current.executeQuery();
			result.next();

			i = result.getInt(stat.getSqlName());

			current.close();
			result.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			sql.closeConnection();
		}
		
		return i;
	}
	
	public void incrementStat(StatType stat, Player p, int i) {
		
		sql.openConnection();
		
		if (!sql.containsPlayer(p))
			return;
		
		try {
			
			PreparedStatement stmt = sql.getC().prepareStatement(
					"UPDATE `uhc` SET " + stat.getSqlName() + "=? WHERE username=?");
			
			stmt.setInt(1, this.getStat(stat, p) + i);
			stmt.setString(2, p.getName());
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeConnection();
		}
	}
	
	public void decrementStat(StatType stat, Player p, int i) {

		sql.openConnection();
		
		if (!sql.containsPlayer(p))
			return;
		
		try {
			
			PreparedStatement stmt = sql.getC().prepareStatement(
					"UPDATE `uhc` SET " + stat.getSqlName() + "=? WHERE username=?");
			
			stmt.setInt(1, this.getStat(stat, p) - i);
			stmt.setString(2, p.getName());
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeConnection();
		}
	}

	public void setStat(StatType stat, Player p, int i) {

		sql.openConnection();
		
		if (!sql.containsPlayer(p))
			return;
		
		try {
			
			PreparedStatement stmt = sql.getC().prepareStatement(
					"UPDATE `uhc` SET " + stat.getSqlName() + "=? WHERE username=?");
			
			stmt.setInt(1, i);
			stmt.setString(2, p.getName());
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeConnection();
		}
	}
	
	@SuppressWarnings("deprecation")
	public OfflinePlayer getTopStat(StatType stat) {
		
		OfflinePlayer p = null;
		
		sql.openConnection();
		
		try {
			
			PreparedStatement stmt = sql.getC().prepareStatement("SELECT username,MAX(" 
					+ stat.getSqlName() + ") as max_kills FROM `uhc`;");
			
			ResultSet result = stmt.executeQuery();
			result.next();
			
			p = Bukkit.getOfflinePlayer(result.getString(stat.getSqlName()));
			
			stmt.close();
			result.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			sql.closeConnection();
		}
		
		return p;
	}
	
	public enum StatType { 
		
		KILLS("kills"), 
		DEATHS("deaths"), 
		POINTS("points"), 
		APPLES_ATE("apples_ate"), 
		GAMES_PLAYED("games_played"), 
		GAMES_WON("games_won"); 
		
		private String sqlName;
		
		private StatType(String sqlName) {
			this.sqlName = sqlName;
		}

		public String getSqlName() {
			return sqlName;
		}
	}
}
