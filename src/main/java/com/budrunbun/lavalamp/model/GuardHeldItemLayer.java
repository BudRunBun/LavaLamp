package com.budrunbun.lavalamp.model;

import com.budrunbun.lavalamp.entity.GuardEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class GuardHeldItemLayer extends LayerRenderer<GuardEntity, GuardModel> {
    private final IEntityRenderer<GuardEntity, GuardModel> entityRenderer;

    public GuardHeldItemLayer(IEntityRenderer<GuardEntity, GuardModel> entityRendererIn) {
        super(entityRendererIn);
        this.entityRenderer = entityRendererIn;
    }

    public void render(GuardEntity guard) {
        if (guard.isAggressive()) {
            ItemStack shield = guard.getHeldItemOffhand();
            ItemStack sword = guard.getHeldItemMainhand();
            int angle = guard.getRevengeTarget() != null ? (guard.getRevengeTarget().getDistance(guard) < 2 ? 90 : 0) : 0;
            renderHeldItem(guard, shield, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, angle);
            renderHeldItem(guard, sword, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, 0);
        }
    }

    private void renderHeldItem(LivingEntity guard, ItemStack stack, ItemCameraTransforms.TransformType transformType, HandSide handSide, int degrees) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            if (guard.shouldRenderSneaking()) {
                GlStateManager.translatef(0.0F, 0.2F, 0.0F);
            }
            System.out.println("Rotate for " + degrees + " degrees");
            translateToHand(handSide);
            GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(degrees, 0, 1, 0);
            boolean flag = handSide == HandSide.LEFT;
            GlStateManager.translatef((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(guard, stack, transformType, flag);
            GlStateManager.popMatrix();
        }
    }

    protected void translateToHand(HandSide side) {
        ((IHasArm) this.getEntityModel()).postRenderArm(0.0625F, side);
    }

    @Override
    @Nonnull
    public GuardModel getEntityModel() {
        return this.entityRenderer.getEntityModel();
    }

    @Override
    public void render(@Nonnull GuardEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        render(entityIn);
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
