package com.budrunbun.lavalamp.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class CheeseGeneratorRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CheeseGeneratorRecipe> {

    @Nonnull
    @Override
    public CheeseGeneratorRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
        final Ingredient input = Ingredient.deserialize(inputElement);

        final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));

        return new CheeseGeneratorRecipe(recipeId, input, output);
    }

    @Override
    public CheeseGeneratorRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
        final Ingredient input = Ingredient.read(buffer);
        final ItemStack output = buffer.readItemStack();
        return new CheeseGeneratorRecipe(recipeId, input, output);
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, CheeseGeneratorRecipe recipe) {
        recipe.getInput().write(buffer);
        buffer.writeItemStack(recipe.getOutput());
    }
}
