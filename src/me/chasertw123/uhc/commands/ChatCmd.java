package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.handlers.ChatHandler.ChatType;

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

				ChatType oldType = plugin.getCh().getChatType(p);

				if (oldType == ChatType.GLOBAL) {
					if (plugin.getTm().getTeam(p) != null) {
						plugin.getCh().setChat(ChatType.TEAM, p);
						plugin.sendMessage(p, "You are now chatting to your teammates");
						return true;
					} else if (p.hasPermission("uhc.admin")) {
						plugin.getCh().setChat(ChatType.ADMIN, p);
						plugin.sendMessage(p, "You are now chatting to admins");
						return true;
					} else {
						plugin.sendMessage(p, ChatColor.RED + "Cannot switch channel, you don't have a team");
						return true;
					}
				} else if (oldType == ChatType.TEAM) {
					if (p.hasPermission("uhc.admin")) {
						plugin.getCh().setChat(ChatType.ADMIN, p);
						plugin.sendMessage(p, "You are now chatting to admins");
						return true;
					} else {
						plugin.getCh().setChat(ChatType.GLOBAL, p);
						plugin.sendMessage(p, "You are now chatting to everyone");
						return true;
					}
				} else { // == ADMIN
					plugin.getCh().setChat(ChatType.GLOBAL, p);
					plugin.sendMessage(p, "You are now chatting to everyone");
					return true;
				}
			}

			else if (args.length == 1) {

				if (args[0].equalsIgnoreCase("g")) {

					plugin.getCh().setChat(ChatType.GLOBAL, p);
					plugin.sendMessage(p, "You are now chatting to everyone");

					return true;
				}

				else if (args[0].equalsIgnoreCase("t")) {

					if (plugin.getTm().getTeam(p) == null) {
						plugin.sendMessage(p, ChatColor.RED + "You don't have a team to chat to!");
						return true;
					}

					plugin.getCh().setChat(ChatType.TEAM, p);
					plugin.sendMessage(p, "You are now chatting to your teammates");

					return true;
				}

				else if (args[0].equalsIgnoreCase("a")) {

					if (!p.hasPermission("uhc.admin")) {
						plugin.sendMessage(p, ChatColor.RED + "You do not have permission to enter that chat channel!");
						return true;
					}

					plugin.getCh().setChat(ChatType.ADMIN, p);
					plugin.sendMessage(p, "You are now chatting to admins");
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
