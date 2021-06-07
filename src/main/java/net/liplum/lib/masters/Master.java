package net.liplum.lib.masters;

import net.liplum.api.fight.IActiveSkill;
import net.liplum.api.fight.IMaster;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Master implements IMaster {
    private final Class<? extends WeaponBaseItem<?>> weaponType;
    private final String registerName;
    private Routine routine;

    public Master(String registerName, Class<? extends WeaponBaseItem<?>> weaponType) {
        this.weaponType = weaponType;
        this.registerName = registerName;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(@Nonnull Routine routine) {
        this.routine = routine;
    }

    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public Class<? extends WeaponBaseItem<?>> getWeaponType() {
        return weaponType;
    }

    @Override
    public Map<String, Number> getAttributeAmplifier(int level) {
        return routine.getAttributeAmplifiers(level);
    }

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
