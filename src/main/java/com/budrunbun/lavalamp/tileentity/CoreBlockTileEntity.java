package com.budrunbun.lavalamp.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.core.Core;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CoreBlockTileEntity extends TileEntity implements ITickableTileEntity {
    public CoreBlockTileEntity() {
        super(ModTileEntities.CORE_TE);
    }

    @Override
    public void tick() {
        PlacementSettings placementsettings = new PlacementSettings();
        placementsettings.setBoundingBox(new MutableBoundingBox(new int[]{0, 0, 0, 10, 10, 10}));
        placementsettings.setRotation(Rotation.NONE);
        placementsettings.func_215223_c(true);
        placementsettings.setIgnoreEntities(true);
        placementsettings.addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        placementsettings.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);

        try {
            check(this.pos, placementsettings);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean check(BlockPos pos, PlacementSettings placementIn) throws NoSuchFieldException, IllegalAccessException {
        Template template = new Template();
        if (!this.world.isRemote) {
            template = ((ServerWorld) this.world).getStructureTemplateManager().getTemplate(new ResourceLocation("lavalamp:shop/shop_base"));
        }
        Field field = null;
        try {
            field = template.getClass().getDeclaredField("blocks");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);

        List<List<Template.BlockInfo>> blocks = null;
        try {
            blocks = (List<List<Template.BlockInfo>>) field.get(template);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (!blocks.isEmpty()) {
            List<Template.BlockInfo> list = placementIn.func_204764_a(blocks, pos);
            for (Template.BlockInfo template$blockInfo : template.processBlockInfos(template, this.world, pos, placementIn, list)) {
                BlockPos blockpos = template$blockInfo.pos;
                BlockState state = template$blockInfo.state;

                if (this.world.getBlockState(blockpos).getBlock() != state.getBlock()) {
                    System.out.println("Invalid block at: X: " + blockpos.getX() + " Y: " + blockpos.getY() + " Z: " + blockpos.getZ() + ". Required: " + state.getBlock().getRegistryName().toString() + " Provided: " + this.world.getBlockState(blockpos).getBlock().getRegistryName());
                    return false;
                }

            }
        }

        return true;
    }
}
