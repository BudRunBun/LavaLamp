package com.budrunbun.lavalamp.renderer;

import com.budrunbun.lavalamp.entity.GuardEntity;
import com.budrunbun.lavalamp.model.GuardHeldItemLayer;
import com.budrunbun.lavalamp.model.GuardModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class GuardRenderer extends MobRenderer<GuardEntity, GuardModel> {
    private static final ResourceLocation GUARD_TEXTURES = new ResourceLocation("lavalamp:textures/entity/guard.png");

    public GuardRenderer() {
        super(Minecraft.getInstance().getRenderManager(), new GuardModel(), 0.5F);
        this.addLayer(new GuardHeldItemLayer(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull GuardEntity entity) {
        return GUARD_TEXTURES;
    }

    @Override
    public float handleRotationFloat(@Nonnull GuardEntity livingBase, float partialTicks) {
        return super.handleRotationFloat(livingBase, partialTicks);
    }
}