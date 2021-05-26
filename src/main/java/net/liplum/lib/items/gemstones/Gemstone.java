package net.liplum.lib.items.gemstones;

import net.liplum.lib.registeies.modifier.ModifierRegistry;
import net.liplum.lib.modifiers.IModifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Gemstone {
    private String registryName;
    private Set<IModifier> modifiers = new HashSet<>();
    private Set<ModifierRegistry<IWeaponCore, IModifier>> modifierRegistries = new HashSet<>();

    public Gemstone(String registryName) {
        this.registryName = registryName;
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

    private ModifierRegistry<IWeaponCore, IModifier> getCorrespondingRegistry(IWeaponCore core) {
        for (ModifierRegistry<IWeaponCore, IModifier> r : modifierRegistries) {
            if (r.getCoreType().isInstance(core)) {
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

    public Gemstone addRegistry(ModifierRegistry registry) {
        modifierRegistries.add(registry);
        return this;
    }

    public Gemstone addRegistry(ModifierRegistry... newRegistries) {
        Collections.addAll(modifierRegistries, newRegistries);
        return this;
    }
}
