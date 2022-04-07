package net.liplum.items.weapons.bow;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;
import org.jetbrains.annotations.NotNull;

@LongSupport
public final class BowCores {
    public static final BowCore Empty = new BowCore(Names.Item.EmptyCore, false, false) {
        @Override
        public float computeDamage(float originalDamage, int tick) {
            return 0;
        }

        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }

    };
    public static final BowCore SimpleBow = new BowCore(Names.Item.Bow.SimpleBow) {
        @Override
        public float computeDamage(float originalDamage, int pullingTick) {
            //TODO: compute Damage
            return 0;
        }

        @Override
        public int fullPullingTime() {
            return 60;
        }

        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }
    };
}
