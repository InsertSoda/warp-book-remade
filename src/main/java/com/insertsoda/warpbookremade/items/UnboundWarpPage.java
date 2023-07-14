package com.insertsoda.warpbookremade.items;

import com.insertsoda.warpbookremade.screenhandlers.NameWarpPageScreenHandlerFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class UnboundWarpPage extends Item {


    public UnboundWarpPage(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // If there's a bound warp page in the main hand, don't continue
        if(hand == Hand.OFF_HAND){
            if(user.getInventory().main.get(user.getInventory().selectedSlot).getItem() == ModItems.BOUND_WARP_PAGE){
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }

        if(world.isClient){
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        user.openHandledScreen(new NameWarpPageScreenHandlerFactory("", hand));

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.warp-book-remade.unbound_warp_page.tooltip"));
    }

}
