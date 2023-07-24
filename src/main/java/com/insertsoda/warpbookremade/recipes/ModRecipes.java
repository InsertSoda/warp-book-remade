package com.insertsoda.warpbookremade.recipes;

import com.insertsoda.warpbookremade.WarpBookRemade;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static RecipeSerializer<ReinforcedWarpBookRecipe> REINFORCED_WARP_BOOK_RECIPE;
    public static void register(){
        REINFORCED_WARP_BOOK_RECIPE = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(WarpBookRemade.MOD_ID, "reinforced_warp_book_recipe"), new SpecialRecipeSerializer<>(ReinforcedWarpBookRecipe::new));
    }
}
