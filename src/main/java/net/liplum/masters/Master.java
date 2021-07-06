package net.liplum.masters;

import net.liplum.api.fight.IActiveSkill;
import net.liplum.api.fight.IMaster;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Master implements IMaster {
    private final WeaponType weaponType;
    private Routine routine;

    public Master(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(@Nonnull Routine routine) {
        this.routine = routine;
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
    public Map<String, Number> getAttributeAmplifier(int level) {
        return routine.getAttributeAmplifiers(level);
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
