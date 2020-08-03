package com.budrunbun.lavalamp.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class CheeseGeneratorRecipe implements IRecipe<IInventory> {

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;

    public CheeseGeneratorRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return "CheeseGeneratorRecipe [input = " + this.input + ", output = " + this.output + ", id = " + this.id + "]";
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World worldIn) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult( @Nonnull IInventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return new CheeseGeneratorRecipeSerializer();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return ModRecipes.CHEESE_GENERATOR_RECIPE;
    }

    @Override
    @Nonnull
    public ItemStack getIcon() {
        return new ItemStack(Blocks.STONE);
    }

    public boolean isValid(ItemStack input) {
        return this.input.test(input);
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
