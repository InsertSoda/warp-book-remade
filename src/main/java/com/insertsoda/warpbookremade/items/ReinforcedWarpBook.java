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

public class ReinforcedWarpBook extends WarpBook {

    @Override
    public int getRows() {
        return 2;
    }
    public ReinforcedWarpBook(Settings settings) {
        super(settings);
    }

}
