package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class CoroutineHandler {
    @SubscribeEvent
    public static void serverTickCoroutine(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            CoroutineSystem.Instance().onServerTick();
        }
    }

    @SubscribeEvent
    public static void stopCoroutine(LivingDeathEvent e) {
        CoroutineSystem.Instance().clear(e.getEntityLiving());
    }

}
