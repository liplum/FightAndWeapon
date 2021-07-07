package net.liplum.items.weapons.harp;

import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static net.liplum.Attributes.Generic.AbilityPower;
import static net.liplum.Attributes.Generic.CoolDown;
import static net.liplum.Attributes.Harp.*;

public final class HarpCoreTypes {
    public static final HarpCore Empty = new HarpCore() {
        @Override
        public boolean continueSkill(ContinuousHarpArgs args) {
            return false;
        }

        @Override
        public boolean releaseSkill(SingleHarpArgs args) {
            return false;
        }
    };

    public static final HarpCore Normal = new HarpCore() {

        @Override
        protected void buildAttributes(AttributeBuilder builder) {
            super.buildAttributes(builder);
            builder.set(
                    AbilityPower, AbilityPower.newBasicAttrValue(5)
            ).set(
                    Frequency, Frequency.newBasicAttrValue(40)
            ).set(
                    Radius, Radius.newBasicAttrValue(4)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(200)
            ).set(
                    MaxUseDuration, MaxUseDuration.newBasicAttrValue(400)
            );
        }

        @Override
        public boolean continueSkill(ContinuousHarpArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            double radius = args.getRadius();


            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(radius, 0.25D, radius));

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
            }

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
        public boolean releaseSkill(SingleHarpArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            double radius = args.getRadius();


            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(radius, 0.25D, radius));

            for (EntityLivingBase target : allInRange) {
                target.addPotionEffect(new PotionEffect(MobEffects.HASTE, 60, 0));
            }
            return true;
        }
    };
}
