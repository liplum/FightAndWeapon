package net.liplum.lib.registeies;

import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ModifierRegistry<Core, Modifier> {
    private final Map<Core, Modifier> modifiers = new HashMap<>();

    public void register(Core core, Modifier modifier) {
        modifiers.put(core, modifier);
    }

    /**
     * Gets corresponding modifier of this core.
     *
     * @return the modifier or null if it didn't has a corresponding modifier of the core.
     */
    @Nullable
    public Modifier getModifier(Core core) {
        if (modifiers.containsKey(core)) {
            return modifiers.get(core);
        }
        return null;
    }

    public Class<Core> getCoreType() {
        return (Class<Core>) getClass();
    }

    public boolean isRegistryOf(IWeaponCore core){
        return getCoreType().isInstance(core);
    }
}
