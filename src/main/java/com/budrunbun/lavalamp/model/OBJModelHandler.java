/*package com.budrunbun.lavalamp.model;

import com.budrunbun.lavalamp.LavaLamp;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaLamp.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBJModelHandler {

    private static final TRSRTransformation THIRD_PERSON_BLOCK = ForgeBlockStateV1.Transforms.convert(0, 2.5f, 0, 75, 45, 0, 0.375f);
    private static final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> BLOCK_TRANSFORMS = ImmutableMap.<ItemCameraTransforms.TransformType, TRSRTransformation>builder()
            .put(ItemCameraTransforms.TransformType.GUI, ForgeBlockStateV1.Transforms.convert(0, 0, 0, 30, 225, 0, 0.625f))
            .put(ItemCameraTransforms.TransformType.GROUND, ForgeBlockStateV1.Transforms.convert(0, 3, 0, 0, 0, 0, 0.25f))
            .put(ItemCameraTransforms.TransformType.FIXED, ForgeBlockStateV1.Transforms.convert(0, 0, 0, 0, 0, 0, 0.5f))
            .put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_BLOCK)
            .put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, ForgeBlockStateV1.Transforms.leftify(THIRD_PERSON_BLOCK))
            .put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, ForgeBlockStateV1.Transforms.convert(0, 0, 0, 0, 45, 0, 0.4f))
            .put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, ForgeBlockStateV1.Transforms.convert(0, 0, 0, 0, 225, 0, 0.4f))
            .build();

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        try {
            IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("lavalamp:block/_ramp_block.obj"));

            if (model instanceof OBJModel) {
                IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

                IBakedModel bakedInvModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
                        new BasicState(model.getDefaultState(), true), DefaultVertexFormats.ITEM);
                bakedInvModel = new PerspectiveMapWrapper(bakedInvModel, BLOCK_TRANSFORMS);

                event.getModelRegistry().put(new ModelResourceLocation("lavalamp:ramp_block", ""), bakedModel);
                event.getModelRegistry().put(new ModelResourceLocation("lavalamp:ramp_block", "inventory"), bakedInvModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void onPreTextureStitch(TextureStitchEvent.Pre event) {
        event.addSprite(
                ResourceLocation.tryCreate("lavalamp:block/gray")
        );
    }
}*/