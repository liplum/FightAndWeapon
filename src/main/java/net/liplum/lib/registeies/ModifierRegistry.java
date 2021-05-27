package net.liplum.lib.registeies;

import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ModifierRegistry {
    private final Map<IWeaponCore, Modifier> modifiers = new HashMap<>();

    private Class coreClass;
    private Class modifierClass;

    public ModifierRegistry(Class core, Class modifier) {
        this.coreClass = core;
        this.modifierClass = modifier;
    }

    public void register(IWeaponCore core, Modifier modifier) {
        modifiers.put(core, modifier);
    }

    /**
     * Gets corresponding modifier of this core.
     *
     * @return the modifier or null if it didn't has a corresponding modifier of the core.
     */
    @Nullable
    public Modifier getModifier(IWeaponCore core) {
        if (modifiers.containsKey(core)) {
            return modifiers.get(core);
        }
        return null;
    }

    public Class<? extends IWeaponCore> getCoreType() {
        return coreClass;
    }

    public boolean isRegistryOf(IWeaponCore core){
        return coreClass.isInstance(core);
    }
}
