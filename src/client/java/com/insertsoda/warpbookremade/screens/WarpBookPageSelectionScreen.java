package com.insertsoda.warpbookremade.screens;

import com.insertsoda.warpbookremade.items.ModItems;
import com.insertsoda.warpbookremade.networking.PacketIdentifiers;
import com.insertsoda.warpbookremade.screenhandlers.WarpBookPageSelectionScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
@Environment(EnvType.CLIENT)
public class WarpBookPageSelectionScreen extends HandledScreen<ScreenHandler> {

    public Text warpBookName = ((WarpBookPageSelectionScreenHandler) handler).getWarpBookName();

    public Inventory pageInventory = ((WarpBookPageSelectionScreenHandler) handler).getInventory();

    public WarpBookPageSelectionScreen(ScreenHandler handler, PlayerInventory inventory, Text title){
        super(handler, inventory, Text.translatable("screen.warp-book-remade.warp_book_page_selection_screen.title"));
    }

    private final int buttonWidth = 100;
    private final int buttonHeight = 17;
    private final int marginX = 3;
    private final int marginY = 3;

    @Override
    protected void init(){
        int warpPagesAdded = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int slotId = i * 9 + j;
                ItemStack boundWarpPage = this.pageInventory.getStack(slotId);
                if(boundWarpPage.getItem() == ModItems.BOUND_WARP_PAGE && boundWarpPage.hasNbt()){
                    int x;
                    int y;

                    int offsetX = Math.floorMod(warpPagesAdded, 3);
                    if(offsetX == 0){
                        x = (int) (width / 2 - buttonWidth * 1.5 - marginX);
                    } else if(offsetX == 1) {
                        x = width / 2 - buttonWidth / 2;
                    } else {
                        x = width / 2 + buttonWidth / 2 + marginX;
                    }

                    int offsetY = warpPagesAdded / 3;
                    y = (int) (height / 2 - (4.5 - offsetY) * (buttonHeight + marginY));

                    NbtCompound nbt = boundWarpPage.getOrCreateNbt();
                    String name = nbt.getString("name");

                    ButtonWidget warpButton = ButtonWidget.builder(Text.literal(name), button -> {
                        PacketByteBuf buffer = PacketByteBufs.create();
                        buffer.writeEnumConstant(((WarpBookPageSelectionScreenHandler) handler).getHand());
                        buffer.writeInt(slotId);
                        ClientPlayNetworking.send(PacketIdentifiers.USE_WARP_BOOK_PAGE, buffer);
                        close();
                    })
                            .dimensions(x,y,buttonWidth,buttonHeight)
                            .build();

                    addDrawableChild(warpButton);

                    warpPagesAdded++;
                }
            }
        }

        ButtonWidget cancel = ButtonWidget.builder(Text.translatable("gui.cancel"), button -> {
                    close();
                })
                .dimensions(width / 2 - 100, height / 2 + buttonHeight * 4 + 4 * marginY + 10, 200, 20)
                .build();
        addDrawableChild(cancel);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int textY = height / 2 - buttonHeight * 4 - 4 * marginY - 20;
        context.drawText(this.textRenderer, this.title, width / 2 - textRenderer.getWidth(this.title) / 2, textY, -1, true);
        context.drawText(this.textRenderer, warpBookName, width / 2 - textRenderer.getWidth(warpBookName) / 2, textY - 10, -1, true);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // Yeet the "Inventory" text away
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        renderBackground(context);
    }
}
