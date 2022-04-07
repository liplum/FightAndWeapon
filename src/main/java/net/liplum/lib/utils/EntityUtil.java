package net.liplum.lib.utils;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.FawDamage;
import net.liplum.lib.math.Angle;
import net.liplum.lib.math.P2D;
import net.liplum.lib.math.Point2D;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EntityUtil {
    public static boolean isUndead(@NotNull EntityLivingBase e) {
        return e.isEntityUndead();
    }

    public static boolean isEnemy(@NotNull EntityLivingBase e) {
        return e instanceof EntityMob || e instanceof EntitySlime || e instanceof EntityGhast || e instanceof EntityDragon || e instanceof EntityShulker;
    }

    public static void ifLivingThenDO(@NotNull Entity e, Consumer<EntityLivingBase> delegate) {
        if (e instanceof EntityLivingBase) {
            delegate.accept((EntityLivingBase) e);
        }
    }

    public static boolean isCreative(@Nullable EntityPlayer player) {
        return player != null && player.isCreative();
    }

    public static boolean spawnEntityIfServer(@NotNull World world, @NotNull Entity entity) {
        if (!world.isRemote) {
            world.spawnEntity(entity);
            return true;
        }
        return false;
    }

    public static void knockBack(@NotNull EntityLivingBase subject, @NotNull EntityLivingBase object, float strength) {
        object.knockBack(subject, strength,
                MathHelper.sin(Angle.toRadian(subject.rotationYaw)),
                -MathHelper.cos(Angle.toRadian(subject.rotationYaw)));
    }

    public static void knockBackForward(@NotNull EntityLivingBase subject, @NotNull EntityLivingBase object, float strength) {
        Point2D sub = P2D.toPosition(subject);
        Point2D obj = P2D.toPosition(object);
        Point2D v = obj.minus(sub);
        object.knockBack(subject, strength, v.x, v.y);
    }

    public static void spawnSweepParticles(@NotNull EntityLivingBase entity) {
        //Copied from Vanilla
        World world = entity.world;
        if (world instanceof WorldServer) {
            double d0 = -MathHelper.sin(entity.rotationYaw * 0.017453292F);
            double d1 = MathHelper.cos(entity.rotationYaw * 0.017453292F);
            ((WorldServer) world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK,
                    entity.posX + d0, entity.posY + (double) entity.height * 0.5D, entity.posZ + d1,
                    0, d0, 0.0D, d1, 0.0D);
        }
    }

    public static boolean spawnParticleIfServer(@NotNull World world, @NotNull Consumer<World> doSomething) {
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

    @NotNull
    public static DamageSource genDamageSource(EntityLivingBase entity) {
        return entity instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) entity) : DamageSource.causeMobDamage(entity);
    }

    @NotNull
    public static FawDamage genFawDamage(@NotNull EntityLivingBase attacker, @NotNull ItemStack itemStack) {
        FawDamage damage;
        WeaponBaseItem weapon = (WeaponBaseItem) itemStack.getItem();
        Modifier modifier = GemUtil.getModifierFrom(itemStack);
        if (attacker instanceof EntityPlayer) {
            damage = FawDamage.byPlayer((EntityPlayer) attacker, weapon, modifier, itemStack);
        } else {
            damage = FawDamage.byMob(attacker, weapon, modifier, itemStack);
        }
        return damage;
    }

    @Developing
    public static void setRooting(@NotNull EntityLivingBase entity, double originX, double originY, double originZ) {
        PhysicsUtil.setPosition(entity, originX, originY, originZ);
    }

    @Developing
    public static void setRooting(@NotNull EntityLivingBase entity) {
        entity.motionX = 0;
        entity.motionZ = 0;
    }
}
