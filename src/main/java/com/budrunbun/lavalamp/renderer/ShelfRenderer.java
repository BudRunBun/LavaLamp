package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.block.HorizontalFacingBlock;
import com.budrunbun.lavalamp.tileentity.ShelfTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShelfRenderer extends TileEntityRenderer<ShelfTileEntity> {
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private Direction FACING;
    private float scale;
    private float offset;

    @Override
    public void render(ShelfTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        FACING = entity.getBlockState().get(HorizontalFacingBlock.FACING);
        ItemStack Slot0 = entity.getHandler().getStackInSlot(0);
        ItemStack Slot1 = entity.getHandler().getStackInSlot(1);
        ItemStack Slot2 = entity.getHandler().getStackInSlot(2);
        ItemStack Slot3 = entity.getHandler().getStackInSlot(3);
        ItemStack Slot4 = entity.getHandler().getStackInSlot(4);
        ItemStack Slot5 = entity.getHandler().getStackInSlot(5);
        ItemStack Slot6 = entity.getHandler().getStackInSlot(6);
        ItemStack Slot7 = entity.getHandler().getStackInSlot(7);

        scale = itemRenderer.shouldRenderItemIn3D(Slot0) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot0) ? 0 : 1.5F / 32.0F;
        renderBottomLeftItem(x, y, z, Slot0, scale, 0, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot1) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot1) ? 0 : 1.5F / 32.0F;
        renderTopLeftItem(x, y, z, Slot1, scale, 0, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot2) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot2) ? 0 : 1.5F / 32.0F;
        renderTopRightItem(x, y, z, Slot2, scale, 0, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot3) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot3) ? 0 : 1.5F / 32.0F;
        renderBottomRightItem(x, y, z, Slot3, scale, 0, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot4) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot4) ? 0 : 1.5F / 32.0F;
        renderBottomLeftItem(x, y, z, Slot4, scale, -0.5F, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot5) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot5) ? 0 : 1.5F / 32.0F;
        renderTopLeftItem(x, y, z, Slot5, scale, -0.5F, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot6) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot6) ? 0 : 1.5F / 32.0F;
        renderTopRightItem(x, y, z, Slot6, scale, -0.5F, offset);

        scale = itemRenderer.shouldRenderItemIn3D(Slot7) ? 0.5F : 0.35F;
        offset = itemRenderer.shouldRenderItemIn3D(Slot7) ? 0 : 1.5F / 32.0F;
        renderBottomRightItem(x, y, z, Slot7, scale, -0.5F, offset);
    }

    private void renderBottomLeftItem(double x, double y, double z, ItemStack stack, float scale, float offset1, float offset2) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 4 / 16.0, y + 3 / 16.0 + offset2, z + 12 / 16.0 + offset1);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 12 / 16.0, y + 3 / 16.0 + offset2, z + 4 / 16.0 - offset1);
                GlStateManager.scalef(scale, scale, scale);
                break;
            case WEST:
                GlStateManager.translated(x + 12 / 16.0 + offset1, y + 3 / 16.0 + offset2, z + 12 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.translated(x + 4 / 16.0 - offset1, y + 3 / 16.0 + offset2, z + 4 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopLeftItem(double x, double y, double z, ItemStack stack, float scale, float offset1, float offset2) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 4 / 16.0, y + 10.5 / 16.0 + offset2, z + 12 / 16.0 + offset1);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 12 / 16.0, y + 10.5 / 16.0 + offset2, z + 4 / 16.0 - offset1);
                GlStateManager.scalef(scale, scale, scale);
                break;
            case WEST:
                GlStateManager.translated(x + 12 / 16.0 + offset1, y + 10.5 / 16.0 + offset2, z + 12 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.translated(x + 4 / 16.0 - offset1, y + 10.5 / 16.0 + offset2, z + 4 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopRightItem(double x, double y, double z, ItemStack stack, float scale, float offset1, float offset2) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 12 / 16.0, y + 10.5 / 16.0 + offset2, z + 12 / 16.0 + offset1);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 4 / 16.0, y + 10.5 / 16.0 + offset2, z + 4 / 16.0 - offset1);
                GlStateManager.scalef(scale, scale, scale);
                break;
            case WEST:
                GlStateManager.translated(x + 12 / 16.0 + offset1, y + 10.5 / 16.0 + offset2, z + 4 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.translated(x + 4 / 16.0 - offset1, y + 10.5 / 16.0 + offset2, z + 12 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderBottomRightItem(double x, double y, double z, ItemStack stack, float scale, float offset1, float offset2) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 12 / 16.0, y + 3 / 16.0 + offset2, z + 12 / 16.0 + offset1);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 4 / 16.0, y + 3 / 16.0 + offset2, z + 4 / 16.0 - offset1);
                GlStateManager.scalef(scale, scale, scale);
                break;
            case WEST:
                GlStateManager.translated(x + 12 / 16.0 + offset1, y + 3 / 16.0 + offset2, z + 4 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.translated(x + 4 / 16.0 - offset1, y + 3 / 16.0 + offset2, z + 12 / 16.0);
                GlStateManager.scalef(scale, scale, scale);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
