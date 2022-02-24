package net.liplum.networks;

import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.function.Consumer;

public interface ServerHandler extends Consumer<FMLNetworkEvent.ServerCustomPacketEvent> {
}
