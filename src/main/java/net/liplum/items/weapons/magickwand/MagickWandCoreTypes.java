package net.liplum.items.weapons.magickwand;

import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.skills.weapon.MagickWand;

import javax.annotation.Nonnull;

public class MagickWandCoreTypes {

    public static final MagickWandCore MagickSword = new MagickWandCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    1, MagickWand.Ap2Strength
            );
        }
    };
}
