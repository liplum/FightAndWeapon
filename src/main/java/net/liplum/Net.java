package net.liplum;

import io.netty.buffer.ByteBuf;
import net.liplum.api.annotations.OnlyWhenInitialization;
import net.liplum.gui.FawGuiHandler;
import net.liplum.networks.ChannelNames;
import net.liplum.networks.DataPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.util.UUID;

public class Net {
    public static final DataPacketHandler MasteryGui = new DataPacketHandler(ChannelNames.Mastery.GUI
    ).byServer(event -> {
        FMLProxyPacket packet = event.getPacket();
        ByteBuf buffer = packet.payload();
        /*
        String uuidStr = ByteBufUtils.readUTF8String(buffer);
        UUID uuid = UUID.fromString(uuidStr);
        */
        long most = buffer.readLong();
        long least = buffer.readLong();
        UUID uuid = new UUID(most, least);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        server.addScheduledTask(() -> {
            PlayerList playerList = server.getPlayerList();
            EntityPlayerMP player = playerList.getPlayerByUUID(uuid);
            World world = player.world;
            player.openGui(MetaData.MOD_ID, FawGuiHandler.Mastery_ID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        });
    }).writer(((buffer, args) -> {
        EntityPlayer player = (EntityPlayer) args[0];
        UUID uuid = player.getUniqueID();
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
        /*
        String uuidStr = uuid.toString();
        ByteBufUtils.writeUTF8String(buffer, uuidStr);
        */
    }));

    //You must call it to load this class and all the static fields.
    @OnlyWhenInitialization
    public static void init() {

    }
}
