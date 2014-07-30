package me.chasertw123.uhc.handlers;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.commands.AdminCmd;
import me.chasertw123.uhc.commands.ChatCmd;
import me.chasertw123.uhc.commands.LeaveCmd;
import me.chasertw123.uhc.commands.TeamCmd;

public class CommandHandler {

	public CommandHandler(Main plugin) {
		plugin.getCommand("Admin").setExecutor(new AdminCmd(plugin));
		plugin.getCommand("Team").setExecutor(new TeamCmd(plugin));
		plugin.getCommand("Chat").setExecutor(new ChatCmd(plugin));
		plugin.getCommand("Leave").setExecutor(new LeaveCmd(plugin));
	}
}
