package net.liplum.networks;

import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.annotation.Nonnull;

public interface IDataPacketHandler {
    @Nonnull
    String getChannelName();

    // NOTE: DON'T ADD @SideOnly(Side.CLIENT) !!!!!!!!
    void onClientReceivedPacket(FMLNetworkEvent.ClientCustomPacketEvent event);

    // NOTE: DON'T ADD @SideOnly(Side.SERVER) !!!!!!!!
    void onServerReceivedPacket(FMLNetworkEvent.ServerCustomPacketEvent event);
}
