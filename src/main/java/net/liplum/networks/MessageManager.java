package net.liplum.networks;

import net.liplum.MetaData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageManager {
    private static final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(MetaData.MOD_ID);
    private static int ID = 0;

    private MessageManager() {
    }

    public static void init() {
        //FawNetworkRegistry.registerAllMessage(channel);
        //Client to Server
        channel.registerMessage(AttackMsg.Handler.class, AttackMsg.class, ID++, Side.SERVER);
        channel.registerMessage(FawWeaponLeftClickMsg.Handler.class, FawWeaponLeftClickMsg.class, ID++, Side.SERVER);
        channel.registerMessage(CoolingMsg.Handler.class, CoolingMsg.class, ID++, Side.CLIENT);
        channel.registerMessage(MasteryMsg.Handler.class, MasteryMsg.class, ID++, Side.CLIENT);
    }

    public static void sendMessageToDim(IMessage msg, int dim) {
        channel.sendToDimension(msg, dim);
    }

    public static void sendMessageAroundPos(IMessage msg, int dim, BlockPos pos) {
        channel.sendToAllAround(msg, new NetworkRegistry.TargetPoint(dim, pos.getX(), pos.getY(), pos.getZ(), 2.0D));
    }

    public static void sendMessageToPlayer(IMessage msg, EntityPlayerMP player) {
        channel.sendTo(msg, player);
    }

    public static void sendMessageToAll(IMessage msg) {
        channel.sendToAll(msg);
    }

    public static void sendMessageToServer(IMessage msg) {
        channel.sendToServer(msg);
    }
}
