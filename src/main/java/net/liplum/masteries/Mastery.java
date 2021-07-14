package net.liplum.masteries;

import net.liplum.api.fight.IActiveSkill;
import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.Attribute;

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
    public String getRegisterName() {
        return weaponType.getRegisterName();
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Nonnull
    @Override
    public Map<Attribute, AttrDelta> getAttributeAmplifier(int level) {
        Map<String, Number> source = routine.getAttributeAmplifiers(level);
        Map<Attribute, AttrDelta> res = new HashMap<>();
        for (Map.Entry<String, Number> entry : source.entrySet()) {
            Attribute attribute = Attribute.getAttribute(entry.getKey());
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
    public List<IActiveSkill> getActiveSkills(int level) {
        LinkedList<IActiveSkill> res = new LinkedList<>();
        for (String name : routine.getActiveSkills(level)) {
            IActiveSkill skill = SkillRegistry.getActiveSkillsFromName(name);
            if (skill != null) {
                res.add(skill);
            }
        }
        return res;
    }
}
