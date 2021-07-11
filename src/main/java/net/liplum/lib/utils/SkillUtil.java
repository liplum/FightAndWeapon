package net.liplum.lib.utils;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
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


    private static void addPassiveSkills(@Nonnull Class<? extends Event> eventType, @Nonnull Set<IPassiveSkill<Event>> allSkills, @Nonnull ItemStack itemStack, @Nonnull EntityLivingBase entity) {
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem) {

            //First, gets the passive skills from weapon's gemstone
            WeaponBaseItem weapon = (WeaponBaseItem) item;
            WeaponType weaponType = weapon.getWeaponType();
            WeaponCore core = weapon.getCore();
            IGemstone gemstone = GemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                //I told it can be converted so that it must can be converted!!!
                IPassiveSkill<?>[] skillsFromGemstone = gemstone.getPassiveSkillsOf(core);
                for (IPassiveSkill<?> skill : skillsFromGemstone) {
                    if (skill.getEventTypeArgs().has(eventType)) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }

            //Then, gets the passive skills from entity's mastery if the entity is a player
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                Set<IPassiveSkill<?>> skillsFromMastery = MasteryUtil.getPassiveSkills(player, weaponType);
                for (IPassiveSkill<?> skill : skillsFromMastery) {
                    if (skill.getEventTypeArgs().has(eventType)) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }
        }
    }

    @Nonnull
    public static Set<IPassiveSkill<Event>> getPassiveSkills(@Nonnull Class<? extends Event> eventType, @Nonnull EntityLivingBase entity) {
        Set<IPassiveSkill<Event>> skills = new HashSet<>();
        ItemStack mainHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        addPassiveSkills(eventType, skills, mainHandHeld, entity);

        ItemStack offHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        addPassiveSkills(eventType, skills, offHandHeld, entity);

        return skills;
    }
}
