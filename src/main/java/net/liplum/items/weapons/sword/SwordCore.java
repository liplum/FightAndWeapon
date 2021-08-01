package net.liplum.items.weapons.sword;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Sword.Sweep;

public class SwordCore extends WeaponCore {
    public SwordCore(@Nonnull String registerName) {
        super(registerName);
    }

    @Override
    public boolean releaseSkill(WeaponSkillArgs args) {
        return false;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Sword;
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                EnumAction.BLOCK
        ).set(
                Sweep, Sweep.newBasicAttrValue(0F)
        );
    }
}
