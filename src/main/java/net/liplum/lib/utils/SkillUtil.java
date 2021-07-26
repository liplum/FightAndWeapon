package net.liplum.lib.utils;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.*;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SkillUtil {

    private static void addPassiveSkills(@Nonnull Class<Event> eventType, @Nonnull Set<IPassiveSkill<Event>> allSkills, @Nonnull ItemStack itemStack, @Nonnull EntityLivingBase entity) {
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem) {

            //First, gets the passive skills from weapon's gemstone
            WeaponBaseItem weapon = (WeaponBaseItem) item;
            WeaponCore core = weapon.getCore();
            AggregatePassiveSkill coreSkill = core.getWeaponPassiveSkills();
            if (coreSkill != null && coreSkill.getEventTypeArgs().has(eventType)) {
                allSkills.add(coreSkill);
            }
            IGemstone gemstone = GemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                //I told it can be converted so that it must can be converted!!!
                Collection<IPassiveSkill<?>> skillsFromGemstone = gemstone.getPassiveSkillsOf(core);
                for (IPassiveSkill<?> skill : skillsFromGemstone) {
                    if (skill.getEventTypeArgs().has(eventType)) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }

            //Then, gets the passive skills from entity's mastery if the entity is a player
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                Collection<IPassiveSkill<?>> skillsFromMastery = MasteryUtil.getPassiveSkills(player, core);
                for (IPassiveSkill<?> skill : skillsFromMastery) {
                    if (skill.getEventTypeArgs().has(eventType)) {
                        allSkills.add((IPassiveSkill<Event>) skill);
                    }
                }
            }
        }
        if (FawItemUtil.isWeaponBroken(itemStack)) {
            allSkills.removeIf(IPassiveSkill::isBanedWhenBroken);
        }
    }

    @Nonnull
    @LongSupport
    public static Collection<IPassiveSkill<Event>> getAvailablePassiveSkills(@Nonnull Class<? extends Event> eventType, @Nonnull EntityLivingBase entity) {
        Set<IPassiveSkill<Event>> mainHandSkills = new HashSet<>();
        ItemStack mainHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        addPassiveSkills((Class<Event>) eventType, mainHandSkills, mainHandHeld, entity);

        Set<IPassiveSkill<Event>> offHandSkills = new HashSet<>();
        ItemStack offHandHeld = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        addPassiveSkills((Class<Event>) eventType, offHandSkills, offHandHeld, entity);
        IPSkillCoolingTimer timer = PSkillCoolingTimer.create(entity);

        return Stream.concat(mainHandSkills.stream(), offHandSkills.stream())
                .filter(timer::isNotInCoolingDown)
                .sorted(Comparator.comparing(IPassiveSkill::getTriggerPriority))
                .collect(Collectors.toList());
    }

    @LongSupport
    public static void onTrigger(@Nonnull EntityLivingBase entity, @Nonnull IPassiveSkill<?> passiveSkill, @Nonnull PSkillResult result) {
        if (result.succeed) {
            onCompete(entity, passiveSkill);
        }
    }

    private static void onCompete(@Nonnull EntityLivingBase entity, @Nonnull IPassiveSkill<?> passiveSkill) {
        heatSkill(entity, passiveSkill);
    }

    private static void heatSkill(@Nonnull EntityLivingBase entity, IPassiveSkill<?> passiveSkill) {
        IPSkillCoolingTimer timer = PSkillCoolingTimer.create(entity);
        if (passiveSkill.hasCoolDown()) {
            timer.addNewCoolDown(passiveSkill, passiveSkill.getCoolDownTicks());
        }
    }
}
