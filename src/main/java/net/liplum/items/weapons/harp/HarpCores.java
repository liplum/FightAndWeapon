package net.liplum.items.weapons.harp;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.liplum.Attributes.Generic.*;
import static net.liplum.Attributes.Harp.Frequency;
import static net.liplum.Attributes.Harp.Radius;

@LongSupport
public final class HarpCores {
    public static final HarpCore Empty = new HarpCore(Names.Item.EmptyCore, false) {
        @Override
        public boolean continueSkill(ContinuousHarpArgs args) {
            return false;
        }

        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            return false;
        }
    };

    public static final HarpCore Normal = new HarpCore(Names.Item.Harp.HarpItem) {

        @Override
        protected void build(@NotNull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    AbilityPower, 5F
            ).set(
                    Frequency, 40
            ).set(
                    Radius, 4F
            ).set(
                    CoolDown, 10 * 20
            ).set(
                    MaxUseDuration, 400
            );
        }

        @Override
        public boolean continueSkill(ContinuousHarpArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();
            AttrCalculator calculator = args.calculator();

            double radius = calculator.calcu(Radius).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(radius, 0.25D, radius));

            int effectedEntityCount = 0;
            for (EntityLivingBase target : allInRange) {
                //If friend
                if (!EntityUtil.canAttack(player, target) ||//Player's team member
                        target instanceof EntityVillager
                ) {
                    target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60, 1));
                }
                //If enemy
                else {
                    //Detect whether the entity is a killer of zombies
                    if (EntityUtil.isUndead(target)) {
                        target.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
                    } else {
                        target.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 2));
                    }
                }
                effectedEntityCount++;
            }

            int weaponDamage = (int) MathUtil.castTo(1F, 5F, (float) effectedEntityCount / 3);
            FawItemUtil.damageWeapon(args.weapon(), args.itemStack(), weaponDamage, player);

            double px = player.posX, py = player.posY, pz = player.posZ;

            int rangeInt = MathHelper.ceil(radius);
            for (int i = -rangeInt; i <= rangeInt; i++) {
                for (int j = -rangeInt; j <= rangeInt; j++) {
                    world.spawnParticle(EnumParticleTypes.NOTE,
                            px + i + (0.5D - Utils.getRandom().nextGaussian())
                            , py,
                            pz + j + (0.5D - Utils.getRandom().nextGaussian()),
                            1, 1, 1);
                }
            }
            return true;
        }

        @Override
        public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();
            AttrCalculator calculator = args.calculator();

            double radius = calculator.calcu(Radius).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(radius, 0.25D, radius));

            for (EntityLivingBase target : allInRange) {
                target.addPotionEffect(new PotionEffect(MobEffects.HASTE, 60, 0));
            }
            FawItemUtil.damageWeapon(args.weapon(), args.itemStack(), 3, player);
            return true;
        }
    };
}
