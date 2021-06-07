package net.liplum.api.registeies;

import net.liplum.api.fight.IActiveSkill;
import net.liplum.api.fight.IPassiveSkill;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SkillRegistry {
    private static final Map<Class<?>, Set<IPassiveSkill<?>>> passiveSkillsMap = new HashMap<>();
    private static final Map<String, IPassiveSkill<?>> allPassiveSkills = new HashMap<>();
    private static final Map<String, IActiveSkill> allActiveSkills = new HashMap<>();

    @Nonnull
    public static <T extends Event> IPassiveSkill<T> registerPassiveSkill(@Nonnull String registerName, @Nonnull IPassiveSkill<T> passiveSkill) {
        //Register it to passiveSkillsMap
        Class<? extends Event> eventType = passiveSkill.getEventType();
        if (passiveSkillsMap.containsKey(eventType)) {
            passiveSkillsMap.get(eventType).add(passiveSkill);
        }
        Set<IPassiveSkill<?>> skills = new HashSet<>();
        passiveSkillsMap.put(eventType, skills);

        //Register it to all passive skills
        allPassiveSkills.put(registerName, passiveSkill);
        return passiveSkill;
    }

    @Nonnull
    public static IActiveSkill registerActiveSkill(@Nonnull String registerName, @Nonnull IActiveSkill activeSkill) {
        allActiveSkills.put(registerName, activeSkill);
        return activeSkill;
    }

    /**
     * @param registerName
     * @return
     */
    @Nullable
    public static IPassiveSkill<?> getPassiveSkillsFromName(@Nonnull String registerName) {
        if (allPassiveSkills.containsKey(registerName)) {
            return allPassiveSkills.get(registerName);
        }
        return null;
    }

    /**
     * @param registerName
     * @return
     */
    @Nullable
    public static IActiveSkill getActiveSkillsFromName(@Nonnull String registerName) {
        if (allActiveSkills.containsKey(registerName)) {
            return allActiveSkills.get(registerName);
        }
        return null;
    }

    /**
     * @param eventType
     * @return all the passive skills or null if there's no responding passive skill registered of this event.
     */
    @Nullable
    public static IPassiveSkill<?>[] getPassiveSkillsFromEvent(@Nonnull Class<? extends Event> eventType) {
        if (passiveSkillsMap.containsKey(eventType)) {
            return passiveSkillsMap.get(eventType).toArray(new IPassiveSkill[0]);
        }
        return null;
    }

}
