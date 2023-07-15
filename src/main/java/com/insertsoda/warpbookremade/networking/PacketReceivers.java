package com.insertsoda.warpbookremade.networking;

import com.insertsoda.warpbookremade.functionalities.ModFunctionalities;
import com.insertsoda.warpbookremade.items.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.event.GameEvent;

public class PacketReceivers {

    public static void register(){
        ServerPlayNetworking.registerGlobalReceiver(PacketIdentifiers.SUBMIT_WARP_PAGE_NAME_ID, (server, player, handler, buffer, packetSender) -> {
            String name = buffer.readString();
            Hand hand = buffer.readEnumConstant(Hand.class);

            server.execute(() -> {

                ItemStack itemStack = player.getStackInHand(hand);

                if(itemStack.getItem() == ModItems.BOUND_WARP_PAGE && itemStack.hasNbt()){
                    NbtCompound nbt = itemStack.getNbt();
                    nbt.putString("name", name);
                    itemStack.setNbt(nbt);
                    return;
                }

                NbtCompound nbt = new NbtCompound();

                double posX = (double) Math.round(player.getX() * 100) / 100;
                double posY = (double) Math.round(player.getY() * 100) / 100;
                double posZ = (double) Math.round(player.getZ() * 100) / 100;

                String boundWorld = player.getWorld().getRegistryKey().getValue().toString();

                nbt.putDouble("positionX", posX);
                nbt.putDouble("positionY", posY);
                nbt.putDouble("positionZ", posZ);
                nbt.putString("world", boundWorld);
                nbt.putString("name", name);
                ItemStack BoundWarpPage = new ItemStack(ModItems.BOUND_WARP_PAGE);
                BoundWarpPage.setNbt(nbt);

                itemStack.decrement(1);

                if(itemStack.isEmpty()){
                    if(hand == Hand.OFF_HAND){
                        player.getInventory().offHand.set(0,BoundWarpPage);
                    } else {
                        player.getInventory().main.set(player.getInventory().selectedSlot, BoundWarpPage);
                    }
                } else {
                    player.getInventory().insertStack(BoundWarpPage);
                }

                player.getItemCooldownManager().set(itemStack.getItem(), 10);
                player.getItemCooldownManager().set(BoundWarpPage.getItem(), 10);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketIdentifiers.USE_WARP_BOOK_PAGE, (server, player, handler, buffer, packetSender) -> {
            Hand hand = buffer.readEnumConstant(Hand.class);
            ItemStack stack = player.getStackInHand(hand);

            if(stack.getItem() == ModItems.WARP_BOOK){
                player.getItemCooldownManager().set(stack.getItem(), 10);

                int slotId = buffer.readInt();
                DefaultedList<ItemStack> pageInventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.readNbt(stack.getOrCreateNbt(), pageInventory);

                stack = pageInventory.get(slotId);
            }

            ModFunctionalities.attemptTeleportUsingBoundWarpPageItem(player, stack);
        });
    }



}