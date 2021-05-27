package net.liplum.modifiers;

import net.liplum.ModifierRegistries;
import net.liplum.items.weapons.lance.LanceCoreType;
import net.liplum.lib.modifiers.LanceModifier;

public final class EnderGemstoneModifier {
    public final static LanceModifier Normal_Lance = ModifierRegistries.toLance(new LanceModifier() {
        @Override
        public double getRangeDelta() {
            return 2;
        }

        @Override
        public double getRangeRate() {
            return 0;
        }
    },LanceCoreType.Normal);

}
