package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.entities.IndestructibleEntityItem;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class FAWItemExpireHandler {
    @SubscribeEvent
    public static void onFAWItemExpire(ItemExpireEvent event) {
        if(event.getEntityItem() instanceof IndestructibleEntityItem){
            event.setCanceled(true);
        }
    }

}
