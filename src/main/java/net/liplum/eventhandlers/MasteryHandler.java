package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IMastery;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.events.mastery.MasteryUpgradedEvent;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class MasteryHandler {
    @SubscribeEvent
    public static void onCloneMaster(PlayerEvent.Clone event) {
        MasteryCapability oldOne = event.getOriginal().getCapability(CapabilityRegistry.Mastery_Capability, null);
        MasteryCapability newOne = event.getEntityPlayer().getCapability(CapabilityRegistry.Mastery_Capability, null);
        if (newOne != null && oldOne != null) {
            newOne.setAllMasteries(oldOne.shallowCloneAllMasters());
        }
    }

    @SubscribeEvent
    public static void onMasteryUpgraded(MasteryUpgradedEvent event) {
        EntityPlayer player = event.player;
        IMastery mastery = event.mastery;
        int newLevel = event.newLevel;
        TextComponentString text = new TextComponentString("Now you are " + newLevel + " in " + mastery.getRegisterName());
        player.sendMessage(text);
    }
}
