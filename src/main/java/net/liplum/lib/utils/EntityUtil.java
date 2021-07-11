package net.liplum.lib.utils;

import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.lib.FawDamage;
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
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EntityUtil {
    public static boolean isUndead(@Nonnull EntityLivingBase e) {
        return e.isEntityUndead();
    }

    public static boolean isEnemy(@Nonnull EntityLivingBase e) {
        return e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityGhast || e instanceof EntityDragon || e instanceof EntityShulker;
    }

    public static void ifLivingThenDO(@Nonnull Entity e, Consumer<EntityLivingBase> delegate) {
        if (e instanceof EntityLivingBase) {
            delegate.accept((EntityLivingBase) e);
        }
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

    public static FawDamage genFawDamage(EntityLivingBase attacker, WeaponCore weaponCore, @Nullable IGemstone gemstone) {
        FawDamage damage;
        if (attacker instanceof EntityPlayer) {
            damage = FawDamage.byPlayer((EntityPlayer) attacker, weaponCore);
        } else {
            damage = FawDamage.byMob(attacker, weaponCore);
        }
        if (gemstone != null) {
            damage.setGemstone(gemstone);
        }
        return damage;
    }

    public static FawDamage genFawDamage(EntityLivingBase attacker, WeaponCore weaponCore, @Nullable IGemstone gemstone, @Nullable Modifier modifier) {
        FawDamage damage;
        if (attacker instanceof EntityPlayer) {
            damage = FawDamage.byPlayer((EntityPlayer) attacker, weaponCore);
        } else {
            damage = FawDamage.byMob(attacker, weaponCore);
        }
        if (gemstone != null && modifier != null) {
            damage.setGemstone(gemstone, modifier);
        }
        return damage;
    }

    public static void setRooting(EntityLivingBase livingEntity, double originX, double originY, double originZ) {
        PhysicsTool.setPosition(livingEntity, originX, originY, originZ);
    }
}
