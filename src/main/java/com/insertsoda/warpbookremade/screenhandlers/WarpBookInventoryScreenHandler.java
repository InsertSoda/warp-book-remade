package com.insertsoda.warpbookremade.screenhandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class WarpBookInventoryScreenHandler extends ScreenHandler {


    private Inventory inventory;

    public int getRows() {
        return rows;
    }

    private int rows;

    public ItemStack getWarpBookStack() {
        return warpBookStack;
    }

    private ItemStack warpBookStack;

    public WarpBookInventoryScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buffer){
        super(ModScreenHandlers.WARP_BOOK_INVENTORY_SCREEN_HANDLER, syncId);

        this.rows = buffer.readInt();
        this.warpBookStack = buffer.readItemStack();

        this.inventory = new SimpleInventory(this.rows * 9);

        addInventorySlots(playerInventory);

    }

    public WarpBookInventoryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ItemStack warpBookStack, int rows) {
        super(ModScreenHandlers.WARP_BOOK_INVENTORY_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.warpBookStack = warpBookStack;
        this.rows = rows;

        addInventorySlots(playerInventory);
    }

    // Extracting this into a method, since doing this() in the client method won't allow the warp book's inventory size to be properly set
    public void addInventorySlots(PlayerInventory playerInventory){
        inventory.onOpen(playerInventory.player);

        int i = (this.rows - 4) * 18;

        int j;
        int k;
        for(j = 0; j < this.rows; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new WarpBookInventorySlot(this.inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(j = 0; j < 3; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if(player.getWorld().isClient()){
            return;
        }

        NbtCompound nbt = warpBookStack.getOrCreateNbt();

        Inventories.writeNbt(nbt, ((WarpBookInventoryScreenHandlerFactory)inventory).getItems());
        warpBookStack.setNbt(nbt);
        super.onClosed(player);
    }
}
