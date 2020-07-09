package com.budrunbun.lavalamp.items;

import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Cheese extends Item {

    public Cheese() {
        super(new Item.Properties().group(ItemGroup.FOOD).food(Foods.APPLE));
        setRegistryName("cheese");
    }
}
