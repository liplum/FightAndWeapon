package net.liplum.items.weapons.sword;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.item.EnumAction;
import org.jetbrains.annotations.NotNull;

import static net.liplum.Attributes.Sword.Sweep;

public class SwordCore extends WeaponCore {
    public SwordCore(@NotNull String registerName) {
        super(registerName);
    }

    public SwordCore(@NotNull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
        return false;
    }

    @NotNull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Sword;
    }

    @Override
    protected void build(@NotNull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                EnumAction.BLOCK
        ).set(
                Sweep, 0F
        );
    }
}
