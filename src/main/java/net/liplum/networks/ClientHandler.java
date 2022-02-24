package net.liplum.networks;

import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.function.Consumer;

public interface ClientHandler extends Consumer<FMLNetworkEvent.ClientCustomPacketEvent> {
}
