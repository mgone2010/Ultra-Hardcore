package me.chasertw123.uhc.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeecordMessengerSender {

	private Main plugin;

	public BungeecordMessengerSender(Main plugin) {
		this.plugin = plugin;
	}

	public void updateState(GameState newstate, Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("UHC|UpdateState");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(plugin.getServerName());
			msgout.writeUTF(newstate.toString()); // Might get fancy colors later...
		} catch (IOException e) {e.printStackTrace();} 

		p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}

	public void updatePlayers(Integer newamount, Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("UHC|UpdatePlayerCount");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(plugin.getServerName());
			msgout.writeInt(newamount); 
		} catch (IOException e) {e.printStackTrace();} 

		p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
}
