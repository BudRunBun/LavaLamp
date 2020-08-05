package com.budrunbun.lavalamp.tileEntities;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {
    @ObjectHolder("lavalamp:cheese_generator")
    public static final TileEntityType<CheeseGeneratorTileEntity> CHEESE_GENERATOR_TE = null;

    @ObjectHolder("lavalamp:shelf_block")
    public static final TileEntityType<ShelfTileEntity> SHELF_TE = null;

    @ObjectHolder("lavalamp:player_sensor")
    public static final TileEntityType<PlayerSensorTileEntity> PLAYER_SENSOR_TE = null;
}
