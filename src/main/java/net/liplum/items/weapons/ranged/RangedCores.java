package net.liplum.items.weapons.ranged;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.minecraft.item.EnumAction;
import org.jetbrains.annotations.NotNull;

@LongSupport
public class RangedCores {
    public static final RangedCore Empty = new RangedCore(Names.Item.EmptyCore, false) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }
    };

    public static final RangedCore Sickle = new RangedCore(Names.Item.Ranged.SickleItem) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@NotNull WeaponCoreBuilder builder) {
            super.build(builder);
        }
    };

    public static final RangedCore ChainedHammer = new RangedCore(Names.Item.Ranged.ChainedHammerItem) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@NotNull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    EnumAction.BOW
            );
        }
    };

    public static final RangedCore MeteorHammer = new RangedCore(Names.Item.Ranged.MeteorHammerItem) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@NotNull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    EnumAction.BOW
            );
        }
    };

}
