package com.budrunbun.lavalamp.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {
    @ObjectHolder("lavalamp:cheese_generator")
    public static final TileEntityType<CheeseGeneratorTileEntity> CHEESE_GENERATOR_TE = null;

    @ObjectHolder("lavalamp:display_stand")
    public static final TileEntityType<DisplayStandTileEntity> DISPLAY_STAND_TE = null;

    @ObjectHolder("lavalamp:player_sensor")
    public static final TileEntityType<PlayerSensorTileEntity> PLAYER_SENSOR_TE = null;

    @ObjectHolder("lavalamp:display_freezer")
    public static final TileEntityType<DisplayFreezerTileEntity> DISPLAY_FREEZER_TE = null;

    @ObjectHolder("lavalamp:conveyor_belt")
    public static final TileEntityType<ConveyorBeltTileEntity> CONVEYOR_BELT_TE = null;

    @ObjectHolder("lavalamp:shelf")
    public static final TileEntityType<ShelfTileEntity> SHELF_TE = null;

    @ObjectHolder("lavalamp:core")
    public static final TileEntityType<CoreBlockTileEntity> CORE_TE = null;
}
