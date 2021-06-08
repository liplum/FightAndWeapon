package net.liplum.api.weapon;

public interface IModifier<CoreType extends IWeaponCore> {
    default float getCoolDownRate() {
        return 0;
    }

    default float getStrengthDelta() {
        return 0;
    }

    default float getStrengthRate() {
        return 0;
    }

    default int getEnemyBreakingTimeDelta() {
        return 0;
    }

    default float getEnemyBreakingTimeRate() {
        return 0;
    }

    CoreType getCoreType();
}
