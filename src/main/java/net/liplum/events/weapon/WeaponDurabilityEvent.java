package net.liplum.events.weapon;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

public abstract class WeaponDurabilityEvent extends Event {
    @NotNull
    public final ItemStack itemStack;
    @NotNull
    public final WeaponBaseItem weaponItem;
    @NotNull
    public final EntityLivingBase entity;

    private int amount;

    public WeaponDurabilityEvent(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weaponItem,
                                 @NotNull EntityLivingBase entity) {
        this(itemStack, weaponItem, entity, 0);
    }

    public WeaponDurabilityEvent(@NotNull ItemStack itemStack,
                                 @NotNull WeaponBaseItem weaponItem,
                                 @NotNull EntityLivingBase entity, int amount) {
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

        public Healed(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weaponItem, @NotNull EntityLivingBase entity) {
            super(itemStack, weaponItem, entity);
        }

        public Healed(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weaponItem, @NotNull EntityLivingBase entity, int amount) {
            super(itemStack, weaponItem, entity, amount);
        }

    }

    public static class Damaged extends WeaponDurabilityEvent {

        public Damaged(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weaponItem, @NotNull EntityLivingBase entity) {
            super(itemStack, weaponItem, entity);
        }

        public Damaged(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weaponItem, @NotNull EntityLivingBase entity, int amount) {
            super(itemStack, weaponItem, entity, amount);
        }
    }
}
