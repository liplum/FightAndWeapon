package net.liplum;

import net.liplum.lib.modifiers.ILanceModifier;
import net.liplum.lib.registeies.ModifierRegistry;
import net.liplum.lib.weaponcores.ILanceCore;

public final class ModifierRegistries {
    public final static ModifierRegistry<ILanceCore, ILanceModifier> Lance = new ModifierRegistry<>();
}
