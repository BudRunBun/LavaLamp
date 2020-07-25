package com.budrunbun.lavalamp.containers;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class ModContainers {

    @ObjectHolder("lavalamp:cheese_generator")
    public static final ContainerType<CheeseGeneratorContainer> CHEESE_GENERATOR_CONTAINER = null;

    @ObjectHolder("lavalamp:shelf_block")
    public static final ContainerType<ShelfContainer> SHELF_CONTAINER = null;
}
