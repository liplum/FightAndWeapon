package net.liplum.api.weapon;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.registeies.WeaponTypeRegistry;
import org.jetbrains.annotations.NotNull;

@LongSupport
public class WeaponType {
    @NotNull
    private final String registerName;

    @NotNull
    private IWeaponSkillPredicate weaponSkillPredicate = (world, player, weapon) -> true;

    /**
     * Whenever you create the instance, it will register it self to {@link WeaponTypeRegistry} automatically.
     *
     * @param registerName the name to register itself
     */
    public WeaponType(@NotNull String registerName) {
        this.registerName = registerName;
        WeaponTypeRegistry.register(this);
    }

    @NotNull
    @LongSupport
    public String getRegisterName() {
        return registerName;
    }

    @NotNull
    @LongSupport
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @LongSupport
    public WeaponType setWeaponSkillPredicate(@NotNull IWeaponSkillPredicate weaponSkillPredicate) {
        this.weaponSkillPredicate = weaponSkillPredicate;
        return this;
    }
}
