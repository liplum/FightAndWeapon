package net.liplum.lib.items.gemstone;

import net.liplum.lib.registeies.ModifierRegistry;
import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Gemstone {
    private final String registerName;
    private Set<Modifier> modifiers = new HashSet<>();
    private Set<ModifierRegistry> modifierRegistries = new HashSet<>();

    public Gemstone(String registerName) {
        this.registerName = registerName;
    }

    public boolean hasModifierOf(IWeaponCore core) {
        ModifierRegistry registry = getCorrespondingRegistry(core);
        if (registry == null) {
            return false;
        }
        Modifier modifier = registry.getModifier(core);
        if (modifier != null) {
            return modifiers.contains(modifier);
        }
        return false;
    }

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    public Modifier getModifierOf(IWeaponCore core) {
        ModifierRegistry registry = getCorrespondingRegistry(core);
        if (registry == null) {
            return null;
        }
        Modifier modifier = registry.getModifier(core);
        if (modifier != null && modifiers.contains(modifier)) {
            return modifier;
        }
        return null;
    }

    @Nullable
    private ModifierRegistry getCorrespondingRegistry(IWeaponCore core) {
        for (ModifierRegistry r : modifierRegistries) {
            if (r.isRegistryOf(core)) {
                return r;
            }
        }
        return null;
    }

    public Gemstone addModifier(Modifier newModifier) {
        modifiers.add(newModifier);
        return this;
    }

    public Gemstone addModifiers(Modifier... newModifiers) {
        Collections.addAll(modifiers, newModifiers);
        return this;
    }

    public Gemstone addModifierRegistry(ModifierRegistry registry) {
        modifierRegistries.add(registry);
        return this;
    }

    public Gemstone addModifierRegistries(ModifierRegistry... newRegistries) {
        for(ModifierRegistry r : newRegistries){
            modifierRegistries.add(r);
        }
        return this;
    }

    public String getRegisterName() {
        return registerName;
    }
}
