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
    private Inventory inventory = new SimpleInventory(27);

    public Hand getHand() {
        return this.hand;
    }

    private Hand hand;

    public Text getWarpBookName() {
        return this.warpBookStack.getName();
    }

    private ItemStack warpBookStack;

    public WarpBookPageSelectionScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buffer){
        this(syncId, playerInventory, ItemStack.EMPTY, Hand.MAIN_HAND);
        this.hand = buffer.readEnumConstant(Hand.class);
        this.warpBookStack = buffer.readItemStack();

        NbtCompound nbt = this.warpBookStack.getOrCreateNbt();
        DefaultedList<ItemStack> pageInventory = DefaultedList.ofSize(27, ItemStack.EMPTY);

        Inventories.readNbt(nbt, pageInventory);

        for (int i = 0; i < 27; i++) {
            this.inventory.setStack(i, pageInventory.get(i));
        }

    }

    public WarpBookPageSelectionScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack warpBookStack, Hand hand) {
        super(ModScreenHandlers.WARP_BOOK_PAGE_SELECTION_SCREEN_HANDLER, syncId);
        this.warpBookStack = warpBookStack;
        this.hand = hand;
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
