package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.liplum.registeies.HotkeyRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

// NOTE: DON'T ADD @SideOnly(Side.CLIENT) !!!!!!!!
@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class HotkeyHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onHotkeyPressed(InputEvent.KeyInputEvent event) {
        if (HotkeyRegistry.Master_Hotkey.isPressed()) {
            //TODO:Open master gui . It is only a test!!!
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;
            IntegratedServer integratedServer = mc.getIntegratedServer();
            MinecraftServer server = integratedServer.getServer();
            PlayerList playerList = server.getPlayerList();
            UUID spUUID = player.getUniqueID();
            String spName = player.getName();
            EntityPlayerMP playerByUsername = playerList.getPlayerByUsername(spName);
            EntityPlayerMP playerByUUID = playerList.getPlayerByUUID(spUUID);
            if (playerByUUID == playerByUsername) {
                System.out.println("The Same player!!!");
            }
            playerByUsername.sendMessage(new TextComponentString("Open gui successfully."));

            playerByUUID.sendMessage(new TextComponentString("Open gui successfully."));
            World world = player.world;
            if (!world.isRemote) {
                player.sendMessage(new TextComponentString("Open gui successfully."));
                player.openGui(MetaData.MOD_ID, FawGuiHandler.Master_ID, world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }
    }
}
