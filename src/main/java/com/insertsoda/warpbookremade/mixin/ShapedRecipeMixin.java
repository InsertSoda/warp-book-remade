package com.insertsoda.warpbookremade.mixin;

import com.insertsoda.warpbookremade.items.WarpBook;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
    @Inject(at = @At("HEAD"), method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", cancellable = true)
    private void craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir){
        ItemStack result = ((ShapedRecipe)(Object)this).getOutput(dynamicRegistryManager).copy();
        if(result.getItem() instanceof WarpBook){
            for (ItemStack item : recipeInputInventory.getInputStacks()) {
                if(item.getItem() instanceof WarpBook){
                    result.setNbt(item.getNbt());
                    cir.setReturnValue(result);
                    cir.cancel();
                    return;
                }
            }
        }

    }
}
