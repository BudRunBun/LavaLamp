/*package com.budrunbun.lavalamp;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        System.out.println("Event!!!");
        PlayerEntity player = event.getPlayer();
        if (player.getHeldItemMainhand().getItem() == Blocks.COBBLESTONE.asItem()) {
            player.getEntityWorld().setBlockState(player.getPosition().offset(player.getHorizontalFacing()), Blocks.SANDSTONE.getDefaultState(), 3);
        }
    }
}*/
