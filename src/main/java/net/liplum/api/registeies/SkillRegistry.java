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
    private static final Map<Class<?>, Set<IPassiveSkill<?>>> PassiveSkillsMap = new HashMap<>();
    private static final Map<String, IPassiveSkill<?>> AllPassiveSkills = new HashMap<>();
    private static final Map<String, IActiveSkill> AllActiveSkills = new HashMap<>();

    @Nonnull
    public static <T extends Event> IPassiveSkill<T> registerPassiveSkill(@Nonnull IPassiveSkill<T> passiveSkill) {
        //Register it to passiveSkillsMap
        Class<? extends Event> eventType = passiveSkill.getEventType();
        if (PassiveSkillsMap.containsKey(eventType)) {
            PassiveSkillsMap.get(eventType).add(passiveSkill);
        }
        Set<IPassiveSkill<?>> skills = new HashSet<>();
        PassiveSkillsMap.put(eventType, skills);

        //Register it to all passive skills
        AllPassiveSkills.put(passiveSkill.getRegisterName(), passiveSkill);
        return passiveSkill;
    }

    @Nonnull
    public static IActiveSkill registerActiveSkill(@Nonnull String registerName, @Nonnull IActiveSkill activeSkill) {
        AllActiveSkills.put(registerName, activeSkill);
        return activeSkill;
    }

    /**
     * @param registerName
     * @return
     */
    @Nullable
    public static IPassiveSkill<?> getPassiveSkillsFromName(@Nonnull String registerName) {
        if (AllPassiveSkills.containsKey(registerName)) {
            return AllPassiveSkills.get(registerName);
        }
        return null;
    }

    /**
     * @param registerName
     * @return
     */
    @Nullable
    public static IActiveSkill getActiveSkillsFromName(@Nonnull String registerName) {
        if (AllActiveSkills.containsKey(registerName)) {
            return AllActiveSkills.get(registerName);
        }
        return null;
    }

    /**
     * @param eventType
     * @return all the passive skills or null if there's no responding passive skill registered of this event.
     */
    @Nullable
    public static IPassiveSkill<?>[] getPassiveSkillsFromEvent(@Nonnull Class<? extends Event> eventType) {
        if (PassiveSkillsMap.containsKey(eventType)) {
            return PassiveSkillsMap.get(eventType).toArray(new IPassiveSkill[0]);
        }
        return null;
    }

}
