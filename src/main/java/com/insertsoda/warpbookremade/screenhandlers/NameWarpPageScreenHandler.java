package com.insertsoda.warpbookremade.screenhandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;

public class NameWarpPageScreenHandler extends ScreenHandler {
    private String warpPageName;
    private Hand hand;

    public NameWarpPageScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buffer){
        this(syncId, playerInventory, new SimpleInventory(0));
        this.warpPageName = buffer.readString();
        this.hand = buffer.readEnumConstant(Hand.class);
    }

    public NameWarpPageScreenHandler(int syncId, PlayerInventory playerInventory, String name, Hand hand){
        this(syncId, playerInventory, new SimpleInventory(0));
        this.warpPageName = name;
        this.hand = hand;
    }

    public NameWarpPageScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.NAME_WARP_PAGE_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public String getWarpPageName() {
        return warpPageName;
    }

    public Hand getHand() {
        return hand;
    }
}
