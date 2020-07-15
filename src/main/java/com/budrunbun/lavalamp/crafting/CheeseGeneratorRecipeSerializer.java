package com.budrunbun.lavalamp.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class CheeseGeneratorRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CheeseGeneratorRecipe> {

    @Override
    public CheeseGeneratorRecipe read(ResourceLocation recipeId, JsonObject json) {

        // Reads a recipe from json.

        // Reads the input. Accepts items, tags, and anything else that
        // Ingredient.deserialize can understand.
        final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
        final Ingredient input = Ingredient.deserialize(inputElement);

        // Reads the output. The common utility method in ShapedRecipe is what all vanilla
        // recipe classes use for this.
        final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));

        return new CheeseGeneratorRecipe(recipeId, input, output);
    }

    @Override
    public CheeseGeneratorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

        // Reads a recipe from a packet buffer. This code is called on the client.
        final Ingredient input = Ingredient.read(buffer);
        final ItemStack output = buffer.readItemStack();
        return new CheeseGeneratorRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public void write(PacketBuffer buffer, CheeseGeneratorRecipe recipe) {

        // Writes the recipe to a packet buffer. This is called on the server when a player
        // connects or when /reload is used.
        recipe.getInput().write(buffer);
        buffer.writeItemStack(recipe.getOutput());
    }
}