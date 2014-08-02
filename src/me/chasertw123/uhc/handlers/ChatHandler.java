package me.chasertw123.uhc.handlers;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ChatHandler {

	private HashMap<String, ChatType> channel = new HashMap<String, ChatType>();
	
	public enum ChatType { ADMIN, GLOBAL, TEAM }
	
	public void setChat(ChatType chat, Player p) {
		channel.put(p.getName(), chat);
	}
	
	public ChatType getChatType(Player p) {
		return channel.get(p.getName());
	}
}
