package com.budrunbun.lavalamp.proxy;

import com.budrunbun.lavalamp.containers.ModContainers;
import com.budrunbun.lavalamp.screens.CheeseGeneratorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

public class ClientProxy implements IProxy {

    /*static {
        OBJLoader.INSTANCE.addDomain(LavaLamp.MOD_ID);
    }*/

    @Override
    public void init() {
        ScreenManager.registerFactory(ModContainers.CHEESE_GENERATOR_CONTAINER, CheeseGeneratorScreen::new);
    }
}
