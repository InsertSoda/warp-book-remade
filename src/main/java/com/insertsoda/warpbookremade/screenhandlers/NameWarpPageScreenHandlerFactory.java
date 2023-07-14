package com.insertsoda.warpbookremade.screenhandlers;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class NameWarpPageScreenHandlerFactory implements ExtendedScreenHandlerFactory {

    String name;
    Hand hand;

    public NameWarpPageScreenHandlerFactory(String name, Hand hand){
        this.name = name;
        this.hand = hand;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buffer) {
        buffer.writeString(name);
        buffer.writeEnumConstant(hand);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new NameWarpPageScreenHandler(syncId, playerInventory, this.name, this.hand);
    }
}
