package com.budrunbun.lavalamp.proxy;

import com.budrunbun.lavalamp.LavaLamp;
import com.budrunbun.lavalamp.containers.ModContainers;
import com.budrunbun.lavalamp.screens.CheeseGeneratorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.lang.reflect.Method;

public class ClientProxy implements IProxy {

    static {
        OBJLoader.INSTANCE.addDomain(LavaLamp.MOD_ID);
    }

    @Override
    public void init() {
        ScreenManager.registerFactory(ModContainers.CHEESE_GENERATOR_CONTAINER, CheeseGeneratorScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
