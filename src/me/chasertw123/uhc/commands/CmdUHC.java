package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdUHC implements CommandExecutor {

	private Main plugin;
	
	public CmdUHC(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			plugin.sendConsole("Only players can use UHC commands!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("Chat")) {
			
			if (args.length == 0) {
				
			}
			
			else if (args.length == 1) {
				
			}
			
			else {
				
				plugin.sendMessage(p, ChatColor.RED + "Invaild amount of arguments!");
				return true;
			}
		}
		
		else if (cmd.getName().equalsIgnoreCase("")) {
			
		}
		
		else if (cmd.getName().equalsIgnoreCase("")) {
			
		}
		
		else if (cmd.getName().equalsIgnoreCase("")) {
	
		}
		
		else if (cmd.getName().equalsIgnoreCase("")) {
	
		}
		
		return true;
	}
}
