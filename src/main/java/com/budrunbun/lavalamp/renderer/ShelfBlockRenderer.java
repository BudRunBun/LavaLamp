package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.blocks.ShelfBlock;
import com.budrunbun.lavalamp.tileEntities.ShelfTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.BellModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShelfBlockRenderer extends TileEntityRenderer<ShelfTileEntity> {
    private static final ResourceLocation field_217655_c = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private static final BookModel blockModel = new BookModel();

    @Override
    public void render(ShelfTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        BlockState blockstate = tileEntityIn.getBlockState();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x + 0.5F, (float)y + 1.0F + 0.0625F, (float)z + 0.5F);
        float f = blockstate.get(ShelfBlock.FACING).rotateY().getHorizontalAngle();
        GlStateManager.rotatef(-f, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(67.5F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translatef(0.0F, -0.125F, 0.0F);
        this.bindTexture(field_217655_c);
        GlStateManager.enableCull();
        blockModel.render(0.0F, 0.1F, 0.9F, 1.2F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}