package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.attributes.IAttribute;
import net.liplum.entities.IndestructibleItemEntity;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class FawWeaponHandler {
    @SubscribeEvent
    public static void onFawWeaponExpire(ItemExpireEvent event) {
        if (event.getEntityItem() instanceof IndestructibleItemEntity) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBrokenWeaponWantReleaseSkill(WeaponSkillReleaseEvent.Pre event) {
        if (FawItemUtil.isBroken(event.getItemStack())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBrokenWeaponAccessAttribute(AttributeAccessEvent event) {
        ItemStack itemStack = event.getItemStack();
        IAttribute attribute = event.getAttribute();
        if (itemStack != null && FawItemUtil.isBroken(itemStack) && attribute.useSpecialValueWhenWeaponBroken() && event.isUseSpecialValueWhenWeaponBroken()) {
            event.setFinalAttrValue(attribute.getValueWhenWeaponBroken());
        }
    }
}
