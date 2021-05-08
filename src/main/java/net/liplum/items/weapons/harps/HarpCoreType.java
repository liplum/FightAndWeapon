package net.liplum.items.weapons.harps;

import net.liplum.lib.tools.EntityTool;
import net.liplum.lib.items.IHarpCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class HarpCoreType {
    public static final IHarpCore Normal = new IHarpCore() {
        @Override
        public double getRadius() {
            return 4;
        }

        @Override
        public void releaseSkill(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target) {
            //If friend
            if (target == player ||
                    player.isOnSameTeam(target) ||//Player's team member
                    target instanceof EntityVillager
            ) {
                target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60, 1));
            }
            //If enemy
            else {
                //Detect whether the entity is a killer of zombies
                if (EntityTool.isUndead(target)) {
                    target.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
                } else {
                    target.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 2));
                }
            }
        }

        @Override
        public int getCoolDown() {
            return 12*20;
        }
    };
}
