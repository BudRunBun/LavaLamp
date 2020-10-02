package com.budrunbun.lavalamp.model;

import com.budrunbun.lavalamp.entity.GuardEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuardModel extends BipedModel<GuardEntity> {
    public GuardModel() {
        this(0.0F, false);
    }

    public GuardModel(float modelSize, boolean p_i1168_2_) {
        super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    public void setRotationAngles(@Nonnull GuardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        boolean flag1 = entityIn.isAggressive();
        boolean flag2 = entityIn.isShieldEquipped();
        float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
        this.bipedLeftArm.rotateAngleZ = flag2 ? (float) Math.PI / 4 : 0;
        this.bipedRightArm.rotateAngleZ = 0;
        //this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
        //this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
        //this.bipedRightArm.rotateAngleX = (float) -Math.PI / 2 * (limbSwingAmount);
        if (flag1) {
            this.bipedRightArm.rotateAngleX = (float) Math.PI * (1 - entityIn.getSwingProgress(Minecraft.getInstance().getRenderPartialTicks()) / 2);
        }
        //this.bipedRightArm.rotateAngleX = flag1 ? -(float) Math.PI / 2.25F : 0F;
        this.bipedLeftArm.rotateAngleX = flag2 ? -(float) Math.PI / 4 : 0F;
        this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
    }
}
