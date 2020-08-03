package com.budrunbun.lavalamp.items;

import com.budrunbun.lavalamp.fluids.SaltyWaterFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class SaltyWaterBucket extends BucketItem {

    public SaltyWaterBucket() {
        super(SaltyWaterFluid.Source::new, new Item.Properties().group(ItemGroup.MISC));
        setRegistryName("salty_water_bucket");
    }
}
