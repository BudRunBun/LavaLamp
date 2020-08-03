package com.budrunbun.lavalamp.screens;

import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheeseGeneratorScreen extends ContainerScreen<CheeseGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation("lavalamp", "textures/gui/container/cheese_generator.png");

    public CheeseGeneratorScreen(CheeseGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.xSize = 184;
        this.ySize = 184;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(i, j, 0, 0, this.xSize, this.ySize, 256, 256);

        int k = this.container.getMilkCapacityScaled();
        this.blit(i + 10, j + 53 - k, 198, 53 - k, 20, k + 1);

        int l = this.container.getWaterCapacityScaled();
        this.blit(i + 154, j + 53 - l, 225, 53 - l, 20, l + 1);
        if (this.container.isWorking()) {
            int m = this.container.getLeftAndRightArrowProgressionScaled();
            //left arrow
            this.blit(i + 30, j + 25, 0, 217, m + 1, 38);
            //right arrow
            this.blit(i + 103, j + 25, 56, 217, 51, 38);
            //right arrow overlay
            this.blit(i + 103, j + 25, 113, 217, 50 - m - 1, 38);

            int o = this.container.getDownArrowProgressionScaled();
            //down arrow
            this.blit(i + 84, j + 79 - o, 56, 213 - o, 15, o + 1);

            int n = this.container.getUpArrowProgressionScaled();
            //up arrow
            this.blit(i + 85, j + 48 - n, 73, 213 - n, 13, n + 1);

            int p = this.container.getFanXPosition();
            //fan rotation
            this.blit(i + 80, j + 49, p, 191, 22, 22);
        }
    }
}
