package com.budrunbun.lavalamp.crafting;

import com.budrunbun.lavalamp.LavaLamp;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CheeseGeneratorRecipe implements IRecipe<IInventory> {


    private final Ingredient input;
    private final ItemStack output;
    private final Block block;
    private final ResourceLocation id;

    public CheeseGeneratorRecipe(ResourceLocation id, Ingredient input, ItemStack output, Block block) {

        this.id = id;
        this.input = input;
        this.output = output;
        this.block = block;

        // This output is not required, but it can be used to detect when a recipe has been
        // loaded into the game.
        System.out.println("Loaded " + this.toString());
    }

    @Override
    public String toString () {

        // Overriding toString is not required, it's just useful for debugging.
        return "CheeseGeneratorRecipe [input=" + this.input + ", output=" + this.output + ", block=" + this.block.getRegistryName() + ", id=" + this.id + "]";
    }

    @Override
    public boolean matches (IInventory inv, World worldIn) {

        // This method is ignored by our custom recipe system, and only has partial
        // functionality. isValid is used instead.
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult (IInventory inv) {

        // This method is ignored by our custom recipe system. getRecipeOutput().copy() is used
        // instead.
        return this.output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput () {

        return this.output;
    }

    @Override
    public ResourceLocation getId () {

        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer () {

        return new CheeseGeneratorRecipeSerializer();
    }

    @Override
    public IRecipeType<?> getType () {

        return ModRecipes.CHEESE_GENERATOR_RECIPE;
    }

    @Override
    public ItemStack getIcon () {

        return new ItemStack(Blocks.STONE);
    }

    public boolean isValid (ItemStack input, Block block) {

        return this.input.test(input) && this.block == block;
    }

    public Block getBlock() {
        return block;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
