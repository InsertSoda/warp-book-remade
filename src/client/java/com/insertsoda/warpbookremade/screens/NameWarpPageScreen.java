package com.insertsoda.warpbookremade.screens;

import com.insertsoda.warpbookremade.networking.PacketIdentifiers;
import com.insertsoda.warpbookremade.screenhandlers.NameWarpPageScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

@Environment(EnvType.CLIENT)
public class NameWarpPageScreen extends HandledScreen<ScreenHandler> {

    public NameWarpPageScreen(ScreenHandler handler, PlayerInventory inventory, Text title){
        super(handler, inventory, Text.translatable("screen.warp-book-remade.name_warp_page_screen.title"));
        handler.syncState();
    }

    private TextFieldWidget nameField;

    @Override
    protected void init(){
        nameField = new TextFieldWidget(textRenderer ,width / 2 - 100, height / 2 - 10, 200, 20, Text.literal(""));
        nameField.setPlaceholder(Text.translatable("screen.warp-book-remade.name_warp_page_screen.input_placeholder"));
        nameField.setText(((NameWarpPageScreenHandler) handler).getWarpPageName());

        ButtonWidget finish = ButtonWidget.builder(Text.translatable("gui.done"), button -> {
                    if (!nameField.getText().equals("")) {
                        submitName(nameField.getText(), ((NameWarpPageScreenHandler) handler).getHand());
                        close();
                    }
                })
                .dimensions(width / 2 - 100, height - 80, 200, 20)
                .build();

        ButtonWidget cancel = ButtonWidget.builder(Text.translatable("gui.cancel"), button -> {
                    close();
                })
                .dimensions(width / 2 - 100, height - 50, 200, 20)
                .build();

        addDrawableChild(finish);
        addDrawableChild(cancel);
        addDrawableChild(nameField);

        setInitialFocus(nameField);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, this.title, width / 2 - textRenderer.getWidth(this.title) / 2, height / 2 - 30, -1, true);
    }

    @Override
    public void handledScreenTick(){
        nameField.tick();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // Yeet the "Inventory" text away
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        renderBackground(context);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Fixes issue with E closing the menu
        if(nameField.isActive() && this.client != null && this.client.options.inventoryKey.matchesKey(keyCode, scanCode)){
            return false;
        }
        super.keyPressed(keyCode,scanCode,modifiers);
        return true;
    }

    private void submitName(String name, Hand hand){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeString(name);
        buffer.writeEnumConstant(hand);
        ClientPlayNetworking.send(PacketIdentifiers.SUBMIT_WARP_PAGE_NAME_ID, buffer);
    }
}
