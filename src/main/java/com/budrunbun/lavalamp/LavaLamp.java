package com.budrunbun.lavalamp;

import com.budrunbun.lavalamp.crafting.CheeseGeneratorRecipe;
import com.budrunbun.lavalamp.crafting.ModRecipes;
import com.budrunbun.lavalamp.proxy.ClientProxy;
import com.budrunbun.lavalamp.proxy.IProxy;
import com.budrunbun.lavalamp.proxy.ServerProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Map;

@Mod("lavalamp")
public class LavaLamp {

    public static final String MOD_ID = "lavalamp";

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public LavaLamp() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
    }
}
