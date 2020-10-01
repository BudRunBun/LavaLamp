package com.budrunbun.lavalamp.model;

import com.budrunbun.lavalamp.entity.GuardEntity;
import com.budrunbun.lavalamp.tileentity.ShopControllerTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class GuardHeldItemLayer extends LayerRenderer<GuardEntity, GuardModel> {

    public GuardHeldItemLayer(IEntityRenderer<GuardEntity, GuardModel> entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(GuardEntity guard) {
        if (true/*guard.isAggressive()*/) {
            renderHeldItem(guard, guard.getShield(), ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
            renderHeldItem(guard, guard.getSword(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
        }
    }

    private void renderHeldItem(GuardEntity guard, ItemStack stack, ItemCameraTransforms.TransformType transformType, HandSide handSide) {
        if (!stack.isEmpty()) {
            boolean flag = handSide == HandSide.LEFT;

            GlStateManager.pushMatrix();


            if (/*!guard.isShieldEquipped() || */!flag) {
                //System.out.println("Right side");
                this.translateToHand(handSide);

                GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);

            } else {
                this.fixShieldRotation(this.getEntityModel().bipedLeftArm, guard);
            }

            GlStateManager.translatef((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(guard, stack, transformType, flag);

            GlStateManager.popMatrix();
        }
    }

    private void fixShieldRotation(RendererModel model, GuardEntity guard) {
        //GlStateManager.rotatef(-45, 0, 1, 0);
        ShopControllerTileEntity controller = (ShopControllerTileEntity) guard.world.getTileEntity(guard.getControllerPosition());

        GlStateManager.translatef((model.rotationPointX + controller.getRotationX()) * 0.0625F, (model.rotationPointY + controller.getRotationY()) * 0.0625F, (model.rotationPointZ + controller.getRotationZ()) * 0.0625F);
        GlStateManager.rotatef(-90, 0, 1, 0);
        GlStateManager.scalef(0.75F, 0.75F, 0.75F);
    }

    private void translateToHand(HandSide side) {
        this.getEntityModel().postRenderArm(0.0625F, side);
    }

    @Override
    public void render(@Nonnull GuardEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        render(entityIn);
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
