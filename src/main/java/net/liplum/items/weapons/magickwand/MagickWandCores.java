package net.liplum.items.weapons.magickwand;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;

import javax.annotation.Nonnull;

@LongSupport
public final class MagickWandCores {

    public static final MagickWandCore Gemsword = new MagickWandCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    1, PSkills.AP2Strength
            );
        }
    };
}
