package net.liplum.networks;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DataPacketHandler implements IDataPacketHandler {
    @Nonnull
    private final String channelName;
    private FMLEventChannel eventChannel;
    @Nullable
    public ClientHandler clientHandler;
    @Nullable
    public ServerHandler serverHandler;
    @Nullable
    public PacketWriter writer;

    public DataPacketHandler(@Nonnull String channelName) {
        this.channelName = channelName;
        this.clientHandler = null;
        this.serverHandler = null;
        registerSelf();
    }

    public DataPacketHandler(@Nonnull String channelName, @Nonnull ClientHandler clientHandler) {
        this.channelName = channelName;
        this.clientHandler = clientHandler;
        this.serverHandler = null;
        registerSelf();
    }

    public DataPacketHandler(@Nonnull String channelName, @Nonnull ServerHandler serverHandler) {
        this.channelName = channelName;
        this.clientHandler = null;
        this.serverHandler = serverHandler;
        registerSelf();
    }

    public DataPacketHandler(@Nonnull String channelName, @Nullable ClientHandler clientHandler, @Nullable ServerHandler serverHandler) {
        this.channelName = channelName;
        this.clientHandler = clientHandler;
        this.serverHandler = serverHandler;
        registerSelf();
    }

    public DataPacketHandler byClient(@Nullable ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        return this;
    }

    public DataPacketHandler byServer(@Nullable ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
        return this;
    }

    public DataPacketHandler writer(@Nullable PacketWriter writer) {
        this.writer = writer;
        return this;
    }

    private void registerSelf() {
        eventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
        eventChannel.register(this);
    }

    public FMLEventChannel getEventChannel() {
        return eventChannel;
    }

    public void send(Object... args) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        if (writer != null) {
            writer.write(buffer, args);
        }
        eventChannel.sendToServer(new FMLProxyPacket(buffer, channelName));
    }

    @Nonnull
    @Override
    public String getChannelName() {
        return channelName;
    }

    @Override
    @SubscribeEvent
    public void onClientReceivedPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        if (clientHandler != null) {
            clientHandler.accept(event);
        }
    }

    @Override
    @SubscribeEvent
    public void onServerReceivedPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (serverHandler != null) {
            serverHandler.accept(event);
        }
    }
}
