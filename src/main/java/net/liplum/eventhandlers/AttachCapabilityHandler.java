package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.capabilities.MasterProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class AttachCapabilityHandler {
    @SubscribeEvent
    public static void onEntityAttachedCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof EntityPlayer) {
            MasterProvider masterProvider = new MasterProvider();
            event.addCapability(Names.genResourceLocation(Names.Capability.Master), masterProvider);
        }
    }
}
