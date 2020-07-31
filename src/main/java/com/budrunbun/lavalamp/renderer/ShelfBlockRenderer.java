package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.blocks.HorizontalFacingBlock;
import com.budrunbun.lavalamp.tileEntities.ShelfTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShelfBlockRenderer extends TileEntityRenderer<ShelfTileEntity> {
    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private Direction FACING;

    @Override
    public void render(ShelfTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        FACING = entity.getBlockState().get(HorizontalFacingBlock.FACING);
        ItemStack itemStack = new ItemStack(Items.WHEAT);
        renderBottomLeftItem(x, y, z, itemStack);
        itemStack = new ItemStack(Items.APPLE);
        renderTopLeftItem(x, y, z, itemStack);
        itemStack = new ItemStack(Items.FEATHER);
        renderBottomRightItem(x, y, z, itemStack);
        itemStack = new ItemStack(Items.LAVA_BUCKET);
        renderTopRightItem(x, y, z, itemStack);
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