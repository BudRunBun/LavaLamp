package com.budrunbun.lavalamp.screens;

import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CheeseGeneratorScreen extends ContainerScreen<CheeseGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation("lavalamp", "textures/gui/container/cheese_generator.png");

    public CheeseGeneratorScreen(CheeseGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        System.out.println("Screen " + this.container.getMilkCapacity() + "/8");
        this.xSize = 184;
        this.ySize = 184;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

/*
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString("Cheese Maker", 8.0F, 6.0F, 1547968);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }
*/

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(i, j, 0, 0, this.xSize, this.ySize, 256, 256);

        int k = this.container.getMilkCapacityScaled();
        this.blit(i + 9, j + 50 - k, 198, 48 - k, 20, k + 1);
    }
}
