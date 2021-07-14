package net.liplum.events.weapon;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public abstract class WeaponDurabilityEvent extends Event {
    @Nonnull
    public final ItemStack itemStack;
    @Nonnull
    public final WeaponBaseItem weaponItem;

    @Nonnull
    public final EntityLivingBase entity;

    private int amount;

    public WeaponDurabilityEvent(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weaponItem,
                                 @Nonnull EntityLivingBase entity) {
        this(itemStack, weaponItem, entity, 0);
    }

    public WeaponDurabilityEvent(@Nonnull ItemStack itemStack,
                                 @Nonnull WeaponBaseItem weaponItem,
                                 @Nonnull EntityLivingBase entity, int amount) {
        this.itemStack = itemStack;
        this.weaponItem = weaponItem;
        this.entity = entity;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class Healed extends WeaponDurabilityEvent {

        public Healed(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weaponItem, @Nonnull EntityLivingBase entity) {
            super(itemStack, weaponItem, entity);
        }

        public Healed(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weaponItem, @Nonnull EntityLivingBase entity, int amount) {
            super(itemStack, weaponItem, entity, amount);
        }

    }

    public static class Damaged extends WeaponDurabilityEvent {

        public Damaged(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weaponItem, @Nonnull EntityLivingBase entity) {
            super(itemStack, weaponItem, entity);
        }

        public Damaged(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weaponItem, @Nonnull EntityLivingBase entity, int amount) {
            super(itemStack, weaponItem, entity, amount);
        }
    }
}
