package net.liplum.modifiers;

import net.liplum.items.weapons.lance.LanceCoreType;
import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.weaponcores.IWeaponCore;

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
        public IWeaponCore getCoreType() {
            return LanceCoreType.Normal;
        }
    };
}
