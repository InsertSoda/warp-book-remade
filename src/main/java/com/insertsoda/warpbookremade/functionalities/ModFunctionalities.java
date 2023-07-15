package com.insertsoda.warpbookremade.functionalities;

import com.insertsoda.warpbookremade.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModFunctionalities {
    public static void attemptTeleportUsingBoundWarpPageItem(ServerPlayerEntity player, ItemStack boundWarpPage){
        if(boundWarpPage.getItem() != ModItems.BOUND_WARP_PAGE){
            return;
        }

        NbtCompound nbt = boundWarpPage.getOrCreateNbt();

        double posX = nbt.getDouble("positionX");
        double posY = nbt.getDouble("positionY");
        double posZ = nbt.getDouble("positionZ");
        String boundWorld = nbt.getString("world");
        if(boundWorld.equals("")){
            boundWorld = "minecraft:overworld";
        }
        Identifier worldIdentifier = new Identifier(boundWorld);
        ServerWorld destinationWorld = player.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, worldIdentifier));

        // Complains about the player moving too quickly when the destination is far away
        // But it seems like the Waystones mod is also affected by it and that's a popular mod
        // so I'm not concerned to get that fixed anymore
        player.teleport(destinationWorld,posX,posY,posZ, player.getYaw(), player.getPitch());


        player.getItemCooldownManager().set(boundWarpPage.getItem(), 10);
        player.playSound(SoundEvent.of(new Identifier("minecraft", "entity.enderman.teleport")), SoundCategory.AMBIENT , 1, 1);
    }
}
