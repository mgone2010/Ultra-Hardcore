package me.chasertw123.uhc.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import me.chasertw123.uhc.Main;

import org.bukkit.entity.Player;

public class ServerConnector {

	Main plugin;
	
	public ServerConnector(Main plugin) {
		this.plugin = plugin;
		
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
	}
	
	public void sendToServer(Player p, String ts) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			out.writeUTF("Connect");
			out.writeUTF(ts);
		} catch (Exception e) {e.printStackTrace();}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
}
