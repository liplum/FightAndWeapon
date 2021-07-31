package net.liplum.entities;

import net.liplum.MetaData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class IndestructibleItemEntity extends EntityItem {
    public IndestructibleItemEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.isImmuneToFire = true;
    }

    public IndestructibleItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.isImmuneToFire = true;
    }

    public IndestructibleItemEntity(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }

    @SubscribeEvent
    public static void onFawWeaponExpire(ItemExpireEvent event) {
        if (event.getEntityItem() instanceof IndestructibleItemEntity) {
            event.setCanceled(true);
        }
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (source.getDamageType().equals(DamageSource.OUT_OF_WORLD.getDamageType())) {
            return true;
        }
        return false;
    }
}
