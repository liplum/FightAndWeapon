package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.networks.MasterGuiHandler;
import net.liplum.registeies.HotkeyRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            MasterGuiHandler.sendClientPacket(player);
        }
    }
}
