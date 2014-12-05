package me.chasertw123.uhc.listeners;

import me.chasertw123.uhc.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

	private Main plugin;
	
	public EntityDamageByEntity(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		
		if (!(e.getEntity() instanceof Player))
			return;
		
		if (e.getDamager() instanceof Player) {
			
			if (!plugin.getA().isGrace())
				return;
			
			if (e.getDamager() instanceof Player) {
				
				e.setCancelled(true);
				plugin.sendMessage((Player) e.getDamager(), ChatColor.RED + "A grace period is currently active!");
				
				return;
			}
		}
		
		else if (e.getDamager() instanceof Projectile) {
			
			if (!plugin.getA().isGrace())
				return;
			
			Projectile proj = (Projectile) e.getDamager();
			
			if (proj.getShooter() instanceof Player) {
				
				e.setCancelled(true);
				plugin.sendMessage((Player) proj.getShooter(), ChatColor.RED + "A grace period is currently active!");
				
				return;
			}
		}
	}
}
