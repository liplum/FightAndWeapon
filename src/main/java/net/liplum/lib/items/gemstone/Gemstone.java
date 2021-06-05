package net.liplum.lib.items.gemstone;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.api.registeies.SkillManager;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Gemstone implements IGemstone {
    private final String registerName;
    private Map<IWeaponCore, IModifier> modifiersMap = new HashMap<>();

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
            return modifiersMap.get(core);
        }
        return null;
    }

    @Override
    public IGemstone addModifier(IModifier newModifier) {
        modifiersMap.put(newModifier.getCoreType(), newModifier);
        return this;
    }

    @Override
    public IGemstone addPassiveSkill(IPassiveSkill newPassiveSkill) {
        SkillManager.Instance().registerPassiveSkill(newPassiveSkill);
        return this;
    }

    @Override
    public String getRegisterName() {
        return registerName;
    }
}
