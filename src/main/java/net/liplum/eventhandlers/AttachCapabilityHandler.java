package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.liplum.registries.CapabilityRegistry.CapabilityEntry;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class AttachCapabilityHandler {
    @SubscribeEvent
    public static void onEntityAttachedCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof EntityPlayer) {
            for (CapabilityEntry entry : CapabilityRegistry.getPlayerCapabilities()) {
                try {
                    ICapabilityProvider provider = entry.provider.newInstance();
                    event.addCapability(entry.resourceLocation, provider);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (CapabilityEntry entry : CapabilityRegistry.getEntityCapabilities()) {
                try {
                    ICapabilityProvider provider = entry.provider.newInstance();
                    event.addCapability(entry.resourceLocation, provider);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
