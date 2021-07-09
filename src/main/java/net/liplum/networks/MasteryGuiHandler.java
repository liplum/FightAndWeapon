package net.liplum.networks;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MasteryGuiHandler implements IDataPacketHandler {
    public static final MasteryGuiHandler instance = new MasteryGuiHandler();
    private static final String ChannelName = ChannelNames.Master.GUI;

    public static MasteryGuiHandler getInstance() {
        return instance;
    }

    public static void sendClientPacket(EntityPlayer player) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        UUID uuid = player.getUniqueID();
        String uuidStr = uuid.toString();
        int uuidLength = uuidStr.length();
        buffer.writeInt(uuidLength);
        buffer.writeString(uuidStr);
        //FawNetworkRegistry.MasterGuiChannel.sendToServer(new FMLProxyPacket(buffer, ChannelName));
    }

    @Override
    @Nonnull
    public String getChannelName() {
        return ChannelName;
    }

    @Override
    @SubscribeEvent
    // NOTE: DON'T ADD @SideOnly(Side.CLIENT) !!!!!!!!
    public void onClientReceivedPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        System.out.println("GOT.");
    }

    @Override
    @SubscribeEvent
    // NOTE: DON'T ADD @SideOnly(Side.SERVER) !!!!!!!!
    public void onServerReceivedPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        FMLProxyPacket packet = event.getPacket();
        ByteBuf buffer = packet.payload();
        int uuidLength = buffer.readInt();
        CharSequence uuidRaw = buffer.readCharSequence(uuidLength + 1, StandardCharsets.UTF_8);
        String uuidStr = uuidRaw.toString().substring(1);
        UUID uuid = UUID.fromString(uuidStr);

        Minecraft mc = Minecraft.getMinecraft();
        IntegratedServer server = mc.getIntegratedServer();
        if (server != null) {
            mc.addScheduledTask(() -> {
                        PlayerList playerList = server.getPlayerList();
                        EntityPlayerMP player = playerList.getPlayerByUUID(uuid);
                        World world = player.world;
                        player.openGui(MetaData.MOD_ID, FawGuiHandler.Mastery_ID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
                    }
            );
        }
    }
}
