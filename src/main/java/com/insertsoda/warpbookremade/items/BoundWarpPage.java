package com.insertsoda.warpbookremade.items;

import com.insertsoda.warpbookremade.functionalities.ModFunctionalities;
import com.insertsoda.warpbookremade.screenhandlers.NameWarpPageScreenHandlerFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class BoundWarpPage extends Item {


    public BoundWarpPage(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // If there's an unbound warp page in the main hand, don't continue
        if(hand == Hand.OFF_HAND){
            if(user.getInventory().main.get(user.getInventory().selectedSlot).getItem() == ModItems.UNBOUND_WARP_PAGE){
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }

        ItemStack itemStack = user.getStackInHand(hand);

        if(!itemStack.hasNbt()){
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        NbtCompound nbt = itemStack.getNbt();

        // If the user is sneaking, open the name warp page screen instead
        if(user.isSneaking()){
            if(!world.isClient()){
                String name = nbt.getString("name");
                user.openHandledScreen(new NameWarpPageScreenHandlerFactory(name, hand));
            }

            return TypedActionResult.success(user.getStackInHand(hand));
        }


        if(!world.isClient()){
            ModFunctionalities.attemptTeleportUsingBoundWarpPageItem((ServerPlayerEntity) user, itemStack);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if(itemStack.hasNbt()){
            NbtCompound nbt = itemStack.getNbt();
            double posX = nbt.getDouble("positionX");
            double posY = nbt.getDouble("positionY");
            double posZ = nbt.getDouble("positionZ");
            String boundWorld = nbt.getString("world");
            String name = nbt.getString("name");
            tooltip.add(Text.literal(name).formatted(Formatting.BOLD).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.warp_page-remade.bound_warp_page.tooltip.bound_with"));
            tooltip.add(Text.literal("x: "+ posX + " y: "+ posY +" z: "+ posZ));
            tooltip.add(Text.literal(boundWorld));
        } else {
            tooltip.add(Text.translatable("item.warp_page-remade.bound_warp_page.tooltip.bound_without_nbt").formatted(Formatting.RED));
            tooltip.add(Text.translatable("item.warp_page-remade.bound_warp_page.tooltip.bound_without_nbt_line_2").formatted(Formatting.RED));
        }
    }
}
