package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.block.*;
import com.budrunbun.lavalamp.container.CheeseGeneratorContainer;
import com.budrunbun.lavalamp.entity.GuardEntity;
import com.budrunbun.lavalamp.entity.ModEntities;
import com.budrunbun.lavalamp.fluid.SaltyWaterFluid;
import com.budrunbun.lavalamp.item.Cheese;
import com.budrunbun.lavalamp.item.SaltyWaterBucket;
import com.budrunbun.lavalamp.tileentity.*;
import com.budrunbun.lavalamp.worldgen.ShopStructure;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IntArray;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    public static final ItemGroup LAVALAMP = new ItemGroup(12, "lavalamp") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CHEESE_GENERATOR);
        }
    };

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
        // register a new feature here
        event.getRegistry().register(EntityType.Builder.create(GuardEntity::new, EntityClassification.MISC).size(0.6F, 1.95F).build("guard").setRegistryName(LavaLamp.MOD_ID,"guard"));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        // register a new block here
        event.getRegistry().register(new CheeseBlock());
        event.getRegistry().register(new CheeseGeneratorBlock());
        event.getRegistry().register(new SaltyWaterBlock());
        event.getRegistry().register(new PillarBlock());
        event.getRegistry().register(new RampBlock());
        event.getRegistry().register(new AshGrayConcreteBlock());
        event.getRegistry().register(new DisplayStandBlock());
        event.getRegistry().register(new FloorBlock());
        event.getRegistry().register(new IronFloorBlock());
        event.getRegistry().register(new GrillCeilingBlock());
        event.getRegistry().register(new GlassDoorBlock());
        event.getRegistry().register(new PlayerSensorBlock());
        event.getRegistry().register(new DisplayFreezerBlock());
        event.getRegistry().register(new PendantLampBlock());
        event.getRegistry().register(new ConcreteStairsBlock());
        event.getRegistry().register(new OfficeCeilingLampBlock());
        event.getRegistry().register(new ConveyorBeltBlock());
        event.getRegistry().register(new PipeBlock());
        event.getRegistry().register(new StructuralChannelBlock());
        event.getRegistry().register(new ShelfBlock());
        event.getRegistry().register(new LabelBlock());
        event.getRegistry().register(new LogoBlock());
        event.getRegistry().register(new CashBoxBlock());
        event.getRegistry().register(new CoreBlock());
        event.getRegistry().register(new ShopControllerBlock());
    }

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        // register a new block item here
        Item.Properties properties = new Item.Properties().group(LAVALAMP);
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_BLOCK, properties).setRegistryName(ModBlocks.CHEESE_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CHEESE_GENERATOR, properties).setRegistryName(ModBlocks.CHEESE_GENERATOR.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.PILLAR_BLOCK, properties).setRegistryName(ModBlocks.PILLAR_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.RAMP_BLOCK, properties).setRegistryName(ModBlocks.RAMP_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.ASH_GRAY_CONCRETE_BLOCK, properties).setRegistryName(ModBlocks.ASH_GRAY_CONCRETE_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.DISPLAY_STAND_BLOCK, properties).setRegistryName(ModBlocks.DISPLAY_STAND_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.FLOOR_BLOCK, properties).setRegistryName(ModBlocks.FLOOR_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.IRON_FLOOR_BLOCK, properties).setRegistryName(ModBlocks.IRON_FLOOR_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.GRILL_CEILING_BLOCK, properties).setRegistryName(ModBlocks.GRILL_CEILING_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.GLASS_DOOR_BLOCK, properties).setRegistryName(ModBlocks.GLASS_DOOR_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.PLAYER_SENSOR_BLOCK, properties).setRegistryName(ModBlocks.PLAYER_SENSOR_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.DISPLAY_FREEZER_BLOCK, properties).setRegistryName(ModBlocks.DISPLAY_FREEZER_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.PENDANT_LAMP_BLOCK, properties).setRegistryName(ModBlocks.PENDANT_LAMP_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CONCRETE_STAIRS_BLOCK, properties).setRegistryName(ModBlocks.CONCRETE_STAIRS_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.OFFICE_CEILING_LAMP_BLOCK, properties).setRegistryName(ModBlocks.OFFICE_CEILING_LAMP_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CONVEYOR_BELT_BLOCK, properties).setRegistryName(ModBlocks.CONVEYOR_BELT_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.PIPE_BLOCK, properties).setRegistryName(ModBlocks.PIPE_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.STRUCTURAL_CHANNEL_BLOCK, properties).setRegistryName(ModBlocks.STRUCTURAL_CHANNEL_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.SHELF_BLOCK, properties).setRegistryName(ModBlocks.SHELF_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.LABEL_BLOCK, properties).setRegistryName(ModBlocks.LABEL_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.LOGO_BLOCK, properties).setRegistryName(ModBlocks.LOGO_BLOCK.getRegistryName()));
        event.getRegistry().register(new BlockItem(ModBlocks.CASH_BOX_BLOCK, properties).setRegistryName(ModBlocks.CASH_BOX_BLOCK.getRegistryName()));

        //register a new item here
        event.getRegistry().register(new Cheese());
        event.getRegistry().register(new SaltyWaterBucket());
        event.getRegistry().register(new SpawnEggItem(ModEntities.GUARD_ENTITY, 7551423,456785, properties).setRegistryName("guard_spawn_egg"));
    }

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
        // register a new tile entity here
        event.getRegistry().register(TileEntityType.Builder.create(CheeseGeneratorTileEntity::new, ModBlocks.CHEESE_GENERATOR).build(null).setRegistryName(LavaLamp.MOD_ID, "cheese_generator"));
        event.getRegistry().register(TileEntityType.Builder.create(DisplayStandTileEntity::new, ModBlocks.DISPLAY_STAND_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "display_stand"));
        event.getRegistry().register(TileEntityType.Builder.create(PlayerSensorTileEntity::new, ModBlocks.PLAYER_SENSOR_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "player_sensor"));
        event.getRegistry().register(TileEntityType.Builder.create(DisplayFreezerTileEntity::new, ModBlocks.DISPLAY_FREEZER_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "display_freezer"));
        event.getRegistry().register(TileEntityType.Builder.create(ConveyorBeltTileEntity::new, ModBlocks.CONVEYOR_BELT_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "conveyor_belt"));
        event.getRegistry().register(TileEntityType.Builder.create(ShelfTileEntity::new, ModBlocks.SHELF_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "shelf"));
        event.getRegistry().register(TileEntityType.Builder.create(CoreBlockTileEntity::new, ModBlocks.CORE_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "core"));
        event.getRegistry().register(TileEntityType.Builder.create(ShopControllerTileEntity::new, ModBlocks.SHOP_CONTROLLER_BLOCK).build(null).setRegistryName(LavaLamp.MOD_ID, "shop_controller"));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onContainerRegistry(RegistryEvent.Register<ContainerType<?>> event) {
        // register a new container here
        event.getRegistry().register(IForgeContainerType.create((id, inv, data) -> new CheeseGeneratorContainer(id, inv, new ItemStackHandler(4), new IntArray(4))).setRegistryName("cheese_generator"));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onFluidRegistry(RegistryEvent.Register<Fluid> event) {
        // register a new fluid here
        event.getRegistry().register(new SaltyWaterFluid.Flowing().setRegistryName("salty_water_flowing"));
        event.getRegistry().register(new SaltyWaterFluid.Source().setRegistryName("salty_water"));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onFeatureRegistry(RegistryEvent.Register<Feature<?>> event) {
        // register a new feature here
        event.getRegistry().register(new ShopStructure(NoFeatureConfig::deserialize).setRegistryName("shop"));
    }
}