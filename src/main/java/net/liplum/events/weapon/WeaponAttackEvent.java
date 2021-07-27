package net.liplum.events.weapon;

import net.liplum.api.weapon.DamageArgs;
import net.liplum.api.weapon.WeaponAttackArgs;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class WeaponAttackEvent<Args extends WeaponAttackArgs<?>> extends Event {
    protected final WeaponAttackArgs<Args> args;

    public WeaponAttackEvent(WeaponAttackArgs<Args> args) {
        this.args = args;
    }

    @Nonnull
    public abstract WeaponAttackArgs<Args> getArgs();

    public static class Attacked extends WeaponAttackEvent<Attacked.Args> {
        private final Args myArgs;

        public Attacked(Args args) {
            super(args);
            this.myArgs = args;
        }

        @Nonnull
        @Override
        public Args getArgs() {
            return myArgs;
        }

        public static class Args extends WeaponAttackArgs<Args> {
            private boolean hitSuccessfully;
            private float totalDamage;

            public boolean isHitSuccessfully() {
                return hitSuccessfully;
            }

            public Args setHitSuccessfully(boolean hitSuccessfully) {
                this.hitSuccessfully = hitSuccessfully;
                return this;
            }

            public float getTotalDamage() {
                return totalDamage;
            }

            @Nonnull
            public Args setTotalDamage(float totalDamage) {
                this.totalDamage = totalDamage;
                return this;
            }
        }
    }

    public static class Attacking extends WeaponAttackEvent<Attacking.Args> {
        private final Args myArgs;

        public Attacking(Args args) {
            super(args);
            this.myArgs = args;
        }

        @Nonnull
        @Override
        public Args getArgs() {
            return myArgs;
        }

        public static class Args extends WeaponAttackArgs<Args> {
            private final List<DamageArgs> allDamages = new LinkedList<>();

            @Nonnull
            public List<DamageArgs> getAllDamages() {
                return allDamages;
            }

            @Nonnull
            @Override
            public Args initialDamage(@Nonnull DamageArgs initialDamage) {
                super.initialDamage(initialDamage);
                allDamages.clear();
                allDamages.add(initialDamage);
                return this;
            }
        }
    }
}
