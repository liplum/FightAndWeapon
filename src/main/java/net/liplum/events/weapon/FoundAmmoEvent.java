package net.liplum.events.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.items.weapons.bow.BowCore;
import net.liplum.items.weapons.bow.BowItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FoundAmmoEvent extends Event implements FawArgsGetter {

    @Nonnull
    private final BowItem bow;
    @Nullable
    private final Modifier modifier;
    @Nonnull
    private final ItemStack itemStack;
    @Nonnull
    private final EntityLivingBase entity;
    @Nonnull
    private ItemStack ammo;

    public FoundAmmoEvent(@Nonnull BowItem bow, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EntityLivingBase entity, @Nonnull ItemStack ammo) {
        this.bow = bow;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.ammo = ammo;
    }

    public FoundAmmoEvent(@Nonnull BowItem bow, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EntityLivingBase entity) {
        this.bow = bow;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.ammo = ItemStack.EMPTY;
    }

    public boolean hasAmmo() {
        return !ammo.isEmpty();
    }

    public FoundAmmoEvent ammo(@Nonnull ItemStack ammo) {
        this.ammo = ammo;
        return this;
    }

    @Nonnull
    @Override
    public BowItem weapon() {
        return bow;
    }

    @Nonnull
    public BowCore weaponCore() {
        return bow.getConcreteCore();
    }

    @Nonnull
    public ItemStack ammo() {
        return ammo;
    }

    @Nullable
    public Modifier modifier() {
        return modifier;
    }

    @Nonnull
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nullable
    @Override
    public EntityLivingBase entity() {
        return entity;
    }
}
