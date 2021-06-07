package net.liplum.lib.utils;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.lib.items.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

public final class SkillUtil {
    @Nonnull
    public static <T extends Event> LinkedList<IPassiveSkill<T>> getPassiveSkillsFrom(@Nonnull ItemStack itemStack) {
        LinkedList<IPassiveSkill<T>> allSkills = new LinkedList<>();
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem<?>) {
            WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
            IWeaponCore core = weapon.getCore();
            IGemstone gemstone = FawGemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                //I told that it can be converted so it must can be converted!!!
                IPassiveSkill<T>[] skills = (IPassiveSkill<T>[]) gemstone.getPassiveSkillsOf(core);
                Collections.addAll(allSkills, skills);
            }
        }
        return allSkills;
    }

    @Nonnull
    public static <T extends Event> LinkedList<IPassiveSkill<T>> getPassiveSkillsFromPlayer(@Nonnull Class<? extends Event> eventType, @Nonnull EntityPlayer player) {
        LinkedList<IPassiveSkill<T>> skills = new LinkedList<>();
        //First, gets the passive skills from weapon's gemstone
        ItemStack mainHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        Item mainHandHeldItem = mainHandHeld.getItem();

        ItemStack offHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        Item offHandHeldItem = offHandHeld.getItem();

        skills.addAll(getPassiveSkillsFrom(mainHandHeld));
        skills.addAll(getPassiveSkillsFrom(offHandHeld));

        if (mainHandHeldItem instanceof WeaponBaseItem<?>) {
            Set<IPassiveSkill<?>> mainHandSkills = MasterUtil.getPassiveSkills(player, (Class<? extends WeaponBaseItem<?>>) mainHandHeldItem.getClass());
        }
        if (offHandHeldItem instanceof WeaponBaseItem<?>) {
            Set<IPassiveSkill<?>> offHandSkills = MasterUtil.getPassiveSkills(player, (Class<? extends WeaponBaseItem<?>>) offHandHeldItem.getClass());
        }
        return skills;
    }
}
