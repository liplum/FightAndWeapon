package net.liplum.events.weapon;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.items.weapons.bow.BowCore;
import net.liplum.items.weapons.bow.BowItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FoundAmmoEvent extends Event implements FawArgsGetter {

    @NotNull
    private final BowItem bow;
    @Nullable
    private final Modifier modifier;
    @NotNull
    private final ItemStack itemStack;
    @NotNull
    private final EntityLivingBase entity;
    @NotNull
    private ItemStack ammo;

    public FoundAmmoEvent(@NotNull BowItem bow, @Nullable Modifier modifier, @NotNull ItemStack itemStack, @NotNull EntityLivingBase entity, @NotNull ItemStack ammo) {
        this.bow = bow;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.ammo = ammo;
    }

    public FoundAmmoEvent(@NotNull BowItem bow, @Nullable Modifier modifier, @NotNull ItemStack itemStack, @NotNull EntityLivingBase entity) {
        this.bow = bow;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.ammo = ItemStack.EMPTY;
    }

    public boolean hasAmmo() {
        return !ammo.isEmpty();
    }

    public FoundAmmoEvent ammo(@NotNull ItemStack ammo) {
        this.ammo = ammo;
        return this;
    }

    @NotNull
    @Override
    public BowItem weapon() {
        return bow;
    }

    @NotNull
    public BowCore weaponCore() {
        return bow.getConcreteCore();
    }

    @NotNull
    public ItemStack ammo() {
        return ammo;
    }

    @Nullable
    public Modifier modifier() {
        return modifier;
    }

    @NotNull
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nullable
    @Override
    public EntityLivingBase entity() {
        return entity;
    }
}
