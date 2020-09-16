package com.budrunbun.lavalamp.model;

import com.budrunbun.lavalamp.entity.GuardEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Method;

public class GuardHeldItemLayer extends HeldItemLayer<GuardEntity, GuardModel> {
    public GuardHeldItemLayer(IEntityRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(GuardEntity guard, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (guard.isAggressive()) {
            ItemStack shield = guard.getHeldItemOffhand();
            ItemStack sword = guard.getHeldItemMainhand();
            Method renderHeldItemMethod = null;
            try {
                renderHeldItemMethod = HeldItemLayer.class.getDeclaredMethod("renderHeldItem", LivingEntity.class, ItemStack.class, ItemCameraTransforms.TransformType.class, HandSide.class);
                renderHeldItemMethod.setAccessible(true);
            } catch (Throwable t) {
                return;
            }
            if (!shield.isEmpty()) {
                GlStateManager.pushMatrix();
                int angle = guard.getRevengeTarget() != null ? (guard.getRevengeTarget().getDistance(guard) < 2 ? 90 : 0) : 0;
                GlStateManager.rotated(90, 0, 1, 0);
                try {
                    renderHeldItemMethod.invoke(guard, shield, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
                } catch (Throwable t) {
                    return;
                }
                GlStateManager.popMatrix();
            }

            if (!sword.isEmpty()) {
                GlStateManager.pushMatrix();
                try {
                    renderHeldItemMethod.invoke(guard, shield, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
                } catch (Throwable t) {
                    return;
                }
                GlStateManager.popMatrix();
            }
        }
    }
}
