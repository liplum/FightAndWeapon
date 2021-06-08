package net.liplum.lib.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EntityUtil {
    public static boolean isUndead(@Nonnull EntityLivingBase e) {
        return e.isEntityUndead();
    }

    public static boolean isEnemy(@Nonnull EntityLivingBase e) {
        return e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityGhast || e instanceof EntityDragon || e instanceof EntityShulker;
    }

    public static boolean spawnEntityIfServer(@Nonnull World world, @Nonnull Entity entity) {
        if (!world.isRemote) {
            world.spawnEntity(entity);
            return true;
        }
        return false;
    }

    public static boolean spawnParticleIfServer(@Nonnull World world, @Nonnull Consumer<World> doSomething) {
        if (!world.isRemote) {
            doSomething.accept(world);
            return true;
        }
        return false;
    }

    public static boolean canAttack(Entity subject, Entity object) {
        if (subject == object) {
            return false;
        }
        if (subject.isOnSameTeam(object)) {
            Team team = subject.getTeam();
            assert team != null;
            return team.getAllowFriendlyFire();
        }
        return true;
    }

    public static DamageSource genDamageSource(EntityLivingBase entity) {
        return entity instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) entity) : DamageSource.causeMobDamage(entity);
    }

    public static void setRooting(EntityLivingBase livingEntity, double originX, double originY, double originZ) {
        PhysicsTool.setPosition(livingEntity, originX, originY, originZ);
    }
}
