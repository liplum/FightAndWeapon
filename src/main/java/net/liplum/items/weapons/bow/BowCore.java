package net.liplum.items.weapons.bow;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.api.weapon.WeaponType;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;

public class BowCore extends WeaponCore {
    @Override
    public boolean releaseSkill(WeaponSkillArgs args) {
        return false;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Bow;
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.setRightClickUseAction(
                EnumAction.BOW
        );
    }
}
