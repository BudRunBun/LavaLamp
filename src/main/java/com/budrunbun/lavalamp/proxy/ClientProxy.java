package com.budrunbun.lavalamp.proxy;

import com.budrunbun.lavalamp.container.ModContainers;
import com.budrunbun.lavalamp.renderer.ConveyorBeltRenderer;
import com.budrunbun.lavalamp.renderer.DisplayFreezerRenderer;
import com.budrunbun.lavalamp.renderer.ShelfBlockRenderer;
import com.budrunbun.lavalamp.screen.CheeseGeneratorScreen;
import com.budrunbun.lavalamp.tileentity.ConveyorBeltTileEntity;
import com.budrunbun.lavalamp.tileentity.DisplayFreezerTileEntity;
import com.budrunbun.lavalamp.tileentity.ShelfTileEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ModContainers.CHEESE_GENERATOR_CONTAINER, CheeseGeneratorScreen::new);
        ClientRegistry.bindTileEntitySpecialRenderer(ShelfTileEntity.class, new ShelfBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DisplayFreezerTileEntity.class, new DisplayFreezerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(ConveyorBeltTileEntity.class, new ConveyorBeltRenderer());
    }
    /*static {
        OBJLoader.INSTANCE.addDomain(LavaLamp.MOD_ID);
    }*/

}
