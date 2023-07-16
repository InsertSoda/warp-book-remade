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
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

public class WarpBookPageSelectionScreenHandler extends ScreenHandler {


    public Inventory getInventory() {
        return this.inventory;
    }

    private int rows;

    public int getRows() {
        return this.rows;
    }
    private Inventory inventory;

    public Hand getHand() {
        return this.hand;
    }

    private Hand hand;

    public Text getWarpBookName() {
        return this.warpBookStack.getName();
    }

    private ItemStack warpBookStack;

    public WarpBookPageSelectionScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buffer){
        this(syncId, playerInventory, ItemStack.EMPTY, Hand.MAIN_HAND, 1);
        this.hand = buffer.readEnumConstant(Hand.class);
        this.rows = buffer.readInt();
        this.warpBookStack = buffer.readItemStack();

        NbtCompound nbt = this.warpBookStack.getOrCreateNbt();
        DefaultedList<ItemStack> pageInventory = DefaultedList.ofSize(rows * 9, ItemStack.EMPTY);

        Inventories.readNbt(nbt, pageInventory);

        this.inventory = new SimpleInventory(this.rows * 9);

        for (int i = 0; i < this.rows * 9; i++) {
            this.inventory.setStack(i, pageInventory.get(i));
        }

    }

    public WarpBookPageSelectionScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack warpBookStack, Hand hand, int rows) {
        super(ModScreenHandlers.WARP_BOOK_PAGE_SELECTION_SCREEN_HANDLER, syncId);
        this.warpBookStack = warpBookStack;
        this.hand = hand;
        this.rows = rows;
        this.inventory = new SimpleInventory(this.rows * 9);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

}
