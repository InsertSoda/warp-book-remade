package com.insertsoda.warpbookremade.recipes;

import com.insertsoda.warpbookremade.WarpBookRemade;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static RecipeSerializer<ShapedWarpBookUpgradeRecipe> SHAPED_WARP_BOOK_UPGRADE_RECIPE_SERIALIZER;
    public static void register(){
        SHAPED_WARP_BOOK_UPGRADE_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(WarpBookRemade.MOD_ID, "shaped_warp_book_upgrade_recipe"), new ShapedWarpBookUpgradeRecipe.Serializer());
    }
}
