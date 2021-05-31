package net.liplum.lib.registeies;

import net.liplum.api.fight.IPassiveSkill;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SkillManager {
    private final static SkillManager instance = new SkillManager();

    public static SkillManager Instance() {
        return instance;
    }

    private Map<Class<? extends Event>, Set<IPassiveSkill>> passiveSkills = new HashMap<>();

    public void registerPassiveSkill(@Nonnull IPassiveSkill passiveSkill) {
        Class<? extends Event> eventType = passiveSkill.getEventType();
        if (passiveSkills.containsKey(eventType)) {
            passiveSkills.get(eventType).add(passiveSkill);
        }
        Set<IPassiveSkill> skills = new HashSet<>();
        passiveSkills.put(eventType, skills);
    }

    /**
     *
     * @param eventType
     * @return all the passive skills or null if there's no responding passive skill registered of this event.
     */
    @Nullable
    public IPassiveSkill[] getPassiveSkills(Class<? extends Event> eventType){
        if(passiveSkills.containsKey(eventType)){
            return passiveSkills.get(eventType).toArray(new IPassiveSkill[0]);
        }
        return null;
    }
}
