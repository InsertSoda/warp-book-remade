package com.insertsoda.warpbookremade.recipes;

import com.insertsoda.warpbookremade.WarpBookRemade;
import com.insertsoda.warpbookremade.items.ModItems;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ReinforcedWarpBookRecipe extends SpecialCraftingRecipe {
    public ReinforcedWarpBookRecipe(Identifier identifier, CraftingRecipeCategory craftingRecipeCategory) {
        super(identifier, craftingRecipeCategory);
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        System.out.println("it tried to math but lol");
        int i = 0;
        int j = 0;
        for (int k = 0; k < recipeInputInventory.size(); ++k) {
            ItemStack itemStack = recipeInputInventory.getStack(k);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == ModItems.WARP_BOOK) {
                ++i;
            } else if (itemStack.getItem() == Items.ENDER_PEARL) {
                ++j;
            } else {
                return false;
            }
            if (j <= 1 && i <= 1) continue;
            return false;
        }
        return i == 1 && j == 1;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            Item item = itemStack2.getItem();
            if (item == ModItems.WARP_BOOK) {
                itemStack = itemStack2;
                continue;
            }
            if (!(item == Items.ENDER_PEARL)) {
            }
        }
        ItemStack itemStack3 = new ItemStack(ModItems.REINFORCED_WARP_BOOK);
        if (itemStack.hasNbt()) {
            itemStack3.setNbt(itemStack.getNbt().copy());
        }
        return itemStack3;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.REINFORCED_WARP_BOOK_RECIPE;
    }
}
