package com.insertsoda.warpbookremade.screenhandlers;

import com.insertsoda.warpbookremade.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

public class WarpBookPageSelectionScreenHandlerFactory implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private int rows;
    private DefaultedList<ItemStack> items;

    private final ItemStack warpBookStack;

    private final Hand hand;

    public WarpBookPageSelectionScreenHandlerFactory(ItemStack warpBookStack, Hand hand, int rows){
        this.warpBookStack = warpBookStack;
        this.hand = hand;
        this.rows = rows;

        items = DefaultedList.ofSize(rows * 9, ItemStack.EMPTY);

        NbtCompound nbt = warpBookStack.getOrCreateNbt();
        Inventories.readNbt(nbt, items);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WarpBookPageSelectionScreenHandler(syncId, playerInventory, this.warpBookStack, hand, rows);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buffer) {
        buffer.writeEnumConstant(this.hand);
        buffer.writeInt(this.rows);
        buffer.writeItemStack(this.warpBookStack);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int size() {
        return ImplementedInventory.super.size();
    }

    @Override
    public boolean isEmpty() {
        return ImplementedInventory.super.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return ImplementedInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        return ImplementedInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ImplementedInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ImplementedInventory.super.setStack(slot, stack);
    }

    @Override
    public void clear() {
        ImplementedInventory.super.clear();
    }

    @Override
    public void markDirty() {
        ImplementedInventory.super.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return ImplementedInventory.super.canPlayerUse(player);
    }
}
