package com.insertsoda.warpbookremade.functionalities;

import com.insertsoda.warpbookremade.items.BoundWarpPage;
import com.insertsoda.warpbookremade.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModFunctionalities {
    public static void attemptTeleportUsingBoundWarpPageItem(ServerPlayerEntity player, ItemStack boundWarpPage){
        if(!(boundWarpPage.getItem() instanceof BoundWarpPage)){
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

        player.getServerWorld().spawnParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1, player.getZ(), 100,0,0,0,0.1);

        // Complains about the player moving too quickly when the destination is far away
        // But it seems like the Waystones mod is also affected by it and that's a popular mod
        // so I'm not concerned to get that fixed anymore
        player.teleport(destinationWorld, posX, posY, posZ, player.getYaw(), player.getPitch());

        player.getItemCooldownManager().set(ModItems.BOUND_WARP_PAGE, 20);
        player.getItemCooldownManager().set(ModItems.LEATHER_WARP_BOOK, 20);
        player.getItemCooldownManager().set(ModItems.IRON_WARP_BOOK, 20);
        player.getItemCooldownManager().set(ModItems.GOLDEN_WARP_BOOK, 20);

        destinationWorld.playSound(null, posX, posY, posZ, SoundEvent.of(new Identifier("minecraft", "entity.enderman.teleport")), SoundCategory.AMBIENT , 1, 1);

        spawnParticlesWithLongDistanceEnabled(destinationWorld, ParticleTypes.REVERSE_PORTAL, posX, posY + 1, posZ, 100, 0,0,0,1);
    }

    public static void spawnParticlesWithLongDistanceEnabled(ServerWorld world, ParticleType particleType, double x, double y, double z, int count,float deltaX, float deltaY, float deltaZ, float speed){
        ParticleS2CPacket particlesPacket = new ParticleS2CPacket(ParticleTypes.REVERSE_PORTAL, true, x, y, z, deltaX, deltaY, deltaZ, speed, count);

        for (int i = 0; i < world.getPlayers().size(); i++) {
            world.sendToPlayerIfNearby(world.getPlayers().get(i), false, x, y, z, particlesPacket);
        }
    }

}