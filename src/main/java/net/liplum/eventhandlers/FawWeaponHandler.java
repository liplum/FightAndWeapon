package net.liplum.eventhandlers;

import net.liplum.attributes.IAttribute;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.events.weapon.WeaponSkillReleaseEvent;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FawWeaponHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBrokenWeaponWantReleaseSkill(WeaponSkillReleaseEvent.Pre event) {
        if (FawItemUtil.isWeaponBroken(event.getItemStack())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBrokenWeaponAccessAttribute(AttributeAccessEvent event) {
        ItemStack itemStack = event.getItemStack();
        IAttribute attribute = event.getAttribute();
        if (itemStack != null && FawItemUtil.isWeaponBroken(itemStack) && attribute.useSpecialValueWhenWeaponBroken() && event.isUseSpecialValueWhenWeaponBroken()) {
            event.setFinalAttrValue(attribute.getValueWhenWeaponBroken(event.getFinalAttrValue()));
        }
    }
}
