package net.liplum.items.weapons.rangeweapon;

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
            builder.setRegisterName(
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
            builder.setRightClickUseAction(
                    EnumAction.BOW
            ).setRegisterName(
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
            builder.setRightClickUseAction(
                    EnumAction.BOW
            ).setRegisterName(
                    Names.Item.RangedWeapon.MeteorHammerItem
            );
        }
    };

}
