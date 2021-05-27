package net.liplum.modifiers;

import net.liplum.ModifierRegistries;
import net.liplum.items.weapons.lance.LanceCoreType;
import net.liplum.lib.modifiers.ILanceModifier;
import net.liplum.lib.weaponcores.ILanceCore;

public final class EnderGemstoneModifier {
    public final static ILanceModifier Normal_Lance = lance(new ILanceModifier() {
        @Override
        public double getRangeModifier() {
            return 2;
        }
    },LanceCoreType.Normal);

    private static ILanceModifier lance(ILanceModifier modifier, ILanceCore core){
        ModifierRegistries.Lance.register(core,modifier);
        return modifier;
    }
}
