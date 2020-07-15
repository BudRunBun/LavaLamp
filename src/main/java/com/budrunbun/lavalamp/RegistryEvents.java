package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.blocks.CheeseBlock;
import com.budrunbun.lavalamp.blocks.CheeseGenerator;
import com.budrunbun.lavalamp.blocks.ModBlocks;
import com.budrunbun.lavalamp.containers.CheeseGeneratorContainer;
import com.budrunbun.lavalamp.crafting.CheeseGeneratorRecipeSerializer;
import com.budrunbun.lavalamp.crafting.ModRecipes;
import com.budrunbun.lavalamp.items.Cheese;
import com.budrunbun.lavalamp.tileEntities.CheeseGeneratorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        // register a new block here
        event.getRegistry().register(new CheeseBlock());
        event.getRegistry().register(new CheeseGenerator());
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        // register a new item here
        Item.Properties properties = new Item.Properties().group(ItemGroup.MISC);
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_BLOCK, properties).setRegistryName(ModBlocks.CHEESE_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_GENERATOR, properties).setRegistryName(ModBlocks.CHEESE_GENERATOR.getRegistryName()));
        event.getRegistry().register(new Cheese());
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
        // register a new tile entity here
        event.getRegistry().register(TileEntityType.Builder.create(CheeseGeneratorTileEntity::new, ModBlocks.CHEESE_GENERATOR).build(null).setRegistryName(LavaLamp.MOD_ID, "cheese_generator"));
    }

    @SubscribeEvent
    public static void onContainerEntityRegistry(RegistryEvent.Register<ContainerType<?>> event) {
        // register a new container here
        event.getRegistry().register(IForgeContainerType.create(CheeseGeneratorContainer::new).setRegistryName("cheese_generator"));
    }

    @SubscribeEvent
    public static void onRecipesRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        // register a new container here
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ModRecipes.CHEESE_GENERATOR_RECIPE.toString()), ModRecipes.CHEESE_GENERATOR_RECIPE);
        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.getRegistry().register(new CheeseGeneratorRecipeSerializer().setRegistryName("cheese_generator_recipe"));
    }
}