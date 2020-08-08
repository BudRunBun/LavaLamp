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
public class ShelfBlockRenderer extends TileEntityRenderer<ShelfTileEntity> {
    /*
    |1|3|
    |0|2|
    */
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private Direction FACING;

    @Override
    public void render(ShelfTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        FACING = entity.getBlockState().get(HorizontalFacingBlock.FACING);

        ItemStack slot0 = entity.getHandler().getStackInSlot(0);
        ItemStack slot1 = entity.getHandler().getStackInSlot(1);
        ItemStack slot2 = entity.getHandler().getStackInSlot(2);
        ItemStack slot3 = entity.getHandler().getStackInSlot(3);

        if (!slot0.isEmpty()) {
            renderBottomLeftItem(x, y, z, slot0);
        }
        if (!slot1.isEmpty()) {
            renderTopLeftItem(x, y, z, slot1);
        }
        if (!slot2.isEmpty()) {
            renderBottomRightItem(x, y, z, slot2);
        }
        if (!slot3.isEmpty()) {
            renderTopRightItem(x, y, z, slot3);
        }
    }

    private void renderBottomLeftItem(double x, double y, double z, ItemStack stack) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 0.25, y + 0.25, z + 0.45);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 0.75, y + 0.25, z + 0.45 + 0.0625);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                break;
            case EAST:
                GlStateManager.translated(x + 0.45 + 0.0625, y + 0.25, z + 0.25);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
            default:
                GlStateManager.translated(x + 0.45, y + 0.25, z + 0.75);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
        }


        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopLeftItem(double x, double y, double z, ItemStack stack) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 0.25, y + 0.75, z + 0.45);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 0.75, y + 0.75, z + 0.45 + 0.0625);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                break;
            case EAST:
                GlStateManager.translated(x + 0.45 + 0.0625, y + 0.75, z + 0.25);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
            default:
                GlStateManager.translated(x + 0.45, y + 0.75, z + 0.75);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderTopRightItem(double x, double y, double z, ItemStack stack) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 0.75, y + 0.75, z + 0.45);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 0.25, y + 0.75, z + 0.45 + 0.0625);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                break;
            case EAST:
                GlStateManager.translated(x + 0.45 + 0.0625, y + 0.75, z + 0.75);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
            default:
                GlStateManager.translated(x + 0.45, y + 0.75, z + 0.25);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    private void renderBottomRightItem(double x, double y, double z, ItemStack stack) {
        GlStateManager.pushMatrix();

        switch (FACING) {
            case NORTH:
                GlStateManager.translated(x + 0.75, y + 0.25, z + 0.45);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(180, 0, 1, 0);
                break;
            case SOUTH:
                GlStateManager.translated(x + 0.25, y + 0.25, z + 0.45 + 0.0625);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                break;
            case EAST:
                GlStateManager.translated(x + 0.45 + 0.0625, y + 0.25, z + 0.75);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(90, 0, 1, 0);
                break;
            default:
                GlStateManager.translated(x + 0.45, y + 0.25, z + 0.25);
                GlStateManager.scalef(0.35F, 0.35F, 0.35F);
                GlStateManager.rotatef(270, 0, 1, 0);
                break;
        }

        this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}