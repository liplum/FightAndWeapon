package net.liplum.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GemswordBeam extends EntityThrowable {

    public GemswordBeam(World worldIn) {
        super(worldIn);
        setSize(1F, 1F);
    }

    public GemswordBeam(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        setSize(1F, 1F);
    }

    public GemswordBeam(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        setSize(1F, 1F);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {

    }

    @Override
    protected float getGravityVelocity() {
        return 0.001F;
    }
}
