package net.liplum.api.weapon;

import net.liplum.api.registeies.WeaponTypeRegistry;

import javax.annotation.Nonnull;

public class WeaponType {
    @Nonnull
    private final String registerName;

    @Nonnull
    private IWeaponSkillPredicate weaponSkillPredicate = (world, player, weapon) -> true;

    public WeaponType(@Nonnull String registerName) {
        this.registerName = registerName;
        WeaponTypeRegistry.register(this);
    }

    @Nonnull
    public String getRegisterName() {
        return registerName;
    }

    @Nonnull
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    public WeaponType setWeaponSkillPredicate(@Nonnull IWeaponSkillPredicate weaponSkillPredicate) {
        this.weaponSkillPredicate = weaponSkillPredicate;
        return this;
    }
}
