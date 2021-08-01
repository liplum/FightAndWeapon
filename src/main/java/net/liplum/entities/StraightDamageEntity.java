package net.liplum.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class StraightDamageEntity extends EntityLiving {

    private float straightDamage = 10;
    private EntityPlayer player;

    public StraightDamageEntity(World worldIn) {
        super(worldIn);
    }

    public StraightDamageEntity(World worldIn, EntityPlayer player, float damage) {
        super(worldIn);
        this.player = player;
        this.straightDamage = damage;
        setSize(1F,1F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        AxisAlignedBB playerBox = this.getEntityBoundingBox();
        List<EntityLivingBase> collided = world
                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox);
        for (EntityLivingBase e : collided) {
            if (e != player) {
                e.attackEntityFrom(DamageSource.causePlayerDamage(player), straightDamage);
            }
        }
        if (motionX == 0 && motionZ == 0) {
            this.setDead();
        }
    }
}
