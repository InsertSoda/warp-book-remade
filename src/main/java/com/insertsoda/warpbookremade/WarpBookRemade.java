package com.insertsoda.warpbookremade;

import com.insertsoda.warpbookremade.networking.PacketReceivers;
import com.insertsoda.warpbookremade.items.ModItems;
import com.insertsoda.warpbookremade.recipes.ModRecipes;
import com.insertsoda.warpbookremade.screenhandlers.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarpBookRemade implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("warp-book-remade");

	public static final String MOD_ID = "warp-book-remade";



	@Override
	public void onInitialize() {
		ModItems.register();
		ModScreenHandlers.register();
		PacketReceivers.register();
		ModRecipes.register();
	}
}