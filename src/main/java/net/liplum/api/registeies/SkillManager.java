package net.liplum.api.registeies;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.utils.FawGemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class SkillManager {
    private final static SkillManager instance = new SkillManager();
    private final Map<Class<? extends Event>, Set<IPassiveSkill<? extends Event>>> passiveSkills = new HashMap<>();

    public static SkillManager Instance() {
        return instance;
    }

    public void registerPassiveSkill(@Nonnull IPassiveSkill<?> passiveSkill) {
        Class<? extends Event> eventType = passiveSkill.getEventType();
        if (passiveSkills.containsKey(eventType)) {
            passiveSkills.get(eventType).add(passiveSkill);
        }
        Set<IPassiveSkill<?>> skills = new HashSet<>();
        passiveSkills.put(eventType, skills);
    }

    /**
     * @param eventType
     * @return all the passive skills or null if there's no responding passive skill registered of this event.
     */
    @Nullable
    public IPassiveSkill<?>[] getPassiveSkills(Class<? extends Event> eventType) {
        if (passiveSkills.containsKey(eventType)) {
            return passiveSkills.get(eventType).toArray(new IPassiveSkill[0]);
        }
        return null;
    }

    public <T extends Event> LinkedList<IPassiveSkill<T>> getPassiveSkillsFromPlayer(Class<? extends Event> eventType, EntityPlayer player) {
        LinkedList<IPassiveSkill<T>> skills = new LinkedList<>();
        //First, gets the passive skills from weapon's gemstone
        ItemStack mainHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        ItemStack offHandHeld = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        skills.addAll(getPassiveSkillsFrom(mainHandHeld));
        skills.addAll(getPassiveSkillsFrom(offHandHeld));

        //TODO:Passives skills form master
        return skills;
    }

    private <T extends Event> LinkedList<IPassiveSkill<T>> getPassiveSkillsFrom(ItemStack itemStack) {
        LinkedList<IPassiveSkill<T>> allSkills = new LinkedList<>();
        Item item = itemStack.getItem();
        if (item instanceof WeaponBaseItem<?>) {
            WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
            IWeaponCore core = weapon.getCore();
            IGemstone gemstone = FawGemUtil.getGemstoneFrom(itemStack);
            if (gemstone != null) {
                IPassiveSkill<T>[] passiveSkills = (IPassiveSkill<T>[]) gemstone.getPassiveSkillsOf(core);
                if (passiveSkills != null) {
                    Collections.addAll(allSkills, passiveSkills);
                }
            }
        }
        return allSkills;
    }
}
