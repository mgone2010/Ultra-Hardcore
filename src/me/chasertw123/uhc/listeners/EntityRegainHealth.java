package me.chasertw123.uhc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityRegainHealth implements Listener {

	@EventHandler
	public void onHealtRegen(EntityRegainHealthEvent e) {
		if (e.getRegainReason() == RegainReason.REGEN || e.getRegainReason() == RegainReason.SATIATED) {
			// RegainCause.REGEN; When a player regains health from regenerating due to Peaceful mode (difficulty=0) 
			// RegainCause.SATIATED; When a player regains health from regenerating due to their hunger being satisfied. 
			
			e.setCancelled(true); 
		}
	}
}
