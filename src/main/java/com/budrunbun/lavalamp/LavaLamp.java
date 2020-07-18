package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.proxy.ClientProxy;
import com.budrunbun.lavalamp.proxy.IProxy;
import com.budrunbun.lavalamp.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LavaLamp.MOD_ID)
public class LavaLamp {

    public static final String MOD_ID = "lavalamp";

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public LavaLamp() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
    }
}
