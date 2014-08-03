package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCmd implements CommandExecutor {

	private Main plugin;
	
	public AdminCmd(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("Admin")) {
			
			if (!(sender instanceof Player)) {
				plugin.sendConsole("You must be a player to use that command!");
				return true;
			}

			Player p = (Player) sender;
			
			if (!p.hasPermission("uhc.admin")) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
				return true;
			}
			
			if (args.length == 0) {
				
				plugin.sendMessage(p, ChatColor.GREEN + "/admin setLobby - Set the lobby location");
				plugin.sendMessage(p, ChatColor.GREEN + "/admin setDeathmatch - Set the deathmatch location");
				
				return true;
			}
			
			else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("setLobby")) {
					plugin.getA().getConfig().set("arena.lobby", plugin.serializeLoc(p.getLocation()));
					plugin.getA().saveConfig();
					plugin.sendMessage(p, ChatColor.GREEN + "Set lobby location!");
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("setDeathmatch")) {
					plugin.getA().getConfig().set("arena.deathmatch", plugin.serializeLoc(p.getLocation()));
					plugin.getA().saveConfig();
					plugin.sendMessage(p, ChatColor.GREEN + "Set deathmatch location!");					
					return true;
				}
				
				else {
					
					plugin.sendMessage(p, ChatColor.GOLD + args[0] + ChatColor.RED + " is an unknown argument!");
					return true;
				}
			}
			
			else {
				
				plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
				return true;
			}
		}
		
		return true;
	}
}
