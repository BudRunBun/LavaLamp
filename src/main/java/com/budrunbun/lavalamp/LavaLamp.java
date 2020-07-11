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
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickBlock);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
    }

    private Map<ResourceLocation, IRecipe<?>> getRecipes (IRecipeType<?> recipeType, RecipeManager manager) {

        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return recipesMap.get(recipeType);
    }

    private void onPlayerClickBlock (PlayerInteractEvent.LeftClickBlock event) {

        // Check that the world is server side, and the player actually exists.
        if (!event.getWorld().isRemote && event.getEntityPlayer() != null) {

            // Get the currently held item of the player, for the hand that was used in the
            // event.
            final ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());

            // Iterates all the recipes for the custom recipe type. If you have lots of recipes
            // you may want to consider adding some form of recipe caching. In this case we
            // could store the last successful recipe in a global field to lower the lookup
            // time for repeat crafting. You could also use RecipesUpdatedEvent to build a
            // cache of your recipes. Make sure to build the cache on LOWEST priority so mods
            // like CraftTweaker can work with your recipes.
            for (final IRecipe<?> recipe : this.getRecipes(ModRecipes.CHEESE_GENERATOR_RECIPE, event.getWorld().getRecipeManager()).values()) {

                // If you need access to custom recipe methods you will need to check and cast
                // to your recipe type. This step could be skipped if you did it during a cache
                // process.
                if (recipe instanceof CheeseGeneratorRecipe) {

                    final CheeseGeneratorRecipe clickBlockRecipe = (CheeseGeneratorRecipe) recipe;

                    // isValid is a custom recipe which checks if the held item and block match
                    // a known recipe. If this were cached to a multimap you could use Block as
                    // a key and only check the held item.
                    if (clickBlockRecipe.isValid(heldItem, event.getWorld().getBlockState(event.getPos()).getBlock())) {

                        // When the recipe is valid, shrink the held item by one.
                        heldItem.shrink(1);

                        // This forge method tries to give a player an item. If they have no
                        // room it drops on the ground. We're giving them a copy of the output
                        // item.
                        ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), clickBlockRecipe.getRecipeOutput().copy());
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    }
}
