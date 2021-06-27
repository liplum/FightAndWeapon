package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.liplum.networks.IDataPacketHandler;
import net.liplum.networks.MasterGuiHandler;
import net.liplum.networks.MessageManager;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.LinkedList;

public class FawNetworkRegistry {
    private static final LinkedList<Tuple<FMLEventChannel, IDataPacketHandler>> handlers = new LinkedList<>();
    //private static final LinkedList<MessageEntry<? extends IMessage>> Messages = new LinkedList<>();
    public static final FMLEventChannel MasterGuiChannel = with(MasterGuiHandler.getInstance());
    //private static Consumer<SimpleNetworkWrapper> RegisterAction;
    //private static int ID = 0;

  /*  public static void registerAllMessage(SimpleNetworkWrapper simpleNetworkWrapper) {
        Utils.notNullThenDo(simpleNetworkWrapper, s -> RegisterAction.accept(s));
    }

    static {
        add(new MessageEntry<>(AttackDistanceMsg.Handler.class, AttackDistanceMsg.class, ID++, Side.CLIENT));
    }
*/
    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MetaData.MOD_ID, new FawGuiHandler());

        for (Tuple<FMLEventChannel, IDataPacketHandler> item : handlers) {
            FMLEventChannel channel = item.getFirst();
            IDataPacketHandler handler = item.getSecond();
            channel.register(handler);
        }

    /*    RegisterAction = c -> {
            for (MessageEntry<? extends IMessage> messageEntry : Messages) {
                IMessageHandler<? extends IMessage, IMessage> handler = messageEntry.handler;
                Class<? extends IMessage> messageType = messageEntry.messageType;
                c.registerMessage(
                        (IMessageHandler<IMessage, IMessage>) handler, (Class<IMessage>) messageType,
                        messageEntry.ID,
                        messageEntry.side);
            }
        };
*/
        MessageManager.init();
    }

    public static FMLEventChannel with(IDataPacketHandler handler) {
        FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(handler.getChannelName());
        handlers.add(new Tuple<>(channel, handler));
        return channel;
    }

 /*   public static <T extends IMessage> MessageEntry<T> add(MessageEntry<T> messageEntry) {
        Messages.add(messageEntry);
        return messageEntry;
    }

    public static class MessageEntry<T extends IMessage> {
        @Nonnull
        public final Class<IMessageHandler<T, IMessage>> handler;
        @Nonnull
        public final Class<T> messageType;
        public final int ID;
        @Nonnull
        public final Side side;

        public MessageEntry(@Nonnull Class<IMessageHandler<T, IMessage>> handler,
                            @Nonnull Class<T> messageType,
                            int ID, @Nonnull Side side) {
            this.handler = handler;
            this.messageType = messageType;
            this.ID = ID;
            this.side = side;
        }
    }*/
}
