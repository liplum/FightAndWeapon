package net.liplum.masteries;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Mastery implements IMastery {
    private final WeaponType weaponType;
    private Routine routine;

    /**
     * Whenever you create the instance, it will register it self to {@link MasteryRegistry} automatically.
     *
     * @param weaponType the corresponding weapon type of this master
     */
    public Mastery(WeaponType weaponType) {
        this.weaponType = weaponType;
        MasteryRegistry.register(this);
    }

    public Routine getRoutine() {
        return routine;
    }

    public Mastery setRoutine(@Nonnull Routine routine) {
        this.routine = routine;
        return this;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Nonnull
    @Override
    public Map<IAttribute, AttrDelta> getAttributeAmplifier(int level) {
        Map<String, Number> source = routine.getAttributeAmplifiers(level);
        Map<IAttribute, AttrDelta> res = new HashMap<>();
        for (Map.Entry<String, Number> entry : source.entrySet()) {
            IAttribute attribute = Attribute.getAttribute(entry.getKey());
            if (attribute != null) {
                res.put(attribute, attribute.newAttrDelta(entry.getValue()));
            }
        }
        return res;
    }

    @Nonnull
    @Override
    public List<IPassiveSkill<?>> getPassiveSkills(int level) {
        LinkedList<IPassiveSkill<?>> res = new LinkedList<>();
        for (String name : routine.getPassiveSkills(level)) {
            IPassiveSkill<?> skill = SkillRegistry.getPassiveSkillsFromName(name);
            if (skill != null) {
                res.add(skill);
            }
        }
        return res;
    }

    @Nonnull
    @Override
    public UnlockedPSkillList getUnlockedPassiveSkills(int level) {
        return new UnlockedPSkillList(routine.getLockedPassiveSkills(level));
    }
}
