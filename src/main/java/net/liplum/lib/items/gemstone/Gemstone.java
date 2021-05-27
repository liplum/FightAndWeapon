package net.liplum.lib.items.gemstone;

import net.liplum.lib.registeies.ModifierRegistry;
import net.liplum.lib.modifiers.IModifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Gemstone {
    private final String registerName;
    private Set<IModifier> modifiers = new HashSet<>();
    private Set<ModifierRegistry<IWeaponCore, IModifier>> modifierRegistries = new HashSet<>();

    public Gemstone(String registerName) {
        this.registerName = registerName;
    }

    public boolean hasModifierOf(IWeaponCore core) {
        ModifierRegistry<IWeaponCore, IModifier> registry = getCorrespondingRegistry(core);
        if (registry == null) {
            return false;
        }
        IModifier modifier = registry.getModifier(core);
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
    public IModifier getModifierOf(IWeaponCore core) {
        ModifierRegistry<IWeaponCore, IModifier> registry = getCorrespondingRegistry(core);
        if (registry == null) {
            return null;
        }
        IModifier modifier = registry.getModifier(core);
        if (modifier != null && modifiers.contains(modifier)) {
            return modifier;
        }
        return null;
    }

    @Nullable
    private ModifierRegistry<IWeaponCore, IModifier> getCorrespondingRegistry(IWeaponCore core) {
        for (ModifierRegistry<IWeaponCore, IModifier> r : modifierRegistries) {
            if (r.isRegistryOf(core)) {
                return r;
            }
        }
        return null;
    }

    public Gemstone addModifier(IModifier newModifier) {
        modifiers.add(newModifier);
        return this;
    }

    public Gemstone addModifiers(IModifier... newModifiers) {
        Collections.addAll(modifiers, newModifiers);
        return this;
    }

    public Gemstone addModifierRegistry(ModifierRegistry registry) {
        modifierRegistries.add(registry);
        return this;
    }

    public Gemstone addModifierRegistries(ModifierRegistry<?, ?>... newRegistries) {
        for(ModifierRegistry r : newRegistries){
            modifierRegistries.add(r);
        }
        return this;
    }

    public String getRegisterName() {
        return registerName;
    }
}
