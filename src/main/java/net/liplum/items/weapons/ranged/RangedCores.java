package net.liplum.items.weapons.ranged;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;

@LongSupport
public class RangedCores {
    public static final RangedCore Sickle = new RangedCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    Names.Item.Ranged.SickleItem
            );
        }
    };

    public static final RangedCore ChainedHammer = new RangedCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    EnumAction.BOW
            ).set(
                    Names.Item.Ranged.ChainedHammerItem
            );
        }
    };

    public static final RangedCore MeteorHammer = new RangedCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    EnumAction.BOW
            ).set(
                    Names.Item.Ranged.MeteorHammerItem
            );
        }
    };

}
