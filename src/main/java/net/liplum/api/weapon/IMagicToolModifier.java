package net.liplum.api.weapon;

public interface IMagicToolModifier<CoreType extends IWeaponCore> extends IModifier<CoreType> {
    default int getAbilityPowerDelta() {
        return 0;
    }

    default float getAbilityPowerRate() {
        return 0;
    }
}
