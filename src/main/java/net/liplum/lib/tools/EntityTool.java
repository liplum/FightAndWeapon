package net.liplum.lib.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;

public class EntityTool {
    public static boolean isUndeadMob(LivingEntity e) {
        return e instanceof ZombieEntity || e instanceof ZombieVillagerEntity || e instanceof ZombifiedPiglinEntity || e instanceof GiantEntity || e instanceof ZombieHorseEntity ||
                e instanceof SkeletonEntity || e instanceof SkeletonHorseEntity || e instanceof WitherEntity || e instanceof WitherSkeletonEntity|| e instanceof HuskEntity|| e instanceof StrayEntity || e instanceof ZoglinEntity || e instanceof PhantomEntity || e instanceof DrownedEntity;
    }
}
