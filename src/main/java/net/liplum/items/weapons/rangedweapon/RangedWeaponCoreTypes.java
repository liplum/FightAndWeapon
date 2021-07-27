package net.liplum.items.weapons.rangedweapon;

import net.liplum.Names;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;

public class RangedWeaponCoreTypes {
    public static final RangedWeaponCore Sickle = new RangedWeaponCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    Names.Item.RangedWeapon.SickleItem
            );
        }
    };

    public static final RangedWeaponCore ChainedHammer = new RangedWeaponCore() {
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
                    Names.Item.RangedWeapon.ChainedHammerItem
            );
        }
    };

    public static final RangedWeaponCore MeteorHammer = new RangedWeaponCore() {
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
                    Names.Item.RangedWeapon.MeteorHammerItem
            );
        }
    };

}
