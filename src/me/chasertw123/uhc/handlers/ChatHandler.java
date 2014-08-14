package me.chasertw123.uhc.handlers;

import java.util.HashMap;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler {

	private HashMap<String, ChatType> channel = new HashMap<String, ChatType>();

	public ChatColor G_COLOR, T_COLOR, A_COLOR; // Color of prefix G/T/A and chat message
	public ChatColor SQ_BR_COLOR; // SQUARE_BRACKETS_COLOR
	public String DELIMINATOR; // Between username & message
	public String G_PREFIX, T_PREFIX, A_PREFIX; // Prefix of G(lobal)/T(eam)/A(dmin) chat

	public enum ChatType { ADMIN, GLOBAL, TEAM }

	public ChatHandler() {
		// Possibly load from config in future?
		G_COLOR = ChatColor.WHITE; 
		T_COLOR = ChatColor.GREEN;
		A_COLOR = ChatColor.AQUA;
		SQ_BR_COLOR = ChatColor.WHITE;

		G_PREFIX = SQ_BR_COLOR + "[" + G_COLOR + "G" + SQ_BR_COLOR + "] ";
		T_PREFIX = SQ_BR_COLOR + "[" + T_COLOR + "T" + SQ_BR_COLOR + "] ";
		A_PREFIX = SQ_BR_COLOR + "[" + A_COLOR + "A" + SQ_BR_COLOR + "] ";

		DELIMINATOR = ChatColor.YELLOW + " >> " + ChatColor.WHITE;
	}

	public void setChat(ChatType chat, Player p) {
		channel.put(p.getName(), chat);
	}

	public ChatType getChatType(Player p) {
		if (channel.containsKey(p.getName()))
			return channel.get(p.getName());
		else
			return ChatType.GLOBAL;
	}

	public void handleChat(AsyncPlayerChatEvent e, Main plugin) {
		ChatType type = getChatType(e.getPlayer());
		Player p = e.getPlayer();

		/** Message layout: T/A/G_PREFIX + DISPLAY_NAME + DELI + T/A/G_COLOR + MESSAGE **/

		if (type == ChatType.GLOBAL)
			Bukkit.broadcastMessage(G_PREFIX + p.getDisplayName() + DELIMINATOR + G_COLOR + e.getMessage());

		else if (type == ChatType.TEAM) {
			Team t = plugin.getTm().getTeam(p);
			if (t != null) 
				t.sendMessage(T_PREFIX  + p.getDisplayName() + DELIMINATOR + T_COLOR + e.getMessage(), false);
			else {
				plugin.sendMessage(p, ChatColor.RED + "You don't have a team, you got automagically put in global chat.");
				setChat(ChatType.GLOBAL, p);
				Bukkit.broadcastMessage(G_PREFIX + p.getDisplayName() + DELIMINATOR + G_COLOR + e.getMessage());
			}

		} else if (type == ChatType.ADMIN) {
			if (p.hasPermission("uhc.admin")) 
				for (Player pl : Bukkit.getOnlinePlayers()) 
					if (pl.hasPermission("uhc.admin"))
						pl.sendMessage(A_PREFIX + p.getDisplayName() + DELIMINATOR + A_COLOR + e.getMessage());
					else;
			else {
				plugin.sendMessage(p, ChatColor.RED + "You don't have permission to chat here! You got automagically put in global chat.");
				setChat(ChatType.GLOBAL, p);
				Bukkit.broadcastMessage(G_PREFIX + p.getDisplayName() + DELIMINATOR + G_COLOR + e.getMessage());
			}
		}
	}
}
