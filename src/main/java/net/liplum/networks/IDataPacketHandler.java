package net.liplum.networks;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.jetbrains.annotations.NotNull;

public interface IDataPacketHandler {
    @NotNull
    String getChannelName();

    /**
     * This handles packet when receiving and will run on a physical client.
     *
     * @param event packet
     */
    @SubscribeEvent
    void onClientReceivedPacket(FMLNetworkEvent.ClientCustomPacketEvent event);

    /**
     * This handles packet when receiving and may run on a physical sever so that you mustn't use any ClientOnly function/field.
     *
     * @param event packet
     */
    @SubscribeEvent
    void onServerReceivedPacket(FMLNetworkEvent.ServerCustomPacketEvent event);
}
