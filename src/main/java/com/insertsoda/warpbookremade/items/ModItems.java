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

    public static final Item LEATHER_WARP_BOOK = registerItem("leather_warp_book",new LeatherWarpBook(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item IRON_WARP_BOOK = registerItem("iron_warp_book",new IronWarpBook(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item GOLDEN_WARP_BOOK = registerItem("golden_warp_book",new GoldenWarpBook(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));;
    private static void addItemsToTabItemGroup(FabricItemGroupEntries entries){
        entries.add(UNBOUND_WARP_PAGE);
        entries.add(BOUND_WARP_PAGE);
        entries.add(LEATHER_WARP_BOOK);
        entries.add(IRON_WARP_BOOK);
        entries.add(GOLDEN_WARP_BOOK);
    }
    public static void  register(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToTabItemGroup);
    }

    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(WarpBookRemade.MOD_ID, name), item);
    }
}
