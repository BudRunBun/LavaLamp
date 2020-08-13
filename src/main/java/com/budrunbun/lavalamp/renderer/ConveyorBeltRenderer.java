package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.block.HorizontalFacingBlock;
import com.budrunbun.lavalamp.tileentity.ConveyorBeltTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ConveyorBeltRenderer extends TileEntityRenderer<ConveyorBeltTileEntity> {

    private Direction FACING;
    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    @Override
    public void render(@Nonnull ConveyorBeltTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        FACING = entity.getBlockState().get(HorizontalFacingBlock.FACING);
        float[] progress = entity.getProgress();
        boolean[] queue = entity.getQueue();
        ItemStackHandler handler = entity.getHandler();
        for (int i = 0; i < 3; i++) {
            if (!queue[i] && !handler.getStackInSlot(i).isEmpty()) {
                renderItem(x, y, z, handler.getStackInSlot(i), progress[i]);
            }
        }

    }

    @SuppressWarnings("deprecation")
    private void renderItem(double x, double y, double z, ItemStack stack, float progress) {
        GlStateManager.pushMatrix();
        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 1 / 2.0, y + 4 / 16.0, z + 1 - progress / 16.0);

                if (!itemRenderer.shouldRenderItemIn3D(stack)) {
                    GlStateManager.rotatef(270, 1, 0, 0);
                    GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                } else {
                    GlStateManager.rotatef(180, 0, 1, 0);
                    GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                }
                break;
            case SOUTH:
                GlStateManager.translated(x + 1 / 2.0, y + 4 / 16.0, z + progress / 16.0);

                if (!itemRenderer.shouldRenderItemIn3D(stack)) {
                    GlStateManager.rotatef(90, 1, 0, 0);
                    GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                } else {
                    GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                }
                break;
            case EAST:
                GlStateManager.translated(x + progress / 16, y + 4 / 16.0, z + 1 / 2.0);

                if (!itemRenderer.shouldRenderItemIn3D(stack)) {
                    GlStateManager.rotatef(270, 1, 0, 0);
                    GlStateManager.rotatef(270, 0, 0, 1);
                    GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                } else {
                    GlStateManager.rotatef(90, 0, 1, 0);
                    GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                }
                break;
            case WEST:
                GlStateManager.translated(x + 1 - progress / 16, y + 4 / 16.0, z + 1 / 2.0);

                if (!itemRenderer.shouldRenderItemIn3D(stack)) {
                    GlStateManager.rotatef(90, 1, 0, 0);
                    GlStateManager.rotatef(90, 0, 0, 1);
                    GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                } else {
                    GlStateManager.rotatef(270, 0, 1, 0);
                    GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                }
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
