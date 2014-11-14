package me.chasertw123.uhc.handlers;

import me.chasertw123.uhc.Main;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeHandler {

	public RecipeHandler(Main plugin) {
		
		ShapedRecipe goldenCarrot = new ShapedRecipe(new ItemStack(Material.GOLDEN_CARROT));
		ShapelessRecipe glisteringMelon = new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON));
		
		goldenCarrot.shape("AAA", "ABA", "AAA");
		goldenCarrot.setIngredient('A', Material.GOLD_INGOT);
		goldenCarrot.setIngredient('B', Material.CARROT_ITEM);
		
		glisteringMelon.addIngredient(Material.GOLD_BLOCK);
		glisteringMelon.addIngredient(Material.MELON);
		
		plugin.getServer().addRecipe(goldenCarrot);
		plugin.getServer().addRecipe(glisteringMelon);
	}
}
