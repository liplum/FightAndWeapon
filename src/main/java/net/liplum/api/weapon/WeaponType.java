package net.liplum.api.weapon;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.registeies.WeaponTypeRegistry;

import javax.annotation.Nonnull;

@LongSupport
public class WeaponType {
    @Nonnull
    private final String registerName;

    @Nonnull
    private IWeaponSkillPredicate weaponSkillPredicate = (world, player, weapon) -> true;

    /**
     * Whenever you create the instance, it will register it self to {@link WeaponTypeRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public WeaponType(@Nonnull String registerName) {
        this.registerName = registerName;
        WeaponTypeRegistry.register(this);
    }

    @Nonnull
    @LongSupport
    public String getRegisterName() {
        return registerName;
    }

    @Nonnull
    @LongSupport
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @LongSupport
    public WeaponType setWeaponSkillPredicate(@Nonnull IWeaponSkillPredicate weaponSkillPredicate) {
        this.weaponSkillPredicate = weaponSkillPredicate;
        return this;
    }
}
