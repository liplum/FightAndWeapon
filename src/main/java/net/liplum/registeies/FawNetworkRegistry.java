package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.liplum.networks.ChannelNames;
import net.liplum.networks.MasterGuiHandler;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.LinkedList;

public class FawNetworkRegistry {
    private static final LinkedList<Tuple<FMLEventChannel, Class<?>>> handlers = new LinkedList<>();
    public static final FMLEventChannel Channel = with(ChannelNames.Master.GUI, MasterGuiHandler.class);

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MetaData.MOD_ID, new FawGuiHandler());
        for (Tuple<FMLEventChannel, Class<?>> item : handlers) {
            FMLEventChannel channel = item.getFirst();
            Class<?> handler = item.getSecond();
            channel.register(handler);
        }
    }

    public static FMLEventChannel with(String name, Class<?> clz) {
        FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(name);
        handlers.add(new Tuple<>(channel, clz));
        return channel;
    }
}
