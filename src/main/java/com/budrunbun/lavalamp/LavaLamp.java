package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.proxy.ClientProxy;
import com.budrunbun.lavalamp.proxy.IProxy;
import com.budrunbun.lavalamp.proxy.ServerProxy;
import com.budrunbun.lavalamp.renderer.ShelfBlockRenderer;
import com.budrunbun.lavalamp.tileEntities.ShelfTileEntity;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sun.net.ResourceManager;

@Mod(LavaLamp.MOD_ID)
public class LavaLamp {

    public static final String MOD_ID = "lavalamp";

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public LavaLamp() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        proxy.init();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(ShelfTileEntity.class, new ShelfBlockRenderer());
    }
}
