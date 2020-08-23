package com.budrunbun.lavalamp.proxy;

import com.budrunbun.lavalamp.container.ModContainers;
import com.budrunbun.lavalamp.entity.GuardEntity;
import com.budrunbun.lavalamp.entity.ModEntities;
import com.budrunbun.lavalamp.renderer.*;
import com.budrunbun.lavalamp.screen.CheeseGeneratorScreen;
import com.budrunbun.lavalamp.tileentity.ConveyorBeltTileEntity;
import com.budrunbun.lavalamp.tileentity.DisplayFreezerTileEntity;
import com.budrunbun.lavalamp.tileentity.DisplayStandTileEntity;
import com.budrunbun.lavalamp.tileentity.ShelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        Minecraft.getInstance().getRenderManager().register(GuardEntity.class, new GuardRenderer());

        ScreenManager.registerFactory(ModContainers.CHEESE_GENERATOR_CONTAINER, CheeseGeneratorScreen::new);

        ClientRegistry.bindTileEntitySpecialRenderer(DisplayStandTileEntity.class, new DisplayStandRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DisplayFreezerTileEntity.class, new DisplayFreezerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(ConveyorBeltTileEntity.class, new ConveyorBeltRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(ShelfTileEntity.class, new ShelfRenderer());
    }
    /*static {
        OBJLoader.INSTANCE.addDomain(LavaLamp.MOD_ID);
    }*/

}
