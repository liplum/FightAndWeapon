package net.liplum.lib.items.gemstone;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.api.registeies.SkillManager;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Gemstone implements IGemstone {
    private final String registerName;
    private Map<IWeaponCore, Amplifier> modifiersMap = new HashMap<>();

    public Gemstone(String registerName) {
        this.registerName = registerName;
    }

    @Override
    public boolean hasModifierOf(IWeaponCore core) {
        if (modifiersMap.containsKey(core)) {
            return true;
        }
        return false;
    }

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Override
    @Nullable
    public IModifier getModifierOf(IWeaponCore core) {
        if (modifiersMap.containsKey(core)) {
            return modifiersMap.get(core).getModifier();
        }
        return null;
    }

    @Nullable
    @Override
    public IPassiveSkill[] getPassiveSkillsOf(IWeaponCore core) {
        if (modifiersMap.containsKey(core)) {
            return modifiersMap.get(core).getPassiveSkills();
        }
        return null;
    }

    @Override
    public IGemstone addModifier(IModifier newModifier) {
        IWeaponCore core = newModifier.getCoreType();
        if (modifiersMap.containsKey(core)) {
            modifiersMap.get(core).setModifier(newModifier);
        } else {
            Amplifier amplifier = new Amplifier();
            amplifier.setModifier(newModifier);
            modifiersMap.put(core, amplifier);
        }
        return this;
    }

    @Override
    public IGemstone addPassiveSkill(IWeaponCore core, IPassiveSkill newPassiveSkill) {
        SkillManager.Instance().registerPassiveSkill(newPassiveSkill);
        if (modifiersMap.containsKey(core)) {
            modifiersMap.get(core).addPassiveSkills(newPassiveSkill);
        } else {
            Amplifier amplifier = new Amplifier();
            amplifier.addPassiveSkills(newPassiveSkill);
            modifiersMap.put(core, amplifier);
        }
        return this;
    }

    @Override
    public String getRegisterName() {
        return registerName;
    }

    private static class Amplifier {
        private IModifier modifier = null;
        private Set<IPassiveSkill> passiveSkills = new HashSet<>();

        @Nullable
        public IModifier getModifier() {
            return modifier;
        }

        public void setModifier(IModifier modifier) {
            this.modifier = modifier;
        }

        @Nullable
        public IPassiveSkill[] getPassiveSkills() {
            if (passiveSkills.isEmpty()) {
                return null;
            }
            return passiveSkills.toArray(new IPassiveSkill[0]);
        }

        public void addPassiveSkills(IPassiveSkill passiveSkill) {
            passiveSkills.add(passiveSkill);
        }
    }
}
