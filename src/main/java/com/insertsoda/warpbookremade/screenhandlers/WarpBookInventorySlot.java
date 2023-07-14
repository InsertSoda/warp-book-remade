package com.insertsoda.warpbookremade.screenhandlers;

import com.insertsoda.warpbookremade.items.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class WarpBookInventorySlot extends Slot {
    public WarpBookInventorySlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() == ModItems.BOUND_WARP_PAGE;
    }
}
