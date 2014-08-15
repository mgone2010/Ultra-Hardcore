package me.chasertw123.uhc.handlers;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("serial")
public class RandomChestHandler {

	private Random r = new Random();
	private static final ArrayList<ItemStack> TIER_ONE = new ArrayList<ItemStack>(){{
		add(new ItemStack(Material.DIAMOND, 1));
		add(new ItemStack(Material.APPLE, 2));
		add(new ItemStack(Material.GOLDEN_APPLE, 1));
	}};
	private static final ArrayList<ItemStack> TIER_TWO = new ArrayList<ItemStack>(){{
		add(new ItemStack(Material.IRON_INGOT, 1));
		add(new ItemStack(Material.GOLD_INGOT, 1));
	}};
	private static final ArrayList<ItemStack> TIER_THREE = new ArrayList<ItemStack>(){{
		add(new ItemStack(Material.GOLD_NUGGET, 1));
	}};

	public void spawnChest(Location loc) {
		loc.getChunk().load(true);
		loc.setY(loc.getWorld().getHighestBlockYAt(loc));

		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				Location obby = loc.clone();
				obby.add(i, -1, j).getBlock().setType(Material.OBSIDIAN);
				obby.add(0, 1, 0).getBlock().setType(Material.AIR);
				obby.add(0, 1, 0).getBlock().setType(Material.AIR);
			}

		loc.getBlock().setType(Material.CHEST);

		Chest chest = (Chest) loc.getBlock().getState();

		Integer items = r.nextInt(5) + 3; // 3 - 7 items in chest, possible less when items override each other.
		for (int i = 0; i < items; i++)
			chest.getBlockInventory().setItem(r.nextInt(27), getRandomItemStack());
	}

	private ItemStack getRandomItemStack() {
		Integer randomTier = r.nextInt(6);
		ArrayList<ItemStack> tiers;
		if (randomTier == 0) // 1 in 6 (== 0)
			tiers = TIER_ONE;
		else if (randomTier <= 2) // 2 in 6 (== 1 || == 2)
			tiers = TIER_TWO;
		else
			tiers = TIER_THREE; // 3 in 6 (== 3 || == 4 || == 5)

		return tiers.get(r.nextInt(tiers.size()));
	}
}
