package com.insertsoda.warpbookremade;

import com.insertsoda.warpbookremade.screenhandlers.ModScreenHandlers;
import com.insertsoda.warpbookremade.screens.NameWarpPageScreen;
import com.insertsoda.warpbookremade.screens.WarpBookInventoryScreen;
import com.insertsoda.warpbookremade.screens.WarpBookPageSelectionScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WarpBookRemadeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.NAME_WARP_PAGE_SCREEN_HANDLER, NameWarpPageScreen::new);
		HandledScreens.register(ModScreenHandlers.WARP_BOOK_INVENTORY_SCREEN_HANDLER, WarpBookInventoryScreen::new);
		HandledScreens.register(ModScreenHandlers.WARP_BOOK_PAGE_SELECTION_SCREEN_HANDLER, WarpBookPageSelectionScreen::new);
	}
}