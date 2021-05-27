package net.liplum.modifiers;

import net.liplum.ModifierRegistries;
import net.liplum.items.weapons.lance.LanceCoreType;
import net.liplum.lib.modifiers.LanceModifier;

public final class RubyModifier {
    public final static LanceModifier Normal_Lance = ModifierRegistries.toLance(new LanceModifier() {
        @Override
        public double getRangeDelta() {
            return 0;
        }

        @Override
        public double getRangeRate() {
            return 0;
        }

        @Override
        public float getStrengthDelta() {
            return 5;
        }

        @Override
        public float getStrengthRate() {
            return 0.2F;
        }
    }, LanceCoreType.Normal);
}
