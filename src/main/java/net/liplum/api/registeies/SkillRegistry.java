package net.liplum.api.registeies;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IEventTypeArgs;
import net.liplum.api.fight.IPassiveSkill;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@LongSupport
public final class SkillRegistry {
    private static final Map<Class<?>, Set<IPassiveSkill<?>>> EventTypePassiveSkillMap = new HashMap<>();
    private static final Map<String, IPassiveSkill<?>> AllPassiveSkills = new HashMap<>();

    @Nonnull
    @LongSupport
    public static <T extends IPassiveSkill<? extends Event>> T register(@Nonnull T passiveSkill) {
        //Register it to passiveSkillsMap
        IEventTypeArgs eventTypeArgs = passiveSkill.getEventTypeArgs();
        for (Class<? extends Event> eventType : eventTypeArgs.getAllEventTypes()) {
            if (EventTypePassiveSkillMap.containsKey(eventType)) {
                EventTypePassiveSkillMap.get(eventType).add(passiveSkill);
            } else {
                Set<IPassiveSkill<?>> skills = new HashSet<>();
                skills.add(passiveSkill);
                EventTypePassiveSkillMap.put(eventType, skills);
            }
            //Register it to all passive skills
        }
        AllPassiveSkills.put(passiveSkill.getRegisterName(), passiveSkill);
        return passiveSkill;
    }


    /**
     * @param registerName
     * @return
     */
    @Nullable
    @LongSupport
    public static IPassiveSkill<?> getPassiveSkillsFromName(@Nonnull String registerName) {
        if (AllPassiveSkills.containsKey(registerName)) {
            return AllPassiveSkills.get(registerName);
        }
        return null;
    }

    /**
     * @param eventType
     * @return all the passive skills or null if there's no responding passive skill registered of this event.
     */
    @Nullable
    public static IPassiveSkill<?>[] getPassiveSkillsFromEvent(@Nonnull Class<? extends Event> eventType) {
        if (EventTypePassiveSkillMap.containsKey(eventType)) {
            return EventTypePassiveSkillMap.get(eventType).toArray(new IPassiveSkill[0]);
        }
        return null;
    }

}
