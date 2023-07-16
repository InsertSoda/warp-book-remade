package com.insertsoda.warpbookremade.items;

import com.insertsoda.warpbookremade.screenhandlers.WarpBookInventoryScreenHandlerFactory;
import com.insertsoda.warpbookremade.screenhandlers.WarpBookPageSelectionScreenHandlerFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class WarpBook extends Item {

    public WarpBook(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtCompound nbt = itemStack.getOrCreateNbt();

        DefaultedList<ItemStack> pageInventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        Inventories.readNbt(nbt, pageInventory);


        if(user.isSneaking()){
            user.openHandledScreen(new WarpBookInventoryScreenHandlerFactory(itemStack));
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        // Passing the hand as well to verify that the user clicked on a real warp on the server, instead of a forged one
        // No idea if Minecraft has something to prevent that built-in, but I'll keep it in
        if(!world.isClient()){
            user.openHandledScreen(new WarpBookPageSelectionScreenHandlerFactory(itemStack, hand, 3));
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int rows = 3;
        DefaultedList<ItemStack> pageInventory = DefaultedList.ofSize(rows * 9, ItemStack.EMPTY);
        Inventories.readNbt(nbt, pageInventory);
        int amountOfWarpPages = 0;
        for (int i = 0; i < rows * 9; i++) {
            if(pageInventory.get(i).getItem() == ModItems.BOUND_WARP_PAGE){
                amountOfWarpPages++;
            }
        }
        tooltip.add(Text.translatable("item.warp-book-remade.warp_book.tooltip.amount_of_pages", amountOfWarpPages, rows * 9));
    }
}
