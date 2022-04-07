package net.liplum.items.weapons.magickwand;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.entities.FlyingItemEntity;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@LongSupport
public final class MagickWandCores {


    public static final MagickWandCore Empty = new MagickWandCore(Names.Item.MagickWand.GemswordItem, false) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }
    };

    public static final MagickWandCore Gemsword = new MagickWandCore(Names.Item.MagickWand.GemswordItem) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@NotNull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.add(
                    1, PSkills.AP2Strength
            );
        }
    };

    public static final MagickWandCore Athame = new MagickWandCore(Names.Item.MagickWand.AthameItem) {
        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            World world = args.world();
            if (!world.isRemote) {
                ItemStack itemStack = args.itemStack();
                WeaponBaseItem weapon = args.weapon();
                EntityLivingBase player = args.entity();
                FlyingItemEntity flyingItem = new FlyingItemEntity(world, player, itemStack,
                        (weaponEntity, target, itemStack1) -> {
                            weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), target, 10);
                            if (!weaponEntity.world.isRemote) {
                                weaponEntity.setDead();
                            }
                        });
                flyingItem.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 1F, 1F);
                world.spawnEntity(flyingItem);
            }
            return true;
        }
    };
}
