package me.chasertw123.uhc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpreadPlayers {
	private static final Random random = new Random();
	private Main plugin;
	public static final Integer maxXZ = 900; // Players can spawn up to 920 blocks from 0, 0 in each axis

	public SpreadPlayers(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public void spreadPlayers(Arena a, Location[] locs, World w) {
		List<Player> players = new ArrayList<Player>();

		for (Team t : Team.teamObjects)
			for (String s : t.getMembers())
				if (Bukkit.getPlayerExact(s) != null) {
					Player p = Bukkit.getPlayerExact(s);
					players.add(p);
				}

		spread(w, players, locs);
	}


	private void spread(World world, List<Player> list, Location[] locations) {
		int i = 0;
		Map<Team, Location> hashmap = new HashMap<Team, Location>();

		for (int j = 0; j < list.size(); ++j) {
			Player player = list.get(j);
			Location location;

			Team team = plugin.getTm().getTeam(player);

			if (!hashmap.containsKey(team)) 
				hashmap.put(team, locations[i++]);

			location = hashmap.get(team);

			double playerXOffset = random.nextInt(10) - 5;
			double playerZOffset = random.nextInt(10) - 5;

			final Location loc = new Location(world, Math.floor(location.getX() + playerXOffset) + 0.5D, 
					world.getHighestBlockYAt((int) (location.getX() + playerXOffset), (int) (location.getZ() + playerZOffset)) + 1.5, 
					Math.floor(location.getZ() + playerZOffset) + 0.5D);

			final UUID uuid = player.getUniqueId();

			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (Bukkit.getPlayer(uuid) != null) {
						Location tpLoc = makeSafe(loc);
						tpLoc.setWorld(Bukkit.getWorld("UHC_world"));
						
						if (loc.getBlock().getBiome() == Biome.OCEAN || loc.getBlock().getBiome() == Biome.DEEP_OCEAN) {
							loc.getWorld().spawnEntity(tpLoc, EntityType.BOAT);
							Bukkit.getPlayer(uuid).teleport(tpLoc); // Sorry, setting as passanger is not working :(
						} else
							Bukkit.getPlayer(uuid).teleport(tpLoc);
					}
				}
			}.runTaskLater(plugin, (long) Math.floor(j / 2) * 20);
		}
	}

	public Location[] getSpreadLocations(World world, int size, double xRangeMin, double zRangeMin, double xRangeMax, double zRangeMax) {
		Location[] locations = new Location[size];

		for (int i = 0; i < size; ++i) {
			double x = xRangeMin >= xRangeMax ? xRangeMin : random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin;
			double z = zRangeMin >= zRangeMax ? zRangeMin : random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin;
			locations[i] = (new Location(world, x, 0, z));
		}

		return locations;
	}

	private Location makeSafe(Location l) {
		int tries = 0;
		Location loc = l.clone();
		if (loc.getBlock().getType() != Material.LAVA)
			return loc;
		
		while (loc.getBlock().getType() == Material.LAVA && tries <= 10) {
			tries++;
			
			if (loc.getX() < maxXZ)
				loc.setX(l.getX() + 1);
			else 
				break;
			
			loc.setY(l.getWorld().getHighestBlockYAt(loc));
			
			if (loc.getBlock().getType() != Material.LAVA)
				return loc;
		}
		
		tries = 0;
		loc = l.clone();

		while (loc.getBlock().getType() == Material.LAVA && tries <= 10) {
			tries++;
			
			if (loc.getZ() < maxXZ)
				loc.setZ(l.getZ() + 1);
			else 
				break;
			
			loc.setY(l.getWorld().getHighestBlockYAt(loc));
			
			if (loc.getBlock().getType() != Material.LAVA)
				return loc;
		}
		
		tries = 0;
		loc = l.clone();

		while (loc.getBlock().getType() == Material.LAVA && tries <= 10) {
			tries++;
			
			if (loc.getZ() > -maxXZ)
				loc.setZ(l.getZ() - 1);
			else 
				break;
			
			loc.setY(l.getWorld().getHighestBlockYAt(loc));
			
			if (loc.getBlock().getType() != Material.LAVA)
				return loc;
		}
		
		tries = 0;
		loc = l.clone();

		while (loc.getBlock().getType() == Material.LAVA && tries <= 10) {
			tries++;
			
			if (loc.getX() > -maxXZ)
				loc.setX(l.getX() - 1);
			else 
				break;
			
			loc.setY(l.getWorld().getHighestBlockYAt(loc));
			
			if (loc.getBlock().getType() != Material.LAVA)
				return loc;
		}

		return l; // Oh well, we give up, ever saw a lava lake, 20 wide in both directions? Hopefully this never happens :)
	}
}
