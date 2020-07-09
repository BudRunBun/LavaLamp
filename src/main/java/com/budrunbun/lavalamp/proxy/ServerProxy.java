package com.budrunbun.lavalamp.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("This should be only run on client");
    }

    @Override
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("This should be only run on client");
    }

    @Override
    public void init() {
    }
}
