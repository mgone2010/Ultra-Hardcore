package me.chasertw123.uhc.listeners;

import java.util.UUID;

import me.chasertw123.uhc.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerTeleport implements Listener{

	private Main plugin;

	public PlayerTeleport(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		e.setTo(calculateTo(e.getTo(), false));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport (PlayerPortalEvent e) {
		final UUID uuid = e.getPlayer().getUniqueId();
		new BukkitRunnable() {

			@Override
			public void run() {
				if (Bukkit.getPlayer(uuid) != null)
					Bukkit.getPlayer(uuid).teleport(calculateTo(Bukkit.getPlayer(uuid).getLocation(),true));
			}
		}.runTaskLater(plugin, 20L);
	}

	public Location calculateTo(Location to, boolean generatePortal) {

		if (to == null || !to.getWorld().getName().contains("UHC_world"))
			return to;

		if (to.getWorld().getEnvironment() == Environment.NORMAL) {
			if (to.getBlock().getType() == Material.PORTAL && (to.getX() > 999 || to.getX() < -999 || to.getZ() > 999 || to.getZ() < -999))
				to.getBlock().breakNaturally();
			else 
				generatePortal = false;

			if (to.getBlock().getType() != Material.PORTAL) {
				to.setX(Math.min(998, Math.max(-998, to.getX())));
				to.setZ(Math.min(998, Math.max(-998, to.getZ())));
			}
		} else {
			if (to.getBlock().getType() == Material.PORTAL && (to.getX() > 125 || to.getX() < -125 || to.getZ() > 125 || to.getZ() < -125))
				to.getBlock().breakNaturally();
			else 
				generatePortal = false;

			if (to.getBlock().getType() != Material.PORTAL) {
				to.setX(Math.min(123, Math.max(-123, to.getX())));
				to.setZ(Math.min(123, Math.max(-123, to.getZ()))); // Changed it so portals with generatePortal don't destroy bedrock
			}
		}

		if (generatePortal)
			generatePortal(to);

		return to;
	}

	private void generatePortal(Location startPoint) {
		Location startingPoint = startPoint.clone();
		startingPoint.add(0, -1, 1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom (in floor)
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom (above floor / feet level)
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Mid (head level)
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top (above head level)
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top (top level)
		startingPoint.add(0, 0, -1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top mid
		startingPoint.add(0, 0, -1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top mid
		startingPoint.add(0, 0, -1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top (top level)
		startingPoint.add(0, -1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Top (above head)
		startingPoint.add(0, -1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Mid (head)
		startingPoint.add(0, -1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom (feet)
		startingPoint.add(0, -1, 0);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom (ground)
		startingPoint.add(0, 0, 1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom mid (ground)
		startingPoint.add(0, 0, 1);
		startingPoint.getBlock().setType(Material.OBSIDIAN); // Bottom mid (ground)
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.AIR); // Back at start point
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.AIR); // Head level
		startingPoint.add(0, 1, 0);
		startingPoint.getBlock().setType(Material.AIR); // Above head
		startingPoint.add(0, -1, -1);
		startingPoint.getBlock().setType(Material.AIR); // Head & other side
		startingPoint.add(0, -1, 0);
		startingPoint.getBlock().setType(Material.AIR); // Last floor block
		startingPoint.add(0, 2, 0);
		startingPoint.getBlock().setType(Material.FIRE); // Other side now, top

		// Cleaing blocks in front (+x) in case they are stuck.
		startingPoint.add(1, 1, 1);
		for (int y = 0; y >= -3; y--)
			for (int z = 0; z >= -3; z--)
				startingPoint.clone().add(1, y, z).getBlock().breakNaturally();
	}
}
