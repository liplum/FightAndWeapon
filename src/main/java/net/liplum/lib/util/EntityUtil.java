package net.liplum.lib.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityUtil {
    public static boolean isUndead(@Nonnull EntityLivingBase e) {
        return e instanceof EntityZombie || e instanceof EntityZombieVillager || e instanceof EntityPigZombie || e instanceof EntityGiantZombie || e instanceof EntityZombieHorse ||
                e instanceof EntitySkeleton || e instanceof EntitySkeletonHorse || e instanceof EntityWither || e instanceof EntityWitherSkeleton || e instanceof EntityHusk || e instanceof EntityStray;
    }

    public static boolean isEnemy(@Nonnull EntityLivingBase e){
        return e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityGhast || e instanceof EntityDragon || e instanceof EntityShulker;
    }

    public static boolean spawnEntityIfServer(@Nonnull World world, @Nonnull Entity entity) {
        if (!world.isRemote) {
            world.spawnEntity(entity);
            return true;
        }
        return false;
    }
}
