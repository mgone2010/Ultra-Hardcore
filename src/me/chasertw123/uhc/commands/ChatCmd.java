package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCmd implements CommandExecutor {

	private Main plugin;
	
	public ChatCmd(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!cmd.getName().equalsIgnoreCase("Chat")) {
			
			if (!(sender instanceof Player)) {
				plugin.sendConsole("You must be a player to use that command!");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("uhc.chat")) {
				plugin.sendMessage(p, ChatColor.RED + "You do not have permission to do that!");
				return true;
			}
			
			if (args.length == 0) {
				
				// TODO: Swap chat channel
				
				return true;
			}
			
			else if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("g")) {
				
					// TODO: Set chat channel to global
					
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("t")) {
					
					// TODO: Set chat channel to team
					
					return true;
				}
				
				else if (args[0].equalsIgnoreCase("a")) {
					
					if (!p.hasPermission("uhc.chat.admin")) {
						plugin.sendMessage(p, ChatColor.RED + "You do not have permission to enter that chat channel!");
						return true;
					}
					
					// TODO: Set chat channel to admin
					
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
