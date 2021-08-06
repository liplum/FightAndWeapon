package net.liplum.eventhandlers;

import net.liplum.networks.MasteryGuiHandler;
import net.liplum.registries.HotkeyRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class HotkeyHandler {

    @SubscribeEvent
    public void onHotkeyPressed(InputEvent.KeyInputEvent event) {
        if (HotkeyRegistry.Master_Hotkey.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;
            MasteryGuiHandler.sendClientPacket(player);
        }
    }
}
