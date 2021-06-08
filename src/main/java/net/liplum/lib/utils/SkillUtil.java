package net.liplum.lib.utils;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.lib.items.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class SkillUtil {


    private static void addPassiveSkillsFromPlayer(@Nonnull Class<? extends Event> eventType, @Nonnull Set<IPassiveSkill<Event>> allSkills, @Nonnull ItemStack itemStack, @Nonnull EntityPlayer player) {
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem<?>) {

            //First, gets the passive skills from weapon's gemstone
            WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
            Class<WeaponBaseItem<?>> clz = (Class<WeaponBaseItem<?>>) weapon.getClass();
            IWeaponCore core = weapon.getCore();
            IGemstone gemstone = FawGemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                //I told it can be converted so that it must can be converted!!!
                IPassiveSkill<?>[] skillsFromGemstone = gemstone.getPassiveSkillsOf(core);
                for (IPassiveSkill<?> skill : skillsFromGemstone) {
                    if (skill.getEventType() == eventType) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }


            //Then, gets the passive skills from player's master
            Set<IPassiveSkill<?>> skillsFromMaster = MasterUtil.getPassiveSkills(player, clz);
            for (IPassiveSkill<?> skill : skillsFromMaster) {
                if (skill.getEventType() == eventType) {
                    allSkills.add((IPassiveSkill<Event>) skill);
                }
            }
        }
    }


    private static void addPassiveSkillsFromMob(@Nonnull Class<? extends Event> eventType, @Nonnull Set<IPassiveSkill<Event>> allSkills, @Nonnull ItemStack itemStack, @Nonnull EntityLivingBase player) {
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem<?>) {
            //Gets the passive skills from weapon's gemstone
            WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
            Class<WeaponBaseItem<?>> clz = (Class<WeaponBaseItem<?>>) weapon.getClass();
            IWeaponCore core = weapon.getCore();
            IGemstone gemstone = FawGemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                //I told it can be converted so that it must can be converted!!!
                IPassiveSkill<?>[] skillsFromGemstone = gemstone.getPassiveSkillsOf(core);
                for (IPassiveSkill<?> skill : skillsFromGemstone) {
                    if (skill.getEventType() == eventType) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }
        }
    }

    @Nonnull
    public static Set<IPassiveSkill<Event>> getPassiveSkillsFromPlayer(@Nonnull Class<? extends Event> eventType, @Nonnull EntityPlayer player) {
        Set<IPassiveSkill<Event>> skills = new HashSet<>();
        ItemStack mainHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        addPassiveSkillsFromPlayer(eventType, skills, mainHandHeld, player);

        ItemStack offHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        addPassiveSkillsFromPlayer(eventType, skills, offHandHeld, player);

        return skills;
    }

    @Nonnull
    public static Set<IPassiveSkill<Event>> getPassiveSkillsFromMob(@Nonnull Class<? extends Event> eventType, @Nonnull EntityLivingBase entity) {
        Set<IPassiveSkill<Event>> skills = new HashSet<>();
        ItemStack mainHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        addPassiveSkillsFromMob(eventType, skills, mainHandHeld, entity);

        ItemStack offHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        addPassiveSkillsFromMob(eventType, skills, offHandHeld, entity);

        return skills;
    }
}
