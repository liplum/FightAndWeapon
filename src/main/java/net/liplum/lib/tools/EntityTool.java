package net.liplum.lib.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;

public class EntityTool {
    public static boolean isUndeadMob(EntityLivingBase e) {
        return e instanceof EntityZombie || e instanceof EntityZombieVillager || e instanceof EntityPigZombie || e instanceof EntityGiantZombie || e instanceof EntityZombieHorse ||
                e instanceof EntitySkeleton || e instanceof EntitySkeletonHorse || e instanceof EntityWither || e instanceof EntityWitherSkeleton || e instanceof EntityHusk|| e instanceof EntityStray;
    }
}
