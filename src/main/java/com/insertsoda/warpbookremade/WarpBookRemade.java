package com.insertsoda.warpbookremade;

import com.insertsoda.warpbookremade.networking.RegisterHandlers;
import com.insertsoda.warpbookremade.items.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarpBookRemade implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("warp-book-remade");

	public static final String MOD_ID = "warp-book-remade";

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		RegisterHandlers.register();
	}
}