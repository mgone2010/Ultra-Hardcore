package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCmd implements CommandExecutor {

	private Main plugin;
	
	public TeamCmd(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("Team")) {
			
			if (!(sender instanceof Player)) {
				plugin.sendConsole("You must be a player to use that command!");
				return true;
			}
			
			Player p = (Player) sender;
			
			/*
			 * You can make the permissions.
			 * 
			 * Try to make them like.
			 * 
			 * Example: uhc.team.create
			 */
			
			if (args.length == 0) {
				
			}
			
			else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("leave")) {
					
				}
				
				else if (args[0].equalsIgnoreCase("accept")) {
					
				}
				
				else if (args[0].equalsIgnoreCase("players")) {
					
				}
				
				else if (args[0].equalsIgnoreCase("disband")) {
					
				}
				
				else {
					
					plugin.sendMessage(p, ChatColor.GOLD + args[0] + ChatColor.RED + " is an unknown argument!");
					return true;
				}
			}
			
			else if (args.length == 2) {
				
				if (args[0].equalsIgnoreCase("disband")) {
					
				}
				
				else if (args[0].equalsIgnoreCase("create")) {
					
				}
				
				else if (args[0].equalsIgnoreCase("join")) {
					
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
