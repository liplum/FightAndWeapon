package net.liplum.entities;


import net.liplum.api.fight.IFlyingItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class FlyingItemEntity extends EntityThrowable {
    private static final DataParameter<ItemStack> FlyingItem = EntityDataManager.createKey(FlyingItemEntity.class, DataSerializers.ITEM_STACK);
    @NotNull
    private IFlyingItemBehavior behavior = (weaponEntity, target, itemStack) -> {
    };

    public FlyingItemEntity(@NotNull World worldIn) {
        super(worldIn);
    }

    public FlyingItemEntity(@NotNull World worldIn, double x, double y, double z, @NotNull ItemStack itemStack, @NotNull IFlyingItemBehavior behavior) {
        super(worldIn, x, y, z);
        setFlyingItem(itemStack);
        this.behavior = behavior;
    }

    public FlyingItemEntity(@NotNull World worldIn, @NotNull EntityLivingBase throwerIn, @NotNull ItemStack itemStack, @NotNull IFlyingItemBehavior behavior) {
        super(worldIn, throwerIn);
        setFlyingItem(itemStack);
        this.behavior = behavior;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(FlyingItem, ItemStack.EMPTY);
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        Entity hitEntity = result.entityHit;
        if (hitEntity == null) {
            return;
        }
        behavior.onImpact(this, hitEntity, getFlyingItem());
    }

    @NotNull
    public ItemStack getFlyingItem() {
        return getDataManager().get(FlyingItem);
    }

    public void setFlyingItem(@NotNull ItemStack flyingItem) {
        EntityDataManager dataManager = getDataManager();
        dataManager.set(FlyingItem, flyingItem);
        dataManager.setDirty(FlyingItem);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0001F;
    }
}
