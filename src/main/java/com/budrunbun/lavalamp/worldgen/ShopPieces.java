package com.budrunbun.lavalamp.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.*;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class ShopPieces {
    private static final IStructurePieceType SHOP = IStructurePieceType.register(Shop::new, "shop");

    public static void build(ChunkGenerator<?> generator, TemplateManager templateManager, BlockPos pos, List<StructurePiece> pieces, SharedSeedRandom seed) {
        JigsawManager.func_214889_a(new ResourceLocation("lavalamp:shop/base"), 1, ShopPieces.Shop::new, generator, templateManager, pos, pieces, seed);
    }

    static {
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("lavalamp:shop/base"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("lavalamp:shop/shop_base"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
    }

    public static class Shop extends AbstractVillagePiece {
        public Shop(TemplateManager manager, JigsawPiece piece, BlockPos pos, int groundLevelDelta, Rotation rotation, MutableBoundingBox mbb) {
            super(SHOP, manager, piece, pos, groundLevelDelta, rotation, mbb);
        }

        public Shop(TemplateManager manager, CompoundNBT compound) {
            super(manager, compound, SHOP);
        }
    }
}
