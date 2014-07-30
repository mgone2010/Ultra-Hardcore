package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCmd implements CommandExecutor {

	private Main plugin;
	
	public LeaveCmd(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("Leave")) {
			
			if (!(sender instanceof Player)) {
				plugin.sendConsole("You must me a player to use that command!");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("uhc.leave")) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
				return true;
			}
			
			if (args.length > 0) {
				plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
				return true;
			}
			
			// TODO: Kick player out of the game
			
			return true;
		}
		
		return true;
	}
}
