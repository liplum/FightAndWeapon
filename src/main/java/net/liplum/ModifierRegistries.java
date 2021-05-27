package net.liplum;

import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.registeies.ModifierRegistry;
import net.liplum.lib.weaponcores.ILanceCore;

public final class ModifierRegistries {
    public final static ModifierRegistry Lance = new ModifierRegistry(ILanceCore.class,LanceModifier.class);

    public static LanceModifier toLance(LanceModifier modifier, ILanceCore core){
        Lance.register(core,modifier);
        return modifier;
    }
}
