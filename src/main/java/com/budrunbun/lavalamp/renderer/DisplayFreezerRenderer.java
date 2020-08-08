package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.blocks.DisplayFreezerBlock;
import com.budrunbun.lavalamp.blocks.HorizontalFacingBlock;
import com.budrunbun.lavalamp.tileEntities.DisplayFreezerTileEntity;
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
public class DisplayFreezerRenderer extends TileEntityRenderer<DisplayFreezerTileEntity> {
    /*
        |1|2|
        |0|3|
    */
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private Direction FACING;
    private boolean BOTTOM;
    private float scale;

    @Override
    public void render(DisplayFreezerTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        FACING = entity.getBlockState().get(HorizontalFacingBlock.FACING);
        BOTTOM = entity.getBlockState().get(DisplayFreezerBlock.BOTTOM);
        ItemStack Slot0 = entity.getHandler().getStackInSlot(0);
        ItemStack Slot1 = entity.getHandler().getStackInSlot(1);
        ItemStack Slot2 = entity.getHandler().getStackInSlot(2);
        ItemStack Slot3 = entity.getHandler().getStackInSlot(3);

        //if (entity.getBlockState().get(DisplayFreezerBlock.OPEN)) {

        scale = itemRenderer.shouldRenderItemIn3D(Slot0) ? 0.5F : 0.25F;
        renderBottomLeftItem(x, y, z, Slot0, scale);

        scale = itemRenderer.shouldRenderItemIn3D(Slot1) ? 0.5F : 0.25F;
        renderTopLeftItem(x, y, z, Slot1, scale);

        scale = itemRenderer.shouldRenderItemIn3D(Slot2) ? 0.5F : 0.25F;
        renderTopRightItem(x, y, z, Slot2, scale);

        scale = itemRenderer.shouldRenderItemIn3D(Slot3) ? 0.5F : 0.25F;
        renderBottomRightItem(x, y, z, Slot3, scale);
        //}
    }

    private void renderBottomLeftItem(double x, double y, double z, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        if (!BOTTOM) {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        } else {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 4 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 4 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 4 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 4 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopLeftItem(double x, double y, double z, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        if (!BOTTOM) {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 10 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 10 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 10 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 10 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        } else {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 11 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 11 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 11 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 11 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopRightItem(double x, double y, double z, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        if (!BOTTOM) {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 10 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 10 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 10 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 10 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        } else {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 11 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 11 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 11 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 11 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderBottomRightItem(double x, double y, double z, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        if (!BOTTOM) {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 2 / 16.0 + 0.5 / 16, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        } else {
            switch (FACING) {
                case NORTH:
                    GlStateManager.translated(x + 12 / 16.0, y + 4 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    GlStateManager.translated(x + 4 / 16.0, y + 4 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    break;
                case WEST:
                    GlStateManager.translated(x + 12 / 16.0, y + 4 / 16.0, z + 4 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(270, 0, 1, 0);
                    break;
                case EAST:
                    GlStateManager.translated(x + 4 / 16.0, y + 4 / 16.0, z + 12 / 16.0);
                    GlStateManager.scalef(scale, scale, scale);
                    GlStateManager.rotatef(90, 0, 1, 0);
                    break;
            }
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
