package com.insertsoda.warpbookremade.screenhandlers;

import com.insertsoda.warpbookremade.WarpBookRemade;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static ScreenHandlerType<NameWarpPageScreenHandler> NAME_WARP_PAGE_SCREEN_HANDLER;
    public static ScreenHandlerType<WarpBookInventoryScreenHandler> WARP_BOOK_INVENTORY_SCREEN_HANDLER;
    public static ScreenHandlerType<WarpBookPageSelectionScreenHandler> WARP_BOOK_PAGE_SELECTION_SCREEN_HANDLER;

    public static void register(){
        NAME_WARP_PAGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(WarpBookRemade.MOD_ID,"name_warp_page_screen_handler"), new ExtendedScreenHandlerType<>(NameWarpPageScreenHandler::new));
        WARP_BOOK_INVENTORY_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(WarpBookRemade.MOD_ID,"warp_book_inventory_screen_handler"), new ExtendedScreenHandlerType<>(WarpBookInventoryScreenHandler::new));
        WARP_BOOK_PAGE_SELECTION_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(WarpBookRemade.MOD_ID,"warp_book_page_selection_screen_handler"), new ExtendedScreenHandlerType<>(WarpBookPageSelectionScreenHandler::new));
    }
}
