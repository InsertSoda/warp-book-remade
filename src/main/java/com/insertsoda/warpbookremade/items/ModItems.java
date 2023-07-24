package com.insertsoda.warpbookremade.items;

import com.insertsoda.warpbookremade.WarpBookRemade;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item UNBOUND_WARP_PAGE = registerItem("unbound_warp_page",new UnboundWarpPage(new FabricItemSettings().maxCount(16).rarity(Rarity.COMMON)));
    public static final Item BOUND_WARP_PAGE = registerItem("bound_warp_page",new BoundWarpPage(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));

    public static final Item WARP_BOOK = registerItem("warp_book",new WarpBook(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item REINFORCED_WARP_BOOK = registerItem("reinforced_warp_book",new ReinforcedWarpBook(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    private static void addItemsToTabItemGroup(FabricItemGroupEntries entries){
        entries.add(UNBOUND_WARP_PAGE);
        entries.add(BOUND_WARP_PAGE);
        entries.add(WARP_BOOK);
        entries.add(REINFORCED_WARP_BOOK);
    }
    public static void  register(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToTabItemGroup);
    }

    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(WarpBookRemade.MOD_ID, name), item);
    }
}
