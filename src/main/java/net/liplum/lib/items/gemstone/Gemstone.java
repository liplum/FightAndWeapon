package net.liplum.lib.items.gemstone;

import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Gemstone implements IGemstone {
    private final String registerName;
    private Map<IWeaponCore, Modifier> modifiersMap = new HashMap<>();

    public Gemstone(String registerName) {
        this.registerName = registerName;
    }

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
    @Nullable
    public Modifier getModifierOf(IWeaponCore core) {
        if (!modifiersMap.containsKey(core)) {
            return null;
        }
        return modifiersMap.get(core);
    }

    public Gemstone addModifier(Modifier newModifier) {
        modifiersMap.put(newModifier.getCoreType(), newModifier);
        return this;
    }

    public String getRegisterName() {
        return registerName;
    }
}
