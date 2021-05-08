package net.liplum.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IndestructibleEntityItem extends EntityItem {
    public IndestructibleEntityItem(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.isImmuneToFire = true;
    }

    public IndestructibleEntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.isImmuneToFire = true;
    }

    public IndestructibleEntityItem(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.getDamageType().equals(DamageSource.OUT_OF_WORLD.getDamageType())){
            return true;
        }
        return false;
    }
}
