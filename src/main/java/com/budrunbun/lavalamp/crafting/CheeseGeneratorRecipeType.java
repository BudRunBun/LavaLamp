package com.budrunbun.lavalamp.crafting;

import net.minecraft.item.crafting.IRecipeType;

public class CheeseGeneratorRecipeType implements IRecipeType<CheeseGeneratorRecipe> {
    @Override
    public String toString () {

        // All vanilla recipe types return their ID in toString. I am not sure how vanilla uses
        // this, or if it does. Modded types should follow this trend for the sake of
        // consistency. I am also using it during registry to create the ResourceLocation ID.
        return "lavalamp:cheese_generator_recipe";
    }
}
