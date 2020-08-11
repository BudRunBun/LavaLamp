package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.tileentity.ConveyorBeltTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

public class ConveyorBeltRenderer extends TileEntityRenderer<ConveyorBeltTileEntity> {

    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    @Override
    public void render(@Nonnull ConveyorBeltTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {

        float[] progress = entity.getProgress();

        for (int i = 0; i < 3; i++) {
            renderItem(x, y, z, entity.getHandler().getStackInSlot(i), progress[i]);
        }
    }

    private synchronized void renderItem(double x, double y, double z, ItemStack stack, float progress) {
        GlStateManager.pushMatrix();

        GlStateManager.translated(x + 1 / 2.0, y + 4 / 16.0, z + progress / 16.0);
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        GlStateManager.rotatef(180, 0, 1, 0);

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
