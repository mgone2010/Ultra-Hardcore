package me.chasertw123.uhc.listeners;

import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener{

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (!e.getTo().getWorld().getName().contains("UHC_world"))
			return;
		
		if (e.getTo().getWorld().getEnvironment() == Environment.NORMAL) {
			// Prevent players to tp outside the walls. Blocks enderpearls, netherportals and other teleports.
			e.getTo().setX(Math.min(999, Math.max(-999, e.getTo().getX())));
			e.getTo().setZ(Math.min(999, Math.max(-999, e.getTo().getZ())));
		} else {
			e.getTo().setX(Math.min(124, Math.max(-124, e.getTo().getX())));
			e.getTo().setZ(Math.min(124, Math.max(-124, e.getTo().getZ())));
		}
	}
}
