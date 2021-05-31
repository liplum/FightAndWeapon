package net.liplum.modifiers;

import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.cores.lance.ILanceCore;

public final class RubyModifier {
    public final static LanceModifier Normal_Lance = new LanceModifier() {
        @Override
        public float getSprintLengthDelta() {
            return 0;
        }

        @Override
        public float getSprintLengthRate() {
            return 0;
        }

        @Override
        public float getStrengthDelta() {
            return 2;
        }

        @Override
        public float getStrengthRate() {
            return 0.2F;
        }

        @Override
        public ILanceCore getCoreType() {
            return LanceCoreTypes.Normal;
        }
    };
}
