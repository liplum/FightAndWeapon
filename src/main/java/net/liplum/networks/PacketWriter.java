package net.liplum.networks;

import net.minecraft.network.PacketBuffer;

public interface PacketWriter {
    void write(PacketBuffer buffer,Object...args);
}
