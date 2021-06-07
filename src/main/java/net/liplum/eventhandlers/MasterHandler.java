package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.capabilities.MasterCapability;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class MasterHandler {
    @SubscribeEvent
    public static void onCloneMaster(PlayerEvent.Clone event) {
        MasterCapability oldOne = event.getOriginal().getCapability(CapabilityRegistry.Master_Capability, null);
        MasterCapability newOne = event.getEntityPlayer().getCapability(CapabilityRegistry.Master_Capability, null);
        newOne.setAllMasters(oldOne.shallowCloneAllMasters());
    }
}
