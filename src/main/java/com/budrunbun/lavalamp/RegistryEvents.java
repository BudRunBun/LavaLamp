package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.blocks.*;
import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import com.budrunbun.lavalamp.crafting.CheeseGeneratorRecipeSerializer;
import com.budrunbun.lavalamp.crafting.ModRecipes;
import com.budrunbun.lavalamp.fluids.SaltyWaterFluid;
import com.budrunbun.lavalamp.items.Cheese;
import com.budrunbun.lavalamp.items.SaltyWaterBucket;
import com.budrunbun.lavalamp.tileEntities.CheeseGeneratorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        // register a new block here
        event.getRegistry().register(new CheeseBlock());
        event.getRegistry().register(new CheeseGenerator());
        event.getRegistry().register(new SaltyWaterBlock());
        event.getRegistry().register(new PillarBlock());
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        // register a new item here
        Item.Properties properties = new Item.Properties().group(ItemGroup.MISC);
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_BLOCK, properties).setRegistryName(ModBlocks.CHEESE_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_GENERATOR, properties).setRegistryName(ModBlocks.CHEESE_GENERATOR.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.SALTY_WATER_BLOCK, properties).setRegistryName(ModBlocks.SALTY_WATER_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.PILLAR_BLOCK, properties).setRegistryName(ModBlocks.PILLAR_BLOCK.getRegistryName()));
        event.getRegistry().register(new Cheese());
        event.getRegistry().register(new SaltyWaterBucket());
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
        // register a new tile entity here
        event.getRegistry().register(TileEntityType.Builder.create(CheeseGeneratorTileEntity::new, ModBlocks.CHEESE_GENERATOR).build(null).setRegistryName(LavaLamp.MOD_ID, "cheese_generator"));
    }

    @SubscribeEvent
    public static void onContainerRegistry(RegistryEvent.Register<ContainerType<?>> event) {
        // register a new container here
        event.getRegistry().register(IForgeContainerType.create((id, inv, data) -> new CheeseGeneratorContainer(id, inv, new ItemStackHandler(4), new IntArray(4))).setRegistryName("cheese_generator"));
    }

    @SubscribeEvent
    public static void onRecipesRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        // register a new container here
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ModRecipes.CHEESE_GENERATOR_RECIPE.toString()), ModRecipes.CHEESE_GENERATOR_RECIPE);
        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.getRegistry().register(new CheeseGeneratorRecipeSerializer().setRegistryName("cheese_generator_recipe"));
    }

    @SubscribeEvent
    public static void onFluidRegistry(RegistryEvent.Register<Fluid> event) {
        // register a new fluid here
        event.getRegistry().register(new SaltyWaterFluid.Flowing().setRegistryName("salty_water_flowing"));
        event.getRegistry().register(new SaltyWaterFluid.Source().setRegistryName("salty_water"));
    }
}