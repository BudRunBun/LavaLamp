package com.budrunbun.lavalamp.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IUnderShopProtection {
    void onDestruction(BlockPos pos, IWorld world);
}
