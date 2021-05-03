package net.liplum.entities;

import net.liplum.lib.math.Point;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class StraightDamageEntity extends EntityThrowable {

    private float straightDamage = 0;
    private int duration = 0;
    private int current = 0;

    public StraightDamageEntity(World worldIn) {
        super(worldIn);
    }

    public StraightDamageEntity(World worldIn, EntityLivingBase throwerIn, float damage, int duration) {
        super(worldIn, throwerIn);
        this.straightDamage = damage;
        this.duration = duration;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) thrower), straightDamage);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        current++;
        if (current >= duration) {
            this.setDead();
        }
    }
}
