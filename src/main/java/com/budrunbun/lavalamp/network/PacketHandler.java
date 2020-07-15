package com.budrunbun.lavalamp.network;

import com.budrunbun.lavalamp.LavaLamp;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("lavalamp", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, CheeseGeneratorPacket.class, CheeseGeneratorPacket::encode, CheeseGeneratorPacket::decode, CheeseGeneratorPacket::handle);
    }

    public static <MSG> void sendToServer(MSG msg)
    {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayerEntity player)
    {
        if (!(player instanceof FakePlayer))
        {
            INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
