package com.budrunbun.lavalamp.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CheeseGeneratorPacket {

    private int dim;
    private BlockPos pos;

    private int ordinal;

    public CheeseGeneratorPacket(int d, BlockPos pos, int o) {
        dim = d;
        this.pos = pos;

        ordinal = o;
    }

    public static void encode(CheeseGeneratorPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.dim);
        buf.writeInt(msg.pos.getX());
        buf.writeInt(msg.pos.getY());
        buf.writeInt(msg.pos.getZ());
        buf.writeInt(msg.ordinal);
    }

    public static CheeseGeneratorPacket decode(PacketBuffer buf) {
        int dim = buf.readInt();
        int posx = buf.readInt();
        int posy = buf.readInt();
        int posz = buf.readInt();
        int ordinal = buf.readInt();
        BlockPos p = new BlockPos(posx, posy, posz);

        return new CheeseGeneratorPacket(dim, p, ordinal);
    }

    public static void handle(final CheeseGeneratorPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

        });
        ctx.get().setPacketHandled(true);
    }

}
